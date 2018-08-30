package com.bupt.ZigbeeResolution.transform;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ResourceLeakDetector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class SocketServer {

    private Channel serverChannel;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    @Value("${socket.bind_address}")
    private String host;
    @Value("${socket.bind_port}")
    private Integer port;

    @Value("${socket.netty.leak_detector_level}")
    private String leakDetectorLevel;
    @Value("${socket.netty.boss_group_thread_count}")
    private Integer bossGroupThreadCount;
    @Value("${socket.netty.worker_group_thread_count}")
    private Integer workerGroupThreadCount;

    @PostConstruct
    public void init() throws Exception{
        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.valueOf(leakDetectorLevel.toUpperCase()));

        bossGroup = new NioEventLoopGroup(bossGroupThreadCount);
        workerGroup = new NioEventLoopGroup(workerGroupThreadCount);
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                //.localAddress(new InetSocketAddress(port))
                .childHandler(new SocketServiceInitializer());

        serverChannel = b.bind(port).sync().channel();
    }

    @PreDestroy
    public void shutdown() throws InterruptedException {
        try {
            serverChannel.close().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
