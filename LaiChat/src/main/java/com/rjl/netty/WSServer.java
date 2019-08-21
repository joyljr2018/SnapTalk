package com.rjl.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

@Component
public class WSServer {
    //singleton class
    private static class SingletonWSServer {
        static final WSServer instance = new WSServer();
    }
    public static WSServer getInstance() {

        return SingletonWSServer.instance;
    }

    private  EventLoopGroup mainGroup;
    private EventLoopGroup subGroup;
    private ServerBootstrap server;
    private ChannelFuture future;

    public WSServer() {

        // event group
         mainGroup = new NioEventLoopGroup();
         subGroup = new NioEventLoopGroup();
         server = new ServerBootstrap();
         server.group(mainGroup,subGroup).channel(NioServerSocketChannel.class)
                .childHandler(new WSServerInitializer());
    }

    public void start() {
        this.future = server.bind(8088);

        System.err.println("netty websocket server started");
        System.err.println(future.channel().remoteAddress());
    }

}
