package com.bupt.ZigbeeResolution.method;

import com.bupt.ZigbeeResolution.data.Device;
import com.bupt.ZigbeeResolution.data.Gateway;
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
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    public void getDeviceState(Device device){
        byte[] bytes = new byte[21];

        int index = 0;
        bytes[index++] = (byte) 0x15;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x85;
        bytes[index++] = (byte) 0x0C;
        bytes[index++] = (byte) 0x02;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index=index+TransportHandler.toBytes(device.getShortAddress()).length;
        for(int i=0;i<6;i++){
            bytes[index++] = (byte) 0x00;
        }
        bytes[index++] = device.getEndpoint();
        for(int i=0;i<2;i++){
            bytes[index++] = (byte) 0x00;
        }

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    public void getDeviceBright(Device device){
        byte[] bytes = new byte[21];

        int index = 0;
        bytes[index++] = (byte) 0x15;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x86;
        bytes[index++] = (byte) 0x0C;
        bytes[index++] = (byte) 0x02;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index=index+TransportHandler.toBytes(device.getShortAddress()).length;
        for(int i=0;i<6;i++){
            bytes[index++] = (byte) 0x00;
        }
        bytes[index++] = device.getEndpoint();
        for(int i=0;i<2;i++){
            bytes[index++] = (byte) 0x00;
        }

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    public void getDeviceHue(Device device){
        byte[] bytes = new byte[21];

        int index = 0;
        bytes[index++] = (byte) 0x15;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x87;
        bytes[index++] = (byte) 0x0C;
        bytes[index++] = (byte) 0x02;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index=index+TransportHandler.toBytes(device.getShortAddress()).length;
        for(int i=0;i<6;i++){
            bytes[index++] = (byte) 0x00;
        }
        bytes[index++] = device.getEndpoint();
        for(int i=0;i<2;i++){
            bytes[index++] = (byte) 0x00;
        }

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    public void getDeviceSaturation(Device device){
        byte[] bytes = new byte[21];

        int index = 0;
        bytes[index++] = (byte) 0x15;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x88;
        bytes[index++] = (byte) 0x0C;
        bytes[index++] = (byte) 0x02;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index=index+TransportHandler.toBytes(device.getShortAddress()).length;
        for(int i=0;i<6;i++){
            bytes[index++] = (byte) 0x00;
        }
        bytes[index++] = device.getEndpoint();
        for(int i=0;i<2;i++){
            bytes[index++] = (byte) 0x00;
        }

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    @Override
    public void device_CallBack(Device device){
        System.out.println(device.toString());
    }

    @Override
    public void gateway_CallBack(Gateway gateway){
        System.out.println(gateway.toString());
    }

    @Override
    public void deviceState_CallBack(Device device){
        System.out.println(device.getShortAddress()+"-"+device.getEndpoint()+":"+device.getState());
    }

    @Override
    public void deviceBright_CallBack(String shortAddress, int endPoint, int bright){
        System.out.println(shortAddress+"-"+endPoint+":"+bright);
    }

    @Override
    public void deviceHue_CallBack(String shortAddress, int endPoint, int hue){
        System.out.println(shortAddress+"-"+endPoint+":"+hue);
    }

    @Override
    public void deviceSaturation_CallBack(String shortAddress, int endPoint, int saturation){
        System.out.println(shortAddress+"-"+endPoint+":"+saturation);
    }
}
