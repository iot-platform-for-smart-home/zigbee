package com.bupt.ZigbeeResolution.service;

import com.bupt.ZigbeeResolution.data.Device;
import com.bupt.ZigbeeResolution.data.Gateway;
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
