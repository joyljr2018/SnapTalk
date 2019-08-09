package com.snap.netty.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;

/**
 * @Desription message processing handler
 * TextWebSocketFream: in netty, it's for websocket text processing object, frame is the message carrier
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    // use for recording and manageing all clients channels
    private static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * when client connects the server,
     * obtain the channel and put it to channgelGroup: users
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        users.add(ctx.channel());
        //System.out.println(ctx.channel().localAddress());
        System.out.println("channel added:" + ctx.channel().localAddress());
       // super.handlerAdded(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        users.remove(ctx.channel());
        System.out.println("client disconnected, long id:" + ctx.channel().id().asLongText());
        System.out.println("client disconnected, short id:" + ctx.channel().id().asShortText());

        //super.handlerRemoved(ctx);

    }

    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        // obtain message from client
        String content = msg.text();
        System.out.println("received message:" + content);

        for (Channel channel : users){
            channel.writeAndFlush(new TextWebSocketFrame("[server received message:]"
                    + LocalDateTime.now()+", message received:" + content));

        }
//        users.writeAndFlush(new TextWebSocketFrame("[server received message:]"
//                + LocalDateTime.now()+", message received:" + content));
    }
}
