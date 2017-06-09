package com.sq.server;

import com.sq.common.factory.NamedThreadFactory;
import com.sq.common.handler.HeartbeatHandler;
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
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.TimeUnit;


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
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline()
                                    .addLast(new TelEncoder())
                                    .addLast(new TelDecoder())
                                    .addLast("heartbeat", new IdleStateHandler(1, 5, 5, TimeUnit.SECONDS))
                                    .addLast("heartbeatHandler", new HeartbeatHandler())
                                    .addLast(new TelHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true);

            ChannelFuture future = bootstrap.bind(host, port).sync();
            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

}