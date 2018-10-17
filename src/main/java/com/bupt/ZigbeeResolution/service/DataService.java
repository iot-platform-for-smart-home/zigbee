package com.bupt.ZigbeeResolution.service;

import com.bupt.ZigbeeResolution.data.*;
import com.bupt.ZigbeeResolution.method.GatewayMethod;
import com.bupt.ZigbeeResolution.method.GatewayMethodImpl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
    int taskNameLength;



    public void resolution(byte[] bytes, String gatewayName, DeviceTokenRelationService deviceTokenRelationService, SceneService sceneService) throws Exception {
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
                    device.setSnid(bytesToAscii(Arrays.copyOfRange(bytes, 21+nameLength, 21+nameLength+snLength)));
                }
                device.setZoneType( byte2HexStr(Arrays.copyOfRange(bytes, 21+nameLength+snLength, 23+nameLength+snLength)));
                device.setElectric(Double.valueOf(String.valueOf(bytes[23+nameLength+snLength])));
                device.setRecentState(Arrays.copyOfRange(bytes, length-4, length));
                System.out.println("完成解析");
                gatewayMethod.device_CallBack(device, gatewayName, deviceTokenRelationService);
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
                gatewayMethod.deviceState_CallBack(stateDevice,deviceTokenRelationService);
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

            case 0x0D:
                Scene scene = new Scene();
                scene.setSceneId(byte2HexStr(new byte[]{bytes[3], bytes[4]}));
                int nameLen = (int) bytes[5];
                byte[] nameByte = new byte[nameLen];
                System.arraycopy(bytes,6, nameByte, 0, nameLen);
                scene.setSceneName(byte2HexStr(nameByte));
                System.out.println("完成解析");
                // 添加场景,修改场景名的返回值一样
                gatewayMethod.addScene_CallBack(scene,sceneService);

            case 0x0E:
                scene = new Scene();
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
                taskNameLength = Integer.parseInt(String.valueOf(bytes[5]));
                if(taskNameLength==0){
                    task.setTaskName("");
                }else {
                    task.setTaskName(bytesToAscii(Arrays.copyOfRange(bytes, 6, 6+taskNameLength)));
                }
                System.out.println("完成解析");
                gatewayMethod.task_CallBack(task);
                break;

            case 0x24:
                TaskTimerDetail taskTimerDetail = new TaskTimerDetail();
                TaskSceneDetail taskSceneDetail = new TaskSceneDetail();
                TaskDeviceDetail taskDeviceDetail = new TaskDeviceDetail();
                String taskSceneId;
                length = Integer.parseInt(String.valueOf(bytes[1]));

                switch (bytes[3]){
                    case 0x01:
                        taskTimerDetail.setTaskType(bytes[2]);
                        taskTimerDetail.setDay(Integer.parseInt(String.valueOf(bytes[4])));
                        taskTimerDetail.setHour(Integer.parseInt(String.valueOf(bytes[5])));
                        taskTimerDetail.setMinute(Integer.parseInt(String.valueOf(bytes[6])));
                        taskTimerDetail.setSecond(Integer.parseInt(String.valueOf(bytes[7])));
                        taskSceneId = byte2HexStr(Arrays.copyOfRange(bytes, 24, 26));
                        taskTimerDetail.setIsAlarm(bytes[43]);
                        taskTimerDetail.setIsAble(bytes[44]);
                        taskNameLength = Integer.parseInt(String.valueOf(bytes[49]));
                        if(taskNameLength==0){
                            taskTimerDetail.setTaskName("");
                        }else {
                            taskTimerDetail.setTaskName(bytesToAscii(Arrays.copyOfRange(bytes, 50, 50+taskNameLength)));
                        }
                        taskTimerDetail.setTaskId(byte2HexStr(Arrays.copyOfRange(bytes, 50+taskNameLength, 52+taskNameLength)));

                        System.out.println("完成解析");
                        gatewayMethod.taskTimerDetail_CallBack(taskTimerDetail, taskSceneId);
                        break;
                    case 0x02:
                        taskSceneDetail.setTaskType(bytes[2]);
                        taskSceneDetail.setSceneId(byte2HexStr(Arrays.copyOfRange(bytes, 4, 6)));
                        taskSceneId = byte2HexStr(Arrays.copyOfRange(bytes, 24, 26));
                        taskSceneDetail.setIsAlarm(bytes[43]);
                        taskSceneDetail.setIsAble(bytes[44]);
                        taskNameLength = Integer.parseInt(String.valueOf(bytes[49]));
                        if(taskNameLength==0){
                            taskSceneDetail.setTaskName("");
                        }else {
                            taskSceneDetail.setTaskName(bytesToAscii(Arrays.copyOfRange(bytes, 50, 50+taskNameLength)));
                        }
                        taskSceneDetail.setTaskId(byte2HexStr(Arrays.copyOfRange(bytes, 50+taskNameLength, 52+taskNameLength)));

                        System.out.println("完成解析");
                        gatewayMethod.taskSceneDetail_CallBack(taskSceneDetail, taskSceneId);
                        break;
                    case 0x03:
                        taskDeviceDetail.setTaskType(bytes[2]);
                        taskDeviceDetail.setShortAddress(byte2HexStr(Arrays.copyOfRange(bytes, 4, 6)));
                        taskDeviceDetail.setDeviceId(byte2HexStr(Arrays.copyOfRange(bytes, 6, 8)));
                        taskDeviceDetail.setEndPoint(bytes[8]);
                        taskDeviceDetail.setCondition1(bytes[9]);
                        taskDeviceDetail.setData1(bytesToInt(Arrays.copyOfRange(bytes, 10, 14)));
                        if(bytes[14]!=0x00){
                            taskDeviceDetail.setCondition2(bytes[14]);
                            taskDeviceDetail.setData2(bytesToInt(Arrays.copyOfRange(bytes, 15, 19)));
                        }
                        taskSceneId = byte2HexStr(Arrays.copyOfRange(bytes, 24, 26));
                        taskDeviceDetail.setIsAlarm(bytes[43]);
                        taskDeviceDetail.setIsAble(bytes[44]);
                        taskNameLength = Integer.parseInt(String.valueOf(bytes[49]));
                        if(taskNameLength==0){
                            taskDeviceDetail.setTaskName("");
                        }else {
                            taskDeviceDetail.setTaskName(bytesToAscii(Arrays.copyOfRange(bytes, 50, 50+taskNameLength)));
                        }
                        taskDeviceDetail.setTaskId(byte2HexStr(Arrays.copyOfRange(bytes, 50+taskNameLength, 52+taskNameLength)));

                        System.out.println("完成解析");
                        gatewayMethod.taskDeviceDetail_CallBack(taskDeviceDetail, taskSceneId);
                        break;
                }
                break;

            case 0x27:
                length = Integer.parseInt(String.valueOf(bytes[1]));
                shortAddress = byte2HexStr(Arrays.copyOfRange(bytes, 2, 4));
                endPoint = Integer.parseInt(String.valueOf(bytes[4]));
                int colourTemp = (int) ((bytes[5] & 0xFF) | ((bytes[6] & 0xFF)<<8));

                System.out.println("完成解析");
                gatewayMethod.deviceColourTemp_CallBack(shortAddress, endPoint, colourTemp);
                break;


            case 0x29:
                length = Integer.parseInt(String.valueOf(bytes[1]));
                switch(bytes[3]){
                    // 更改设备名返回值
                    case 0x03:
                        shortAddress = byte2HexStr(Arrays.copyOfRange(bytes, 4, 6));
                        endPoint = Integer.parseInt(String.valueOf(bytes[6]));
                        String name = bytesToAscii(bytes, 8, Integer.parseInt(String.valueOf(bytes[7])));

                        System.out.println("完成解析");
                        gatewayMethod.changeDeviceName_CallBack(shortAddress, endPoint, name);
                        break;

                    case 0x04:
                        switch(bytes[4]){
                            case (byte) 0x95:
                                gatewayMethod.deleteDevice_CallBack();
                                break;
                            case (byte) 0x82:
                                gatewayMethod.setDeviceState_CallBack();
                                break;
                            case (byte) 0x83:
                                gatewayMethod.setDeviceLevel_CallBack();
                                break;
                            case (byte) 0x84:
                                gatewayMethod.setDeviceHueAndSat_CallBack();
                                break;
                            case (byte) 0x92:
                                gatewayMethod.callScene_CallBack();
                                break;
                            case (byte) 0x9E:
                                gatewayMethod.setReportTime_CallBack();
                                break;
                            case (byte) 0xA8:
                                gatewayMethod.setColorTemperature_CallBack();
                                break;
                        }
                    case (byte)0x93:
                        device = new Device();
                        device.setShortAddress(byte2HexStr(new byte[]{bytes[5], bytes[6]}));
                        device.setEndpoint(bytes[7]);
                        byte[] data = new byte[4];
                        System.arraycopy(bytes, 8, data, 0, 4);
                        String dataStr = byte2HexStr(data);
                        gatewayMethod.getDeviceInfo_CallBack(device, dataStr);
                        break;
                }
                break;

            case (byte)0xAF:
                Group newGroupName = new Group();
                length = Integer.parseInt(String.valueOf(bytes[1]));
                newGroupName.setGroupId(byte2HexStr(Arrays.copyOfRange(bytes, 2, 4)));
                Integer newGroupNameLength = Integer.parseInt(String.valueOf(bytes[4]));
                if(newGroupNameLength==0){
                    newGroupName.setGroupName("");
                }else {
                    newGroupName.setGroupName(bytesToAscii(Arrays.copyOfRange(bytes, 5, 5+newGroupNameLength)));
                }
                System.out.println("完成解析");
                gatewayMethod.setGroupName_CallBack(newGroupName);
                break;

            case 0x70:
                //TODO 设备主动上报（目前只做实验室温湿度传感器和PM2.5传感器）
                Double temperature;
                Integer humidity;
                Integer pm;
                Integer human;
                Map<String, Double> data = new ConcurrentHashMap<String, Double>();

                length = Integer.parseInt(String.valueOf(bytes[1]));
                String shortAddress = byte2HexStr(Arrays.copyOfRange(bytes, 2, 4));
                Integer endPoint = Integer.parseInt(String.valueOf(bytes[4]));
                String clusterId = byte2HexStr(Arrays.copyOfRange(bytes, 5, 7));
                switch(clusterId){
                    case "0204":
                        for(int i = 0; i<Integer.parseInt(String.valueOf(bytes[7])); i++){
                            if(byte2HexStr(Arrays.copyOfRange(bytes, 8+i*5, 10+i*5)).equals("0000")){
                                if(bytes[10+i*5] == 0x29) {
                                    //System.out.println(dataBytesToInt(Arrays.copyOfRange(bytes, 11+i*5, 13+i*5)));
                                    BigDecimal b = new BigDecimal((double)dataBytesToInt(Arrays.copyOfRange(bytes, 11+i*5, 13+i*5))/(double) 100);
                                    temperature = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                                    //System.out.println(temperature);
                                    data.put("temperature", temperature);
                                }
                            }else if(byte2HexStr(Arrays.copyOfRange(bytes, 8+i*5, 10+i*5)).equals("1100")){
                                if(bytes[10+i*5] == 0x29) {
                                    humidity = dataBytesToInt(Arrays.copyOfRange(bytes, 11+i*5, 13+i*5));
                                    data.put("humidity" , humidity.doubleValue());
                                }
                            }
                        }

                        break;
                    case "1504":
                        for(int i = 0; i<Integer.parseInt(String.valueOf(bytes[7])); i++) {
                            if (byte2HexStr(Arrays.copyOfRange(bytes, 8 + i * 5, 10 + i * 5)).equals("0000")) {
                                if (bytes[10 + i * 5] == 0x21) {
                                    pm = dataBytesToInt(Arrays.copyOfRange(bytes, 11+i*5, 13+i*5));
                                    data.put("PM2.5", pm.doubleValue());
                                }
                            }
                        }
                        break;

                    case "0604":
                        for(int i = 0; i<Integer.parseInt(String.valueOf(bytes[7])); i++) {
                            if (byte2HexStr(Arrays.copyOfRange(bytes, 8 + i * 5, 10 + i * 5)).equals("0000")) {
                                if (bytes[10 + i * 5] == 0x21) {
                                    human = Integer.parseInt(String.valueOf(bytes[11+i*5]));
                                    data.put("PIR_status", human.doubleValue());
                                }
                            }
                        }
                        break;
                    case "EEFB":
                        break;
                }
                gatewayMethod.data_CallBack(shortAddress, endPoint, data, deviceTokenRelationService);
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

    public static int bytesToInt(byte[] src) {
        int value;
        value = (int) ((src[0] & 0xFF)
                | ((src[1] & 0xFF)<<8)
                | ((src[2] & 0xFF)<<16)
                | ((src[3] & 0xFF)<<24));
        return value;
    }

    public static int dataBytesToInt(byte[] src) {
        int value;
        value = (int) ((src[0] & 0xFF)
                | ((src[1] & 0xFF)<<8));
        return value;
    }

    public static String deviceId2Type(String deviceId){
        String type = null;
        switch (deviceId){
            case "0000":
                break;
            case "0100":
                break;
            case "0200":
                type = "switch";
                break;
            case "0300":
                break;
            case "0400":
                type = "sceneSelector";
                break;
            case "0500":
                break;
            case "0600":
                break;
            case "0700":
                break;
            case "0800":
                break;
            case "0900":
                type = "outlet";
                break;
            case "0101":
                type = "dimmableLight";
                break;
            case "0203":
                type = "temperature";
                break;
            case "0903":
                type = "PM2.5";
                break;
            case "0204":
                type = "IASZone";
                break;
        }
        return type;
    }
}
