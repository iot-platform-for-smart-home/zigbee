package com.bupt.ZigbeeResolution.method;

import com.bupt.ZigbeeResolution.Gateway.data.Command;
import com.bupt.ZigbeeResolution.data.Device;
import com.bupt.ZigbeeResolution.transform.OutBoundHandler;
import com.bupt.ZigbeeResolution.transform.SocketServer;
import com.bupt.ZigbeeResolution.transform.TransportHandler;

public class GatewayMethodImpl extends OutBoundHandler implements  GatewayMethod{

    byte[] sendMessage =  new byte[1024];

    public void getAllDevice() throws Exception {
        byte[] bytes = new byte[8];
        //TransportHandler.response = 0x01;

        int index = 0;
        bytes[index++] = (byte) 0x08;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF ;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index] = (byte) 0x81;

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    public void getGatewayInfo() throws Exception{
        byte[] bytes = new byte[8];
        //TransportHandler.response = 0x01;

        int index = 0;
        bytes[index++] = (byte) 0x08;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF ;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index] = (byte) 0x9D;

        sendMessage = TransportHandler.getSendContent(12, bytes);
        Command cmd = new Command(sendMessage,(byte) 0x01);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    @Override
    public void device_CallBack(Device device){
        System.out.println(device.toString());
    }
}
