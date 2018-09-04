package com.bupt.ZigbeeResolution.method;

import com.bupt.ZigbeeResolution.data.*;

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

  void getSceneDetail(Scene scene);

  void deleteSceneMember(Scene scene,Device device);

  void getTimerTask();

  void getTask();

  public void device_CallBack(Device device);

  public void gateway_CallBack(Gateway gateway);

  public void deviceState_CallBack(Device device);

  public void deviceBright_CallBack(String shortAddress, int endPoint, int bright);

  public void deviceHue_CallBack(String shortAddress, int endPoint, int hue);

  public void deviceSaturation_CallBack(String shortAddress, int endPoint, int saturation);

  public void group_CallBack(Group group);

  public void groupMember_CallBack(String groupId, String[] shortAddress, int[] endPoint);

  public void scene_CallBack(Scene scene);

  public void sceneDetail_CallBack(String sceneId, String[] shortAddress, int[] endPoint, String[] deviceId, byte[] data1, byte[] data2, byte[] data3, byte[] data4, byte[] IRId, int[] delay);

  public void deleteSceneMember_CallBack(Scene scene);

  public void timerTask_CallBack(TimerTask timerTask);

  public void task_CallBack(Task task);
}
