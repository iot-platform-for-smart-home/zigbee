package com.bupt.ZigbeeResolution.service;

import com.bupt.ZigbeeResolution.data.Device;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DataService {
    private static List<Object> list = new LinkedList<Object>();
    private static boolean state;

    public static void cleatList(){
        list.clear();
    }

    public static void resolution(byte[] bytes) {
        System.out.println("进入");
        byte Response = bytes[0];
        switch (Response){
            case 0x01:
                Device device = new Device();
                Integer length = Integer.parseInt(String.valueOf(bytes[1]));
                System.out.println(length);
                device.setShortAddress(byte2HexStr(Arrays.copyOfRange(bytes, 2, 4)));
                device.setEndpoint(bytes[4]);
                device.setProfileId(byte2HexStr(Arrays.copyOfRange(bytes, 5, 7)));
                device.setDeviceId(byte2HexStr(Arrays.copyOfRange(bytes, 7, 9)));
                device.setState(bytes[9]==0x01);
                Integer nameLength = Integer.parseInt(String.valueOf(bytes[10]));
                System.out.println(nameLength);
                if(nameLength==0){
                    device.setName("");
                }else{
                    device.setName(byte2HexStr(Arrays.copyOfRange(bytes, 11, 11+nameLength)));
                }
                device.setOnlineState(bytes[11+nameLength]);
                device.setIEEE(byte2HexStr(Arrays.copyOfRange(bytes, 12+nameLength, 20+nameLength)));
                Integer snLength = Integer.parseInt(String.valueOf(bytes[20+nameLength]));
                System.out.println(snLength);
                if(snLength==0){
                    device.setSnid("");
                }else {
                    device.setSnid(byte2HexStr(Arrays.copyOfRange(bytes, 21+nameLength, 21+nameLength+snLength)));
                }
                device.setZoneType( byte2HexStr(Arrays.copyOfRange(bytes, 21+nameLength+snLength, 23+nameLength+snLength)));
                device.setElectric(Double.valueOf(String.valueOf(bytes[23+nameLength+snLength])));
                device.setRecentState(Arrays.copyOfRange(bytes, length-4, length));
                list.add(device);
                System.out.println("完成解析");
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

    public static List<Object> getList(){
        return list;
    }

    public static void setStatefalse(){
        state = false;
    }

    public static void setStatetrue(){
        state = true;
    }

    public static boolean getState(){
        return state;
    }

}
