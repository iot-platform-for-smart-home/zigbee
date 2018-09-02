package com.bupt.ZigbeeResolution.transform;

import com.bupt.ZigbeeResolution.method.GatewayMethodImpl;
import com.bupt.ZigbeeResolution.service.DataService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Arrays;

public class TransportHandler extends SimpleChannelInboundHandler<byte[]> implements GenericFutureListener<Future<? super Void>> {
    public static final ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public static final ChannelGroup channelgroups = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public final static String LoginControlMessage = "Login OK!\r\nControl Mode\r\nGateway online:";
    public final static String LoginMessage = "[Febit FCloud Server V2.0.0]\r\n[Normal Socket Mode]\r\nLogin:";
    public static byte response = 0x00 ;
    public static byte[] getSendContent;
    byte[] b = new byte[] { 4, 0, 31, 0 };
    byte[] bt = new byte[1024];

    String ipsname = null;
    String ips = null;

    public static GatewayMethodImpl gatewayMethod = new GatewayMethodImpl();

    public TransportHandler() {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        // ��channel map�����channel��Ϣ
        SocketServer.getMap().put(getIPString(ctx), ctx.channel());
        Channel channel = ctx.channel();
        bt = getSendContent(80, LoginMessage.getBytes());
        channel.write(bt);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        for (Channel ch : group) {
            ch.writeAndFlush(channel.remoteAddress());
        }
        group.add(channel);
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // ɾ��Channel Map�е�ʧЧClient
        SocketServer.getMap().remove(getIPString(ctx));
        ctx.close().sync();

    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, byte[] msg) throws Exception {
        String name = null;
        String pwd = null;
        byte byteA3 = msg[2];
        Channel channel = ctx.channel();
        String ctxip = channel.toString();
        String ip = getRemoteAddress(ctx);
        for (Channel ch : group) {
            ips = ip.substring(1, ip.length() - 6);
            if (ch == channel){
                System.out.println(SocketServer.bytesToHexString(msg));
                if (byteA3 == 30) {
                    System.out.println(ctx.toString() + "：" + SocketServer.bytesToHexString(msg));
                    ch.writeAndFlush(b);
                    if(response!=0x00){
                        DataService.setStatetrue();
                        response = 0x00;
                    }
                }else if(byteA3 == 81) {
                    System.out.println(Arrays.toString(msg));
                    String s = new String(msg);
                    if (s.length() == 16) {
                        name = s.substring(6, 12);
                        pwd = s.substring(12, 16);
                    } else if (s.length() == 17) {
                        name = s.substring(6, 13);
                        pwd = s.substring(13, 17);
                    }
                    bt = getSendContent(10, LoginControlMessage.getBytes());
                    channelgroups.add(channel);
                    ch.writeAndFlush(bt);
                }
                else  if (byteA3 == 12) {
                    System.out.println(ctx.toString() + "控制数据" + SocketServer.bytesToHexString(msg));
                    //chs.writeAndFlush(msg);
                } else if (byteA3 == 11) {

                    System.out.println(ctx.toString() + "传感器数据" + SocketServer.bytesToHexString(msg));
                    byte[] body = new byte[msg.length-6];
                    System.arraycopy(msg, 6, body, 0, msg.length-6);
                    if(body[0]!=response){
                        DataService.setStatetrue();
                        response = 0x00;
                    }
                    DataService.resolution(body);
                    //chs.writeAndFlush(msg);
                }
            }
        }
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

    public static String getIPString(ChannelHandlerContext ctx) {
        String ipString = "";
        String socketString = ctx.channel().remoteAddress().toString();
        int colonAt = socketString.indexOf(":");
        ipString = socketString.substring(1, colonAt);
        return ipString;
    }

    public static String getRemoteAddress(ChannelHandlerContext ctx) {
        String socketString = "";
        socketString = ctx.channel().remoteAddress().toString();
        return socketString;
    }

    public static byte[] getSendContent(int type, byte[] message) {
        int messageLength = message.length;
        int contentLength = message.length + 6;

        byte message_low = (byte) (messageLength & 0x00ff); // �����Ϣ��λ�ֽ�
        byte message_high = (byte) ((messageLength >> 8) & 0xff);// �����Ϣ��λ�ֽ�

        byte type_low = (byte) (type & 0x00ff); // ������͵�λ�ֽ�
        byte type_high = (byte) ((type >> 8) & 0xff);// ������͸�λ�ֽ�

        byte content_low = (byte) (contentLength & 0x00ff); // ������ݳ��ȵ�λ�ֽ�
        byte content_high = (byte) ((contentLength >> 8) & 0xff);// ������ݳ��ȸ�λ�ֽ�

        byte[] headMessage = new byte[6];
        headMessage[0] = content_low;
        headMessage[1] = content_high;
        headMessage[2] = type_low;
        headMessage[3] = type_high;
        headMessage[4] = message_low;
        headMessage[5] = message_high;

        byte[] sendContent = new byte[contentLength];
        System.arraycopy(headMessage, 0, sendContent, 0, 6);
        System.arraycopy(message, 0, sendContent, 6, messageLength);
        return sendContent;
    }
}
