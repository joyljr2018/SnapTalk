package com.snap.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Description: server return hello netty
 */
public class HelloServer {
    public static void main(String[] args) {

        // declare a pair of threads
        // main
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //sub
        EventLoopGroup workerGroup = new NioEventLoopGroup();


        // start server, and set 8088 port
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup,workerGroup)  // setup main-sub relationship
                    .channel(NioServerSocketChannel.class) //setup nio two-way channel
                    .childHandler(new HelloServerInitializer()); //childhandler to controll workerGroup

            ChannelFuture channelFuture = serverBootstrap.bind(8088).sync();
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
