package com.sq.server;

import com.sq.common.factory.NamedThreadFactory;
import com.sq.common.handler.TelDecoder;
import com.sq.common.handler.TelEncoder;
import com.sq.common.handler.TelHandler;
import com.sq.common.util.NetUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;


/**
 * Created by qishang on 2017/6/8.
 */
public class TelServer implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(TelServer.class);
    private final static String host = "127.0.0.1";
    private final int port;
    private final int workerCount;

    public TelServer() {
        this(10086);
    }

    public TelServer(int port) {
        this(port, Runtime.getRuntime().availableProcessors());
    }

    public TelServer(int port, int workerCount) {
        while (NetUtils.LocalPortIsUsing(port)) {
            port++;
        }
        this.port = port;
        this.workerCount = workerCount;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

    public void init() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup(workerCount, new NamedThreadFactory("TelWorker"));
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline();
                        }
                    })
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline()
                                    .addLast(new LengthFieldBasedFrameDecoder(65536, 0, 4, 0, 0))
                                    .addLast(new TelEncoder())
                                    .addLast(new TelDecoder())
                                    .addLast(new TelHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = bootstrap.bind(host, port).sync();
            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

}