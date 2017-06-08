package com.sq.common.cache;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.channel.group.ChannelMatcher;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * Created by qishang on 2017/6/8.
 */
public class ChannelCache {
    /**
     * 存储所有Channel
     */
    private static final ChannelGroup group = new DefaultChannelGroup("channelCache", GlobalEventExecutor.INSTANCE);

    public static void add(Channel channel) {
        group.add(channel);
    }

    public static ChannelGroupFuture broadcast(Object msg) {
        return group.writeAndFlush(msg);
    }

    public static ChannelGroupFuture broadcast(Object msg, ChannelMatcher matcher) {
        return group.writeAndFlush(msg, matcher);
    }

    public static ChannelGroup flush() {
        return group.flush();
    }

    public static boolean discard(Channel channel) {
        return group.remove(channel);
    }

    public static ChannelGroupFuture disconnect() {
        return group.disconnect();
    }

    public static ChannelGroupFuture disconnect(ChannelMatcher matcher) {
        return group.disconnect(matcher);
    }

    public static boolean contains(Channel channel) {
        return group.contains(channel);
    }

    public static int size() {
        return group.size();
    }
}
