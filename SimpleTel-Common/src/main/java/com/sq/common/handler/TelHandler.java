package com.sq.common.handler;

import com.sq.common.cache.ChannelCache;
import com.sq.common.remote.message.TelRequest;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by qishang on 2017/6/8.
 */
public class TelHandler extends SimpleChannelInboundHandler<TelRequest> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        if (!ChannelCache.contains(channel)) {
            ChannelCache.add(channel);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, final TelRequest msg) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }
}
