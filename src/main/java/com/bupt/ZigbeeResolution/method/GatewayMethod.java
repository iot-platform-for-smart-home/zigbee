package com.bupt.ZigbeeResolution.method;

import com.bupt.ZigbeeResolution.data.Device;
import com.bupt.ZigbeeResolution.data.Gateway;
import com.bupt.ZigbeeResolution.data.Group;
import com.bupt.ZigbeeResolution.data.Scene;

public interface GatewayMethod {
  void getAllDevice() throws Exception;

  void getGatewayInfo() throws Exception;

  void getDeviceState(Device device);

  void getDeviceBright(Device device);

  void getDeviceHue(Device device);

  void getDeviceSaturation(Device device);

  void getGroup();

  void getGroupMember(Group group);

  void getScene();

  public void device_CallBack(Device device);

  public void gateway_CallBack(Gateway gateway);

  public void deviceState_CallBack(Device device);

  public void deviceBright_CallBack(String shortAddress, int endPoint, int bright);

  public void deviceHue_CallBack(String shortAddress, int endPoint, int hue);

  public void deviceSaturation_CallBack(String shortAddress, int endPoint, int saturation);

  public void group_CallBack(Group group);

  public void groupMember_CallBack(String groupId, String[] shortAddress, int[] endPoint);

  public void scene_CallBack(Scene scene);
}
