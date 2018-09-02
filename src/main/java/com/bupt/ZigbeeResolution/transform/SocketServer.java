package com.bupt.ZigbeeResolution.transform;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SocketServer {
    private int port;

    private static Map<String, Channel> map = new ConcurrentHashMap<String, Channel>();
    private static Map<String, byte[]> messageMap = new ConcurrentHashMap<String, byte[]>();
    public SocketServer(int port){
        this.port = port;
    }

    public SocketServer(){}

    public void start() throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            // Decoders
                            //ch.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(1048576, 0, 4, 0, 4));
                            ch.pipeline().addLast("bytesDecoder", new ByteArrayDecoder());
                            // Encoder
                            //ch.pipeline().addLast("frameEncoder", new LengthFieldPrepender(4));
                            ch.pipeline().addLast("bytesEncoder", new ByteArrayEncoder());
                            ch.pipeline().addLast(new OutBoundHandler());
                            ch.pipeline().addLast(new IdleStateHandler(0,0,300), new TransportHandler());
                        }
                    });

            //b.bind(port);
            // Start the server.
            ChannelFuture f = b.bind(port).sync();
            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Shut down all event loops to terminate all threads.
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            System.out.println("shutdown");
        }
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception{
        new SocketServer(8090).start();


    }

    public static Map<String, Channel> getMap() {
        return map;
    }

    public static void setMap(Map<String, Channel> map) {
        SocketServer.map = map;
    }

    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
            stringBuilder.append(' ');
        }
        return stringBuilder.toString();
    }

    /**
     * @return the messageMap
     */
    public static Map<String, byte[]> getMessageMap() {
        return messageMap;
    }

    /**
     * @param messageMap the messageMap to set
     */
    public static void setMessageMap(Map<String, byte[]> messageMap) {
        SocketServer.messageMap = messageMap;
    }
}
