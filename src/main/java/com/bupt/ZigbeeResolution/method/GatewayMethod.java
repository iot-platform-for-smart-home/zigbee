package com.bupt.ZigbeeResolution.method;

import com.bupt.ZigbeeResolution.data.Device;

public interface GatewayMethod {
  void getAllDevice() throws Exception;

  void getGatewayInfo() throws Exception;

  public void device_CallBack(Device device);
}
