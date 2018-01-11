package io.weli.jboss.msc2;

import org.jboss.msc.service.*;
import org.jboss.msc.txn.Transaction;
import org.jboss.msc.txn.TransactionController;
import org.jboss.msc.txn.UpdateTransaction;
import org.jboss.msc.util.CompletionListener;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by weli on 16/05/2017.
 */
public class Play {
    public static void main(String[] args) {

        TransactionController controller = TransactionController.newInstance();

        final CompletionListener<UpdateTransaction> updateListener = new CompletionListener<>();


        class BadExecutor implements Executor {

            @Override
            public void execute(Runnable command) {
                // do nothing
                throw new Error("I'm BAD.");
            }
        }

//        controller.newUpdateTransaction(new BadExecutor(), updateListener);
        controller.newUpdateTransaction(Executors.newSingleThreadExecutor(), updateListener);

        final UpdateTransaction transaction = updateListener.awaitCompletionUninterruptibly();

        /* the following code will add the update transaction into pending queue */
        final CompletionListener<UpdateTransaction> updateListener2 = new CompletionListener<>();
        controller.newUpdateTransaction(Executors.newSingleThreadExecutor(), updateListener2);
        /* unless the currently running update transaction is commited, it will block here */
//        final UpdateTransaction transaction2 = updateListener2.awaitCompletionUninterruptibly();

        class FooService implements Service<Void> {

            @Override
            public void start(StartContext<Void> startContext) {
                System.out.println("Foo service started.");
            }

            @Override
            public void stop(StopContext stopContext) {
                System.out.println("Foo service stopped.");
            }
        }


        try {
            ServiceContainer container = controller.newServiceContainer(transaction);
            ServiceRegistry registry = container.newRegistry(transaction);
            ServiceBuilder<Void> serviceBuilder = controller.newServiceContext(transaction).addService(registry, ServiceName.of("foo"));
            serviceBuilder.setService(new FooService());
            serviceBuilder.install();
            controller.downgrade(transaction, null);

        } finally {
            final CompletionListener<Transaction> prepareListener = new CompletionListener<>();
            controller.prepare(transaction, prepareListener);
            prepareListener.awaitCompletionUninterruptibly();

            final CompletionListener<Transaction> commitListener = new CompletionListener<>();
            controller.commit(transaction, commitListener);
            commitListener.awaitCompletionUninterruptibly();
        }
    }
}
