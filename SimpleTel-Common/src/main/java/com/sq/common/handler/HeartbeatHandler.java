package com.sq.common.handler;

import com.sq.common.cache.ChannelCache;
import com.sq.common.remote.message.HeartBreak;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * Created by qishang on 2017/6/8.
 */
@ChannelHandler.Sharable
public class HeartbeatHandler extends SimpleChannelInboundHandler<HeartBreak> {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.ALL_IDLE) {
                Channel channel = ctx.channel();
                ChannelCache.remove(channel);
                channel.close();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    /**
     * 拦截心跳包
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, HeartBreak msg) throws Exception {
        ctx.writeAndFlush(new HeartBreak());
        super.channelRead(ctx, msg);
    }
}
