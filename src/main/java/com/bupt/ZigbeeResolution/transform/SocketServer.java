package com.bupt.ZigbeeResolution.transform;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
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

        EventLoopGroup group = new NioEventLoopGroup(); // 接受和处理新的连接
        try {
            // 指定使用的NIO传输Channel
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class) // 设置channel类型
                    .localAddress(new InetSocketAddress(port)) // 使用指定的端口设置套接字地址，服务器将绑定到这个地址以监听新的连接请求
                    .childHandler(new SocketServiceInitializer()); // 添加一个Handler实例到子Channel的ChannelPipeline
            ChannelFuture f = b.bind().sync(); // 绑定服务器
            f.channel().closeFuture().sync();  // 获取closeFuture对象
        }
        finally {
            group.shutdownGracefully().sync(); // 关闭 EventLoopGroup，释放所有资源
        }
    }
}
