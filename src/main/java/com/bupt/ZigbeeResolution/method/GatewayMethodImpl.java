package com.bupt.ZigbeeResolution.method;

import com.bupt.ZigbeeResolution.data.Device;
import com.bupt.ZigbeeResolution.service.DataService;
import com.bupt.ZigbeeResolution.transform.OutBoundHandler;
import com.bupt.ZigbeeResolution.transform.SocketServer;
import com.bupt.ZigbeeResolution.transform.TransportHandler;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class GatewayMethodImpl extends OutBoundHandler implements  GatewayMethod{
    byte[] bytes = new byte[8];
    byte[] sendMessage =  new byte[1024];

    public List<Device> getAllDevice(){
        DataService.cleatList();
        DataService.setStatefalse();
        TransportHandler.response = 0x01;

        List<Device> devices= new LinkedList<Device>();

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

        while(true){
            if(DataService.getState()==true){
                Collections.addAll(devices, DataService.getList().toArray(new Device[0]));
                break;
            }
        }
        return devices;
    }
}
