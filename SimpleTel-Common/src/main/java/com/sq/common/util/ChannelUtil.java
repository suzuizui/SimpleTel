package com.sq.common.util;

import com.sq.common.cache.ChannelCache;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

/**
 * Created by qishang on 2017/6/9.
 */
public class ChannelUtil {
    public static void close(final Channel channel) {
        channel.close().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    ChannelCache.remove(channel);
                }
            }
        });
    }
}
