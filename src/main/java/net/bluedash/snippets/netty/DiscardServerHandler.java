package net.bluedash.snippets.netty;

import org.jboss.netty.channel.*;

/**
 * 09 16 2012
 *
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class DiscardServerHandler extends SimpleChannelHandler {

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        e.getCause().printStackTrace();

        Channel ch = e.getChannel();
        ch.close();
    }
}
