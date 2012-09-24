package net.bluedash.snippets.netty;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;

/**
 * 09 16 2012
 *
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class TimeServerHandler extends SimpleChannelHandler {
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
        Channel ch = e.getChannel();

        ChannelBuffer time = ChannelBuffers.buffer(4);
        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));

        ChannelFuture f = ch.write(time);

        f.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) {
                Channel ch = future.getChannel();
                ch.close();
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        e.getCause().printStackTrace();
        e.getChannel().close();
    }
}
