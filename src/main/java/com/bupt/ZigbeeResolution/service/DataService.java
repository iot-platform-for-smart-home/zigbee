package com.bupt.ZigbeeResolution.service;

import com.bupt.ZigbeeResolution.data.*;
import com.bupt.ZigbeeResolution.method.GatewayMethod;
import com.bupt.ZigbeeResolution.method.GatewayMethodImpl;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DataService {
    private static List<Object> list = new LinkedList<Object>();

    private GatewayMethod gatewayMethod = new GatewayMethodImpl();
    public static void cleatList(){
        list.clear();
    }
    Integer length;
    String shortAddress;
    int endPoint;
    String[] shortAddresses;
    int[] endPoints;


    public void resolution(byte[] bytes) {
        System.out.println("进入");
        byte Response = bytes[0];
        switch (Response){
            case 0x01:
                Device device = new Device();
                length = Integer.parseInt(String.valueOf(bytes[1]));
                device.setShortAddress(byte2HexStr(Arrays.copyOfRange(bytes, 2, 4)));
                device.setEndpoint(bytes[4]);
                device.setProfileId(byte2HexStr(Arrays.copyOfRange(bytes, 5, 7)));
                device.setDeviceId(byte2HexStr(Arrays.copyOfRange(bytes, 7, 9)));
                device.setState(bytes[9]==0x01);
                Integer nameLength = Integer.parseInt(String.valueOf(bytes[10]));
                if(nameLength==0){
                    device.setName("");
                }else{
                    device.setName(bytesToAscii(Arrays.copyOfRange(bytes, 11, 11+nameLength)));
                }
                device.setOnlineState(bytes[11+nameLength]);
                device.setIEEE(byte2HexStr(Arrays.copyOfRange(bytes, 12+nameLength, 20+nameLength)));
                Integer snLength = Integer.parseInt(String.valueOf(bytes[20+nameLength]));
                if(snLength==0){
                    device.setSnid("");
                }else {
                    device.setSnid(byte2HexStr(Arrays.copyOfRange(bytes, 21+nameLength, 21+nameLength+snLength)));
                }
                device.setZoneType( byte2HexStr(Arrays.copyOfRange(bytes, 21+nameLength+snLength, 23+nameLength+snLength)));
                device.setElectric(Double.valueOf(String.valueOf(bytes[23+nameLength+snLength])));
                device.setRecentState(Arrays.copyOfRange(bytes, length-4, length));
                System.out.println("完成解析");
                gatewayMethod.device_CallBack(device);
                break;

            case 0x15:
                Gateway gateway = new Gateway();
                length = Integer.parseInt(String.valueOf(bytes[1]));
                gateway.setVersion(bytesToAscii(Arrays.copyOfRange(bytes, 2, 7)));
                gateway.setSnid(byte2HexStr(Arrays.copyOfRange(bytes, 7, 11)));
                gateway.setUsername(bytesToAscii(Arrays.copyOfRange(bytes, 11, 31)));
                gateway.setPassword(bytesToAscii(Arrays.copyOfRange(bytes, 31, 51)));
                gateway.setDeviceNumber(Integer.parseInt(String.valueOf(bytes[51])));
                gateway.setGroupNumber(Integer.parseInt(String.valueOf(bytes[52])));
                gateway.setTimerNumber(Integer.parseInt(String.valueOf(bytes[53])));
                gateway.setSceneNumber(Integer.parseInt(String.valueOf(bytes[54])));
                gateway.setMissionNumber(Integer.parseInt(String.valueOf(bytes[55])));
                gateway.setCompileVersionNumber(byte2HexStr(Arrays.copyOfRange(bytes, 61, 65)));
                System.out.println("完成解析");
                gatewayMethod.gateway_CallBack(gateway);
                break;

            case 0x07:
                Device stateDevice = new Device();
                length = Integer.parseInt(String.valueOf(bytes[1]));
                stateDevice.setShortAddress(byte2HexStr(Arrays.copyOfRange(bytes, 2, 4)));
                stateDevice.setEndpoint(bytes[4]);
                stateDevice.setState(bytes[5]==0x01);
                System.out.println("完成解析");
                gatewayMethod.deviceState_CallBack(stateDevice);
                break;

            case 0x08:
                length = Integer.parseInt(String.valueOf(bytes[1]));
                shortAddress = byte2HexStr(Arrays.copyOfRange(bytes, 2, 4));
                endPoint = Integer.parseInt(String.valueOf(bytes[4]));
                int bright = Integer.parseInt(String.valueOf(bytes[5]));
                System.out.println("完成解析");
                gatewayMethod.deviceBright_CallBack(shortAddress, endPoint, bright);
                break;

            case 0x09:
                length = Integer.parseInt(String.valueOf(bytes[1]));
                shortAddress = byte2HexStr(Arrays.copyOfRange(bytes, 2, 4));
                endPoint = Integer.parseInt(String.valueOf(bytes[4]));
                int hue = Integer.parseInt(String.valueOf(bytes[5]));
                System.out.println("完成解析");
                gatewayMethod.deviceBright_CallBack(shortAddress, endPoint, hue);
                break;

            case 0x0A:
                length = Integer.parseInt(String.valueOf(bytes[1]));
                shortAddress = byte2HexStr(Arrays.copyOfRange(bytes, 2, 4));
                endPoint = Integer.parseInt(String.valueOf(bytes[4]));
                int saturation = Integer.parseInt(String.valueOf(bytes[5]));
                System.out.println("完成解析");
                gatewayMethod.deviceBright_CallBack(shortAddress, endPoint, saturation);
                break;

            case 0x0C:
                Group group = new Group();
                length = Integer.parseInt(String.valueOf(bytes[1]));
                group.setGroupId(byte2HexStr(Arrays.copyOfRange(bytes, 2, 4)));
                Integer groupNameLength = Integer.parseInt(String.valueOf(bytes[4]));
                if(groupNameLength==0){
                    group.setGroupName("");
                }else {
                    group.setGroupName(bytesToAscii(Arrays.copyOfRange(bytes, 5, 5+groupNameLength)));
                }
                System.out.println("完成解析");
                gatewayMethod.group_CallBack(group);
                break;

            case 0x0B:
                length = Integer.parseInt(String.valueOf(bytes[1]));
                String groupId = byte2HexStr(Arrays.copyOfRange(bytes, 2, 4));
                int memberLength = Integer.parseInt(String.valueOf(bytes[4]));
                shortAddresses = new String[memberLength];
                endPoints = new int[memberLength];
                for(int i=0;i<memberLength;i++){
                    shortAddresses[i] =  byte2HexStr(Arrays.copyOfRange(bytes, 5+3*i, 7+3*i));
                    endPoints[i] = Integer.parseInt(String.valueOf(bytes[7+3*i]));
                }
                System.out.println("完成解析");
                gatewayMethod.groupMember_CallBack(groupId, shortAddresses, endPoints);
                break;

            case 0x0E:
                Scene scene = new Scene();
                length = Integer.parseInt(String.valueOf(bytes[1]));
                scene.setSceneId(byte2HexStr(Arrays.copyOfRange(bytes, 2, 4)));
                int sceneNameLength = Integer.parseInt(String.valueOf(bytes[4]));
                if(sceneNameLength==0){
                    scene.setSceneName("");
                }else {
                    scene.setSceneName(bytesToAscii(Arrays.copyOfRange(bytes, 5, 5+sceneNameLength)));
                }
                scene.setSceneNumber(Integer.parseInt(String.valueOf(bytes[5+sceneNameLength])));
                System.out.println("完成解析");
                gatewayMethod.scene_CallBack(scene);
                break;

            case 0x20:
                length = Integer.parseInt(String.valueOf(bytes[1]));
                String sceneId = byte2HexStr(Arrays.copyOfRange(bytes, 2, 4));
                int deviceCount = Integer.parseInt(String.valueOf(bytes[4]));

                shortAddresses = new String[deviceCount];
                endPoints = new int[deviceCount];
                String[] deviceId = new String[deviceCount];
                byte[] data1 = new byte[deviceCount];
                byte[] data2 = new byte[deviceCount];
                byte[] data3 = new byte[deviceCount];
                byte[] data4 = new byte[deviceCount];
                byte[] IRId = new byte[deviceCount];
                int[] delay = new int[deviceCount];

                for(int i=0;i<deviceCount;i++){
                    shortAddresses[i] =  byte2HexStr(Arrays.copyOfRange(bytes, 5+12*i, 7+12*i));
                    endPoints[i] = Integer.parseInt(String.valueOf(bytes[7+12*i]));
                    deviceId[i] = byte2HexStr(Arrays.copyOfRange(bytes, 8+12*i, 10+12*i));
                    data1[i] = bytes[10+12*i];
                    data2[i] = bytes[11+12*i];
                    data3[i] = bytes[12+12*i];
                    data4[i] = bytes[13+12*i];
                    IRId[i] = bytes[14+12*i];
                    delay[i] = Integer.parseInt(String.valueOf(bytes[15+12*i]));
                }

                System.out.println("完成解析");
                gatewayMethod.sceneDetail_CallBack(sceneId, shortAddresses, endPoints, deviceId, data1, data2, data3, data4, IRId, delay);
                break;

            case 0x21:
                Scene deleteScene = new Scene();
                length = Integer.parseInt(String.valueOf(bytes[1]));
                deleteScene.setSceneId(byte2HexStr(Arrays.copyOfRange(bytes, 2, 4)));
                int deleteSceneNameLength = Integer.parseInt(String.valueOf(bytes[4]));
                if(deleteSceneNameLength==0){
                    deleteScene.setSceneName("");
                }else {
                    deleteScene.setSceneName(bytesToAscii(Arrays.copyOfRange(bytes, 5, 5+deleteSceneNameLength)));
                }
                System.out.println("完成解析");
                gatewayMethod.scene_CallBack(deleteScene);
                break;

            case 0x11:
                TimerTask timerTask = new TimerTask();
                length = Integer.parseInt(String.valueOf(bytes[1]));
                timerTask.setTaskId(Integer.parseInt(String.valueOf(bytes[2])));
                timerTask.setAddressMode(Integer.parseInt(String.valueOf(bytes[3])));
                timerTask.setShortAddress(byte2HexStr(Arrays.copyOfRange(bytes, 4, 6)));
                timerTask.setEndPoint(bytes[6]);
                timerTask.setDay(Integer.parseInt(String.valueOf(bytes[7])));
                timerTask.setHour(Integer.parseInt(String.valueOf(bytes[8])));
                timerTask.setMinute(Integer.parseInt(String.valueOf(bytes[9])));
                timerTask.setSecond(Integer.parseInt(String.valueOf(bytes[10])));
                timerTask.setTaskMode(bytes[11]);
                timerTask.setData1(bytes[12]);
                timerTask.setData2(bytes[13]);
                System.out.println("完成解析");
                gatewayMethod.timerTask_CallBack(timerTask);
                break;

            case 0x25:
                Task task = new Task();
                length = Integer.parseInt(String.valueOf(bytes[1]));
                task.setTaskType(bytes[2]);
                task.setTaskId(byte2HexStr(Arrays.copyOfRange(bytes, 3, 5)));
                int taskNameLength = Integer.parseInt(String.valueOf(bytes[5]));
                if(taskNameLength==0){
                    task.setTaskName("");
                }else {
                    task.setTaskName(bytesToAscii(Arrays.copyOfRange(bytes, 6, 6+taskNameLength)));
                }
                task.setTaskNumber(Integer.parseInt(String.valueOf(bytes[6+taskNameLength])));
                System.out.println("完成解析");
                gatewayMethod.task_CallBack(task);
                break;
        }
        System.out.println("完成");
    }

    public static String byte2HexStr(byte[] b) {
        String stmp = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
//			sb.append(" ");
        }
        return sb.toString().toUpperCase().trim();
    }

    public static String bytesToAscii(byte[] bytes, int offset, int dateLen) {
        if ((bytes == null) || (bytes.length == 0) || (offset < 0) || (dateLen <= 0)) {
            return null;
        }
        if ((offset >= bytes.length) || (bytes.length - offset < dateLen)) {
            return null;
        }

        String asciiStr = null;
        byte[] data = new byte[dateLen];
        System.arraycopy(bytes, offset, data, 0, dateLen);
        try {
            asciiStr = new String(data, "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
        }
        return asciiStr;
    }

    public static String bytesToAscii(byte[] bytes) {
        return bytesToAscii(bytes, 0, bytes.length);
    }


    public static List<Object> getList(){
        return list;
    }
}
