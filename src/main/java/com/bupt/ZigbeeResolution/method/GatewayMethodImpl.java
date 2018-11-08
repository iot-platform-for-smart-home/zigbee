package com.bupt.ZigbeeResolution.method;

import com.bupt.ZigbeeResolution.data.*;
import com.bupt.ZigbeeResolution.http.HttpControl;
import com.bupt.ZigbeeResolution.mqtt.DataMessageClient;
import com.bupt.ZigbeeResolution.service.DataService;
import com.bupt.ZigbeeResolution.service.DeviceTokenRelationService;
import com.bupt.ZigbeeResolution.service.SceneService;
import com.bupt.ZigbeeResolution.transform.OutBoundHandler;
import com.bupt.ZigbeeResolution.transform.SocketServer;
import com.bupt.ZigbeeResolution.transform.TransportHandler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Map;

public class GatewayMethodImpl extends OutBoundHandler implements  GatewayMethod{

    HttpControl httpControl = new HttpControl();

    byte[] sendMessage =  new byte[1024];

    public void getAllDevice(String ip) throws Exception {
        byte[] bytes = new byte[8];

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
        SocketServer.getMap().get(ip).writeAndFlush(sendMessage);
    }

    public void getGatewayInfo() throws Exception{
        byte[] bytes = new byte[8];

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

    public void getGroup(){
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
        bytes[index] = (byte) 0x8E;

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    public void getGroupMember(Group group){
        byte[] bytes = new byte[11];

        int index = 0;
        bytes[index++] = (byte) 0x0B;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF ;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x98;
        bytes[index++] = (byte) 0x02;
        System.arraycopy(TransportHandler.toBytes(group.getGroupId()), 0, bytes, index, TransportHandler.toBytes(group.getGroupId()).length);

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    public void getScene(){
        System.out.print("获取场景 => ");
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
        bytes[index] = (byte) 0x90;

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    public void getSceneDetail(Scene scene){
        System.out.print("获取场景详细信息 => ");
        byte[] bytes = new byte[scene.getSceneName().getBytes().length + 12];

        int index = 0;
        bytes[index++] = (byte) (0xFF & (scene.getSceneName().getBytes().length+12));
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF ;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x8A;
        bytes[index++] = (byte) (0xFF & (scene.getSceneName().getBytes().length+4));
        System.arraycopy(TransportHandler.toBytes(scene.getSceneId()), 0, bytes, index, TransportHandler.toBytes(scene.getSceneId()).length);
        index=index+TransportHandler.toBytes(scene.getSceneId()).length;
        bytes[index++] = (byte) (0xFF & (scene.getSceneName().getBytes().length));
        System.arraycopy(scene.getSceneName().getBytes(), 0, bytes, index, scene.getSceneName().getBytes().length);

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    public void deleteSceneMember(Scene scene, Device device, String ip){
        byte[] bytes = new byte[scene.getSceneName().getBytes().length+23];

        int index = 0;
        bytes[index++] = (byte) (0xFF & (scene.getSceneName().getBytes().length+23));
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF ;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x8B;
        bytes[index++] = (byte) (0xFF & (scene.getSceneName().getBytes().length+14));
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
        bytes[index++] = (byte) (0xFF & (scene.getSceneName().getBytes().length));
        System.arraycopy(scene.getSceneName().getBytes(), 0, bytes, index, scene.getSceneName().getBytes().length);

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get(ip).writeAndFlush(sendMessage);
    }

    public void getTimerTask(){
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
        bytes[index] = (byte) 0x99;

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }


    public void getTask(){
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
        bytes[index] = (byte) 0xA6;

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    public void getTaskDetail(Task task){
        byte[] bytes = new byte[TransportHandler.toBytes(task.getTaskName()).length+10];

        int index = 0;
        bytes[index++] = (byte) (0xFF & (task.getTaskName().getBytes().length+10));
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF ;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0xA5;
        bytes[index++] = (byte) (0xFF & (task.getTaskName().getBytes().length+1));
        bytes[index++] = (byte) (0xFF & (task.getTaskName().getBytes().length));
        System.arraycopy(task.getTaskName().getBytes(), 0, bytes, index, task.getTaskName().getBytes().length);

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    public void getDeviceColourTemp(Device device){
        byte[] bytes = new byte[21];

        int index = 0;
        bytes[index++] = (byte) 0x15;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0xA9;
        bytes[index++] = (byte) 0x0A;
        bytes[index++] = (byte) 0x02;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index=index+TransportHandler.toBytes(device.getShortAddress()).length;
        for(int i=0;i<6;i++){
            bytes[index++] = (byte) 0x00;
        }
        bytes[index] = device.getEndpoint();

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    public void setGroupName(Group group,String name){
        byte[] bytes = new byte[name.getBytes().length+12];

        int index = 0;
        bytes[index++] = (byte) (0xFF & (name.getBytes().length+12));
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF ;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0xA5;
        bytes[index++] = (byte) (0xFF & (name.getBytes().length+3));
        System.arraycopy(TransportHandler.toBytes(group.getGroupId()), 0, bytes, index, TransportHandler.toBytes(group.getGroupId()).length);
        index=index+TransportHandler.toBytes(group.getGroupId()).length;
        bytes[index++] = (byte) (0xFF & (name.getBytes().length));
        System.arraycopy(name.getBytes(), 0, bytes, index, name.getBytes().length);

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }


    public void changeDeviceName(Device device, String name){
        byte[] bytes = new byte[name.getBytes().length+13];

        int index = 0;
        bytes[index++] = (byte) (0xFF & (name.getBytes().length + 13));
        bytes[index++] = (byte) 0x00;
        for (int i = 0; i < 4; i++){
            bytes[index++] = (byte) 0xFF;
        }
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x94;
        bytes[index++] = (byte) (0xFF & (name.getBytes().length + 4));
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index = index + TransportHandler.toBytes(device.getShortAddress()).length;
        bytes[index++] = device.getEndpoint();
        bytes[index++] = (byte)(0xFF & name.getBytes().length);
        System.arraycopy(name.getBytes(),0, bytes, index, name.getBytes().length);

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    public void deleteDevice(Device device,String ip){
        byte[] bytes = new byte[21];

        int index = 0;
        bytes[index++] = (byte) 0x15;
        bytes[index++] = (byte) 0x00;
        for (int i = 0; i < 4; i++){
            bytes[index++] = (byte) 0xFF;
        }
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x95;
        bytes[index++] = (byte) 0x0C;
        bytes[index++] = (byte) 0x02;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index = index + TransportHandler.toBytes(device.getShortAddress()).length;
        System.arraycopy(TransportHandler.toBytes(device.getIEEE()), 0, bytes, index, TransportHandler.toBytes(device.getIEEE()).length);
        index = index + TransportHandler.toBytes(device.getIEEE()).length;
        bytes[index] = device.getEndpoint();

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get(ip).writeAndFlush(sendMessage);
    }

    public void setDeviceState(Device device, byte state, String ip) {
        System.out.println("进入setDeviceState");
        byte[] bytes = new byte[22];

        int index = 0;
        bytes[index++] = (byte) 0x16;
        bytes[index++] = (byte) 0x00;
        for (int i = 0; i < 4; i++){
            bytes[index++] = (byte) 0xFF;
        }
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x82;
        bytes[index++] = (byte) 0x0D;
        bytes[index++] = (byte) 0x02;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index=index+TransportHandler.toBytes(device.getShortAddress()).length;
        for (int i = 0; i < 6; i++){
            bytes[index++] = (byte) 0x00;
        }
        bytes[index++] = device.getEndpoint();
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0x00;
        bytes[index] = state;

        sendMessage = TransportHandler.getSendContent(12, bytes);
        System.out.println("下发指令");
        SocketServer.getMap().get(ip).writeAndFlush(sendMessage);
    }

    @Override
    public void setDeviceLevel(Device device, byte value, int transition, String ip) {
        byte[] bytes = new byte[24];

        int index = 0;
        bytes[index++] = (byte) 0x18;
        bytes[index++] = (byte) 0x00;
        for (int i = 0; i < 4; i++){
            bytes[index++] = (byte) 0xFF;
        }
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x83;
        bytes[index++] = (byte) 0x0F;
        bytes[index++] = (byte) 0x02;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index = index + TransportHandler.toBytes(device.getShortAddress()).length;
        for (int i = 0; i < 6; i++){
            bytes[index++] = (byte) 0x00;
        }
        bytes[index++] = device.getEndpoint();
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = value;
        bytes[index++] = (byte) (0xFF & (byte)transition);
        bytes[index] = (byte) (0xFF & ((byte)(transition) >> 8));

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get(ip).writeAndFlush(sendMessage);
    }

    public void setDeviceHueAndSat(Device device, byte hue, byte sat, int transition) {
        byte[] bytes = new byte[25];

        int index = 0;
        bytes[index++] = (byte) 0x19;
        bytes[index++] = (byte) 0x00;
        for (int i = 0; i < 4; i++){
            bytes[index++] = (byte) 0xFF;
        }
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x84;
        bytes[index++] = (byte) 0x10;
        bytes[index++] = (byte) 0x02;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index=index+TransportHandler.toBytes(device.getShortAddress()).length;
        for (int i = 0; i < 6; i++){
            bytes[index++] = (byte) 0x00;
        }
        bytes[index++] = device.getEndpoint();
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = hue;
        bytes[index++] = sat;
        bytes[index++] = (byte) (0xFF & transition);
        bytes[index] = (byte) (0xFF & (transition >> 8 ) );

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    @Override
    public void addScene(Device device, byte state, byte data2, byte data3, byte data4, String sceneName, byte irId, int transition, byte funcId, String ip) {
        System.out.print("添加场景 => ");
        byte[] bytes = new byte[31 + sceneName.getBytes().length];

        int index = 0;
        bytes[index++] = (byte) (31 + sceneName.getBytes().length);
        bytes[index++] = (byte) 0x00;
        for (int i = 0; i < 4; i++){
            bytes[index++] = (byte) 0xFF;
        }
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x91;
        bytes[index++] = (byte) (23 + sceneName.getBytes().length);
        bytes[index++] = (byte) 0x02;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, 2);
        index = index + 2;
        for (int i = 0; i < 6; i++){
            bytes[index++] = (byte) 0x00;
        }
        bytes[index++] = device.getEndpoint();
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0x00;
        System.arraycopy(TransportHandler.toBytes(device.getDeviceId()), 0, bytes, index, TransportHandler.toBytes(device.getDeviceId()).length);
        index = index + TransportHandler.toBytes(device.getDeviceId()).length;
        bytes[index++] = state;
        bytes[index++] = data2;
        bytes[index++] = data3;
        bytes[index++] = data4;
        bytes[index++] = (byte)sceneName.getBytes().length;
        System.arraycopy(sceneName.getBytes(), 0, bytes, index, sceneName.getBytes().length);
        index = index + sceneName.getBytes().length;
        bytes[index++] = irId;
        bytes[index++] = (byte)(0xFF & transition);
        bytes[index] = funcId;

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get(ip).writeAndFlush(sendMessage);
    }

    @Override
    public void addScene(Device device, byte state, byte data2, byte data3, byte data4, String sceneName, byte irId, int transition) {
        System.out.print("添加场景 => ");
        byte[] bytes = new byte[30 + sceneName.getBytes().length];

        int index = 0;
        bytes[index++] = (byte) (30 + sceneName.getBytes().length);
        bytes[index++] = (byte) 0x00;
        for (int i = 0; i < 4; i++){
            bytes[index++] = (byte) 0xFF;
        }
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x91;
        bytes[index++] = (byte) (22 + sceneName.getBytes().length);
        bytes[index++] = (byte) 0x02;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index = index+TransportHandler.toBytes(device.getShortAddress()).length;
        for (int i = 0; i < 6; i++){
            bytes[index++] = (byte) 0x00;
        }
        bytes[index++] = device.getEndpoint();
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0x00;
        System.arraycopy(TransportHandler.toBytes(device.getDeviceId()), 0, bytes, index, TransportHandler.toBytes(device.getDeviceId()).length);
        index = index + TransportHandler.toBytes(device.getDeviceId()).length;
        bytes[index++] = state;
        bytes[index++] = data2;
        bytes[index++] = data3;
        bytes[index++] = data4;
        bytes[index++] = (byte)sceneName.getBytes().length;
        System.arraycopy(sceneName.getBytes(), 0, bytes, index, sceneName.getBytes().length);
        index = index + sceneName.getBytes().length;
        bytes[index++] = irId;
        bytes[index] = (byte)transition;

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    @Override
    public void callScene(String sceneId, String ip) {
        System.out.print("调用场景 => ");
        byte[] bytes = new byte[11];

        int index = 0;
        bytes[index++] = (byte) 0x0B;
        bytes[index++] = (byte) 0x00;
        for (int i = 0; i < 4; i++){
            bytes[index++] = (byte) 0xFF;
        }
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x92;
        bytes[index++] = (byte) 0x02;
        System.arraycopy(TransportHandler.toBytes(sceneId), 0, bytes, index, 2);

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get(ip).writeAndFlush(sendMessage);
    }

    @Override
    public void getDeviceInfo(Device device) {
        byte[] bytes = new byte[21];

        int index = 0;
        bytes[index++] = (byte) 0x15;
        bytes[index++] = (byte) 0x00;
        for (int i = 0; i < 4; i ++) {
            bytes[index++] = (byte) 0xFF;
        }
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x93;
        bytes[index++] = (byte) 0x0C;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, TransportHandler.toBytes(device.getShortAddress()).length);
        index = index + TransportHandler.toBytes(device.getShortAddress()).length;
        bytes[index++] = device.getEndpoint();
        for(int i = 0; i < 8; i++){
            bytes[index++] = (byte) 0x00;
        }
        bytes[index] = (byte) 0x00;

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    @Override
    public void changeSceneName(String sceneId, String name) {
        byte[] bytes = new byte[12 + name.getBytes().length];

        int index = 0;
        bytes[index++] = (byte) (0xFF & (12 + name.getBytes().length));
        bytes[index++] = (byte) ((0xFF00 & (12 + name.getBytes().length)) >> 8);
        for (int i = 0; i < 4; i++){
            bytes[index++] = (byte) 0xFF;
        }
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x8C;
        bytes[index++] = (byte) (3 + name.getBytes().length);
        System.arraycopy(TransportHandler.toBytes(sceneId), 0, bytes, index, 2);
        index = index + 2;
        bytes[index++] = (byte) name.getBytes().length;
        System.arraycopy(name.getBytes(), 0, bytes, index, name.getBytes().length);

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    @Override
    public void setReportTime(Device device, String clusterId, String attribId, String dataType, int transition) {
        byte[] bytes = new byte[30];

        int index = 0;
        bytes[index++] = (byte) 0x1E;
        bytes[index++] = (byte) 0x00;
        for (int i = 0; i < 4; i++){
            bytes[index++] = (byte) 0xFF;
        }
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x9E;
        bytes[index++] = (byte) 0x16;
        bytes[index++] = (byte) 0x02;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, 2);
        index = index + 2;
        for (int i = 0; i < 8; i++){

            bytes[index++] = (byte) 0x00;
        }
        bytes[index++] = device.getEndpoint();
        for (int i = 0; i < 2; i++){
            bytes[index++] = (byte) 0x00;
        }
        System.arraycopy(TransportHandler.toBytes(attribId), 0, bytes, index, 2);
        index = index + 2;
        System.arraycopy(TransportHandler.toBytes(attribId), 0, bytes, index, 2);
        index = index + 2;
        bytes[index++] = (byte) 0x21;
        bytes[index++] = (byte) (0xFF & transition);
        bytes[index] = (byte) ((0xFF00 & transition) >> 8);

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    @Override
    public void setColorTemperature(Device device, int value, int transition) {
        byte[] bytes = new byte[25];

        int index = 0;
        bytes[index++] = (byte) 0x18;
        bytes[index++] = (byte) 0x00;
        for (int i = 0; i < 4; i++){
            bytes[index++] = (byte) 0xFF;
        }
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0xA8;
        bytes[index++] = (byte) 0x0F;
        bytes[index++] = (byte) 0x02;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, 2);
        index = index + 2;
        for (int i = 0; i < 6; i++){
            bytes[index++] = (byte) 0x00;
        }
        bytes[index++] = device.getEndpoint();
        for (int i = 0; i < 2; i++){
            bytes[index++] = (byte) 0x00;
        }
        bytes[index++] = (byte) (0xFF & value);
        bytes[index++] = (byte) ((0xFF00 & value) >> 8);
        bytes[index++] = (byte) (0xFF & transition);
        bytes[index] = (byte) ((0xFF00 & transition) >> 8);

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    @Override
    public void setSwitchBindScene(Device device, String sceneId,String ip) {
        byte[] bytes = new byte[23];
        sceneId = sceneId.substring(0,2);

        int index = 0;
        bytes[index++] = (byte) 0x18;
        bytes[index++] = (byte) 0x00;
        for (int i = 0; i < 4; i++){
            bytes[index++] = (byte) 0xFF;
        }
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x8d;
        bytes[index++] = (byte) 0x0F;
        bytes[index++] = (byte) 0x02;
        System.arraycopy(TransportHandler.toBytes(device.getShortAddress()), 0, bytes, index, 2);
        index = index + 2;
        bytes[index++] = device.getEndpoint();
        bytes[index++] = (byte) 0x05;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0x01;
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0x10;
        bytes[index++] = (byte) 0x04;
        bytes[index++] = (byte) 0x04;
        bytes[index++] = (byte) 0xF0;
        bytes[index++] = (byte) 0xF0;
        System.arraycopy(TransportHandler.toBytes(sceneId), 0, bytes, index, 1);
        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get(ip).writeAndFlush(sendMessage);
    }

    @Override
    public void getBindRecord(Device device, String ip) {
        byte[] bytes = new byte[10];

        int index = 0;
        bytes[index++] = (byte) 0x0A;
        bytes[index++] = (byte) 0x00;
        for (int i = 0; i < 4; i++){
            bytes[index++] = (byte) 0xFF;
        }
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x8D;
        bytes[index++] = (byte) 0x01;
        bytes[index] = (byte) 0x08;
        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get(ip).writeAndFlush(sendMessage);
    }

    @Override
    public void cancelBindOfSwitchAndScene(Device device, String clusterId) {
//        byte[] bytes = new byte[35];
//
//        int index = 0;
//        bytes[index++] = (byte) 0x23;
//        bytes[index++] = (byte) 0x00;
//        for (int i = 0; i < 4; i++){
//            bytes[index++] = (byte) 0xFF;
//        }
//        bytes[index++] = (byte) 0xFE;
//        bytes[index++] = (byte) 0x96;
//        bytes[index++] = (byte) 0x00;
    }

    @Override
    public void setSwitchBindScene_CallBack() {

    }

    @Override
    public void getBindRecord_CallBack() {

    }

    @Override
    public void cancelBindOfSwitchAndScene_CallBack() {

    }

    @Override
    public void device_CallBack(Device device, String gatewayName, DeviceTokenRelationService deviceTokenRelationService) throws Exception {
        System.out.println(device.toString());
        DeviceTokenRelation deviceTokenRelation = deviceTokenRelationService.getRelotionByIEEEAndEndPoint(device.getIEEE(), Integer.parseInt(String.valueOf(device.getEndpoint())));
        if(deviceTokenRelation == null){
            String token = null;
            String type = DataService.deviceId2Type(device.getDeviceId());
            Integer deviceNumber = deviceTokenRelationService.getDeviceNumber();
            DeviceTokenRelation gateway = deviceTokenRelationService.getGateway(gatewayName);
            //httpControl.httplogin();
            if(!device.getSnid().equals("") && device.getSnid()!=null)
            {
                String id = httpControl.httpcreate(type+"_"+deviceNumber.toString(), gateway.getIEEE(),type, device.getSnid());
                token = httpControl.httpfind(id);

                DeviceTokenRelation newDeviceTokenRelation = new DeviceTokenRelation(device.getIEEE(), Integer.parseInt(String.valueOf(device.getEndpoint())), token, type, gatewayName, device.getShortAddress(), id);
                deviceTokenRelationService.addARelation(newDeviceTokenRelation);
                DataMessageClient.publishAttribute(token, device.toString());
            }

        }else{
            if(!device.getShortAddress().equals(deviceTokenRelation.getShortAddress())){
                deviceTokenRelationService.updateShortAddress(device.getShortAddress(), device.getIEEE());
                DataMessageClient.publishAttribute(deviceTokenRelation.getToken(), device.toString());
            }
            if(!gatewayName.equals(deviceTokenRelation.getGatewayName())){
                deviceTokenRelationService.updateGatewayName(gatewayName, device.getIEEE());
                String deviceJson = httpControl.httpGetDevice(deviceTokenRelation.getUuid());
                DeviceTokenRelation gatewayInfo = deviceTokenRelationService.getGateway(gatewayName);

                JsonObject jsonObject =(JsonObject) new JsonParser().parse(deviceJson);
                jsonObject.remove("parentDeviceId");
                jsonObject.addProperty("parentDeviceId",gatewayInfo.getUuid());

                httpControl.UpdateDevice(jsonObject.toString());
            }else{
                DataMessageClient.publishAttribute(deviceTokenRelation.getToken(), device.toString());
            }
        }

    }

    @Override
    public void gateway_CallBack(Gateway gateway){
        System.out.println(gateway.toString());
    }

    @Override
    public void deviceState_CallBack(Device device,DeviceTokenRelationService deviceTokenRelationService){
        System.out.println(device.getShortAddress()+"-"+device.getEndpoint()+":"+device.getState());
        DeviceTokenRelation deviceTokenRelation;
        try {
            deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(device.getShortAddress(), Integer.parseInt(String.valueOf(device.getEndpoint())));
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("status",device.getState());

            //System.out.println(jsonObject.toString());
            DataMessageClient.publishData(deviceTokenRelation.getToken(), jsonObject.toString());
        }catch (Exception e){
            System.out.println("数据表中无对应token"+e);
        }
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

    @Override
    public void deviceColourTemp_CallBack(String shortAddress, int endPoint, int colourTemp){
        System.out.println(shortAddress+"-"+endPoint+":"+colourTemp);
    }

    @Override
    public void group_CallBack(Group group){
        System.out.println(group.toString());
    }

    @Override
    public void groupMember_CallBack(String groupId, String[] shortAddress, int[] endPoint){
        System.out.println(groupId);
        for(int i=0;i<shortAddress.length;i++){
            System.out.println(shortAddress[i]+endPoint[i]);
        }
    }

    @Override
    public void scene_CallBack(Scene scene){
        System.out.println(scene.toString());
    }

    @Override
    public void sceneDetail_CallBack(String sceneId, String[] shortAddress, int[] endPoint, String[] deviceId, byte[] data1, byte[] data2, byte[] data3, byte[] data4, byte[] IRId, int[] delay){
        System.out.println(sceneId);
        for(int i=0;i<shortAddress.length;i++){
            System.out.println(shortAddress[i]+endPoint[i]+"|"+deviceId[i]+"|"+data1[i]+","+data2[i]+","+data3[i]+","+data4[i]+"|"+IRId[i]+"|"+delay[i]);
        }
    }

    @Override
    public void deleteSceneMember_CallBack(Scene scene){
        System.out.println(scene.toString());
    }

    @Override
    public void timerTask_CallBack(TimerTask timerTask){
        System.out.println(timerTask.toString());
    }

    @Override
    public void task_CallBack(Task task){
        System.out.println(task.toString());
    }

    @Override
    public void taskDeviceDetail_CallBack(TaskDeviceDetail taskDeviceDetail, String sceneId){
        System.out.println(taskDeviceDetail.toString());
    }

    @Override
    public void taskSceneDetail_CallBack(TaskSceneDetail taskSceneDetail, String sceneId){
        System.out.println(taskSceneDetail.toString());
    }

    @Override
    public void taskTimerDetail_CallBack(TaskTimerDetail taskTimerDetail, String sceneId){
        System.out.println(taskTimerDetail.toString());
    }

    @Override
    public void setGroupName_CallBack(Group group){
        System.out.println(group.toString());
    }

    @Override
    public void changeDeviceName_CallBack(String shortAddress, int endPoint, String name){
        System.out.println("shortAddress:" + shortAddress + " | "
                            + "endPoint:" + endPoint + " | "
                            + "newName:" + name);
    }

    @Override
    public void deleteDevice_CallBack(){
        System.out.println("delete succeed! ");
    }

    @Override
    public void setDeviceState_CallBack(){
        System.out.println("change Device Status succeed! ");
    }

    @Override
    public void setDeviceLevel_CallBack() {
        System.out.println("set device level succeed! ");
    }

    @Override
    public void setDeviceHueAndSat_CallBack() {
        System.out.println("set Device Hue and sat succeed!");
    }

    @Override
    public void addScene_CallBack(Scene scene, SceneService sceneService) {
        System.out.println(scene.toString());
        sceneService.updateScene(scene);

    }

    @Override
    public void callScene_CallBack() {
        System.out.println("call scene succeed! ");
    }

    @Override
    public void getDeviceInfo_CallBack(Device device, String data) {
        System.out.println(device.toString() + "data: "+ data               );
    }

    @Override
    public void changeSceneName_CallBack(Scene scene) {
        System.out.println(scene.toString());
    }

    @Override
    public void setReportTime_CallBack() {
        System.out.println("set report time succeed!");
    }

    @Override
    public void setColorTemperature_CallBack() {
        System.out.println("set device color and Temperature succeed!");
    }

    @Override
    public void data_CallBack(String shortAddress, int endPoint, Map<String,Double> data, DeviceTokenRelationService deviceTokenRelationService) throws Exception {
        DeviceTokenRelation deviceTokenRelation = deviceTokenRelationService.getRelotionBySAAndEndPoint(shortAddress, endPoint);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(data);
        DataMessageClient.publishData(deviceTokenRelation.getToken(),jsonStr);
    }
}
