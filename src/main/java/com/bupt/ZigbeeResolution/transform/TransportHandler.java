package com.bupt.ZigbeeResolution.transform;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
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
        /*int index = 0;
        bytes[index++] = (byte)0x08;
        bytes[index++] = (byte)0x00;
        bytes[index++] = (byte)0x04;
        bytes[index++] = (byte)0x00;
        bytes[index++] = (byte)0x1E;
        bytes[index++] = (byte)0x00;
        bytes[index++] = (byte)0xFE;
        bytes[index] = (byte)0x81;
*/

        String control = "080004001E00FE81";
        bytes = toBytes(control);
        for(byte b:bytes){
            System.out.println(b);
        }

        ByteBuf buf = ctx.alloc().buffer(bytes.length);
        buf.writeBytes(bytes);
        ctx.writeAndFlush(buf).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                System.out.println("complete");
            }
        });
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        byte[] bytes = (byte[]) msg;
        //String bytes = (String) msg;
        //ByteBuf bytes = (ByteBuf) msg;
        String SN = bytesToHexFun3(bytes);
        System.out.println(SN);

    }

    @Override
    public void operationComplete(Future<? super Void> future) throws Exception {

    }

    public String bytesToHexFun3(byte[] bytes) {
        StringBuilder buf = new StringBuilder(bytes.length * 2);
        for(byte b : bytes) { // 使用String的format方法进行转换
            buf.append(String.format("%02x", new Integer(b & 0xff)));
        }

        return buf.toString();
    }

    public static byte[] toBytes(String str) {
        if(str == null || str.trim().equals("")) {
            return new byte[0];
        }

        byte[] bytes = new byte[str.length() / 2];
        for(int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }

        return bytes;
    }
}
