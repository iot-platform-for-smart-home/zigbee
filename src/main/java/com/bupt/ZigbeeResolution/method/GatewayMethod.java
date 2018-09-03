package com.bupt.ZigbeeResolution.method;

import com.bupt.ZigbeeResolution.data.Device;
import com.bupt.ZigbeeResolution.data.Gateway;

public interface GatewayMethod {
  void getAllDevice() throws Exception;

  void getGatewayInfo() throws Exception;

  public void device_CallBack(Device device);

  public void gateway_CallBack(Gateway gateway);
}
