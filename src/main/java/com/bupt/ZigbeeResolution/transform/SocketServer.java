package com.bupt.ZigbeeResolution.transform;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ResourceLeakDetector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;

@Service
public class SocketServer {

    @Value("${socket.bind_address}")
    private String host;
    @Value("${socket.bind_port}")
    private Integer port;

    @PostConstruct
    public void init() throws Exception{
        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.valueOf("DISABLE"));

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new SocketServiceInitializer());
        }
        finally {

        }
    }
}
