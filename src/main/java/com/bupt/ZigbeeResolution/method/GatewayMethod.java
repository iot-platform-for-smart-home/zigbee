package com.bupt.ZigbeeResolution.method;

import com.bupt.ZigbeeResolution.data.Device;
import com.bupt.ZigbeeResolution.data.Gateway;

public interface GatewayMethod {
  void getAllDevice() throws Exception;

  void getGatewayInfo() throws Exception;

  void getDeviceState(Device device);

  void getDeviceBright(Device device);

  void getDeviceHue(Device device);

  void getDeviceSaturation(Device device);

  public void device_CallBack(Device device);

  public void gateway_CallBack(Gateway gateway);

  public void deviceState_CallBack(Device device);

  public void deviceBright_CallBack(String shortAddress, int endPoint, int bright);

  public void deviceHue_CallBack(String shortAddress, int endPoint, int hue);

  public void deviceSaturation_CallBack(String shortAddress, int endPoint, int saturation);
}
