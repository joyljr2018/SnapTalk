package com.snap.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 *
 * @Description: custom Handler
 */

// simpleChannelInboundHandler: similar to check in
public class CustomHandler  extends SimpleChannelInboundHandler<HttpObject> {
    protected void channelRead0(ChannelHandlerContext ctx,
                                HttpObject msg) throws Exception {
        Channel channel = ctx.channel();
        if(msg instanceof HttpRequest ) {


            System.out.println(channel.remoteAddress());

            // define sending message
            ByteBuf content = Unpooled.copiedBuffer("Hello netty~", CharsetUtil.UTF_8);

            // http response
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,content);
            // set respone text type and length
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());

            // show content on cilent
            ctx.writeAndFlush(response);
        }

    }
}
