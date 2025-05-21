package io.weli.reactor;

import reactor.core.publisher.Flux;
import io.smallrye.mutiny.Uni;
import java.util.Random;

public class CaughtException {
    private static final Random random = new Random();
    
    public static void main(String[] args) {
        Flux.just(1, 2, 3)
                .map(i -> {
                    if (i == 2) throw new RuntimeException("Boom!");
                    return i;
                })
                .subscribe(
                        System.out::println,  // 正常处理: 1
                        e -> System.out.println("Error: " + e)  // 错误处理: Boom!
                );

        Uni<Integer> uni = Uni.createFrom().item(() -> {
                    if (random.nextBoolean()) {
                        throw new RuntimeException("Oops!");
                    }
                    return 42;
                })
                .onFailure().recoverWithItem(0); // 出错时返回0

        uni.subscribe().with(
                item -> System.out.println("Received: " + item),
                failure -> System.out.println("Failed with: " + failure) // 不会执行，因为错误已恢复
        );
    }
}
