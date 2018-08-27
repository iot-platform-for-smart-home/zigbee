package com.bupt.ZigbeeResolution.transform;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class SocketServiceInitializer extends ChannelInitializer<SocketChannel>{

    public SocketServiceInitializer()
    {

    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception{
        ChannelPipeline pipeline = socketChannel.pipeline();
/*        pipeline.addLast("decoder", new ByteToMessageDecoder() {
            @Override
            protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

            }
        });

        pipeline.addLast("encoder", new MessageToByteEncoder<String>() {
            @Override
            protected  void encode(ChannelHandlerContext ctx, String msg, ByteBuf out){

            }
        });*/

        TransportHandler handler = new TransportHandler();
        pipeline.addLast(handler);
        socketChannel.closeFuture().addListener(handler);
    }
}
