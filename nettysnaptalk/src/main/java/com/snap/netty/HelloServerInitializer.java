package com.snap.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Description: when channel registerd, initilize with this class
 */
public class HelloServerInitializer extends ChannelInitializer <SocketChannel> {

    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // obtain channel from socketChannel
        ChannelPipeline pipeline = socketChannel.pipeline();

        // add handler
        // HttpServerCodec, helper from netty
        // decode when receive request from server, encode to client side
        pipeline.addLast("HttpServerCodec", new HttpServerCodec());

        // add custom helper, return "hello netty~"
        pipeline.addLast("customHandler",new CustomHandler());
    }
}
