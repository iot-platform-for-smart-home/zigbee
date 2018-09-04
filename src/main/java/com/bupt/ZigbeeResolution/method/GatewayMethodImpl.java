package com.bupt.ZigbeeResolution.method;

import com.bupt.ZigbeeResolution.data.Device;
import com.bupt.ZigbeeResolution.data.Gateway;
import com.bupt.ZigbeeResolution.data.Group;
import com.bupt.ZigbeeResolution.data.Scene;
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
        byte[] bytes = new byte[TransportHandler.toBytes(scene.getSceneName()).length+12];

        int index = 0;
        bytes[index++] = (byte) (0xFF & (TransportHandler.toBytes(scene.getSceneName()).length+12));
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF ;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x8A;
        bytes[index++] = (byte) (0xFF & (TransportHandler.toBytes(scene.getSceneName()).length+4));
        System.arraycopy(TransportHandler.toBytes(scene.getSceneId()), 0, bytes, index, TransportHandler.toBytes(scene.getSceneId()).length);
        index=index+TransportHandler.toBytes(scene.getSceneId()).length;
        bytes[index++] = (byte) (0xFF & (TransportHandler.toBytes(scene.getSceneName()).length));
        System.arraycopy(scene.getSceneName().getBytes(), 0, bytes, index, scene.getSceneName().getBytes().length);

        sendMessage = TransportHandler.getSendContent(12, bytes);
        SocketServer.getMap().get("10.108.219.22").writeAndFlush(sendMessage);
    }

    public void deleteSceneMember(Scene scene, Device device){
        byte[] bytes = new byte[TransportHandler.toBytes(scene.getSceneName()).length+23];

        int index = 0;
        bytes[index++] = (byte) (0xFF & (TransportHandler.toBytes(scene.getSceneName()).length+23));
        bytes[index++] = (byte) 0x00;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFF ;
        bytes[index++] = (byte) 0xFF;
        bytes[index++] = (byte) 0xFE;
        bytes[index++] = (byte) 0x8B;
        bytes[index++] = (byte) (0xFF & (TransportHandler.toBytes(scene.getSceneName()).length+14));
        bytes[index++] = (byte) 0x02;
        System.arraycopy(TransportHandler.toBytes(device.getDeviceId()), 0, bytes, index, TransportHandler.toBytes(device.getDeviceId()).length);
        index=index+TransportHandler.toBytes(device.getDeviceId()).length;
        for(int i=0;i<6;i++){
            bytes[index++] = (byte) 0x00;
        }
        bytes[index++] = device.getEndpoint();
        for(int i=0;i<2;i++){
            bytes[index++] = (byte) 0x00;
        }
        bytes[index++] = (byte) (0xFF & (TransportHandler.toBytes(scene.getSceneName()).length));
        System.arraycopy(scene.getSceneName().getBytes(), 0, bytes, index, scene.getSceneName().getBytes().length);

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
}
