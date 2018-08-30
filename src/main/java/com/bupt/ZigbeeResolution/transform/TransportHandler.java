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

        // 获取当前连接的所有设备
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

    /**
     * 入站消息读取 ， 对每个传入的消息都要引用
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {


    }

    /**
     * 通知Handler最后一次调用channelRead()，读取当前入站信息最后一条消息
     * @param future  （Future 提供了另一种在操作完成时通知应用程序的方式）
     * @throws Exception
     */
    @Override
    public void operationComplete(Future<? super Void> future) throws Exception {

    }

    /**
     * 异常处理（记录异常并关闭连接）
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
