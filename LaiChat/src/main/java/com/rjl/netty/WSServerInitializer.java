package com.rjl.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WSServerInitializer extends ChannelInitializer<SocketChannel> {


    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //websocket based http protocol
        pipeline.addLast(new HttpServerCodec());

        // support large data stream
        pipeline.addLast(new ChunkedWriteHandler());

        // aggregate httpMessage to a FullHttpRequest or FullHttpResponse
        pipeline.addLast(new HttpObjectAggregator(2024 * 64));

        // ===== support http protocol above===

        /**
         *  / websocket server protocol processor, router for client to connect
         *    does the heavy lifting
         *    handshaking(close,ping,pong)
         *
         *
         */


        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        //custom handler
        pipeline.addLast(new ChatHandler());


    }
}
