package com.bupt.ZigbeeResolution.transform;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class TransportHandler extends ChannelInboundHandlerAdapter implements GenericFutureListener<Future<? super Void>> {

    public TransportHandler() {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel is active");

        byte[] bytes = new byte[8];
        int index = 0;
        bytes[index++] = (byte)0x08;
        bytes[index++] = (byte)0x00;
        bytes[index++] = (byte)0x78;
        bytes[index++] = (byte)0x56;
        bytes[index++] = (byte)0x34;
        bytes[index++] = (byte)0x12;
        bytes[index++] = (byte)0xFE;
        bytes[index] = (byte)0x81;

        ctx.writeAndFlush(bytes);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {


    }

    @Override
    public void operationComplete(Future<? super Void> future) throws Exception {

    }
}
