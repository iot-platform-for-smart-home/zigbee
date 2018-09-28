package com.bupt.ZigbeeResolution.mqtt;

import com.bupt.ZigbeeResolution.data.Device;
import com.bupt.ZigbeeResolution.method.GatewayMethod;
import com.bupt.ZigbeeResolution.method.GatewayMethodImpl;
import com.bupt.ZigbeeResolution.service.GatewayGroupService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class RpcMessageCallBack implements MqttCallback{
	private String token;
	private GatewayGroupService gatewayGroupService;
	private RpcMqttClient rpcMqttClient;
	private GatewayMethod gatewayMethod = new GatewayMethodImpl();

	public RpcMessageCallBack(MqttClient rpcMqtt,String token, GatewayGroupService gatewayGroupService){
		this.token = token;
		this.rpcMqttClient = new RpcMqttClient(token, gatewayGroupService);
		this.gatewayGroupService = gatewayGroupService;
	}

	@Override
	public void connectionLost(Throwable arg0) {
		// TODO Auto-generated method stub
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//SecondActivity.wrapper.init();
		rpcMqttClient.init();
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void messageArrived(String topic, MqttMessage msg) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(msg);
		JsonObject jsonObject = new JsonParser().parse(new String(msg.getPayload())).getAsJsonObject();


		switch (jsonObject.get("serviceName").getAsString()){
			case "control switch":
				switch (jsonObject.get("methodName").getAsString()){
					case "setstate":
						try {
							Device controlDevice = new Device();
							controlDevice.setShortAddress(jsonObject.get("shortAddress").getAsString());
							controlDevice.setEndpoint(jsonObject.get("Endpoint").getAsByte());

							byte state;
							if (jsonObject.get("status").getAsString().equals("true")) {
								state = 0x01;
							} else {
								state = 0x00;
							}
							//System.out.println("进入控制");

							String ip = gatewayGroupService.getGatewayIp(controlDevice.getShortAddress(), Integer.parseInt(String.valueOf(controlDevice.getEndpoint())));
							if (ip == null) {
								System.out.println("Gateway offline");
							}

							System.out.println(ip);
							gatewayMethod.setDeviceState(controlDevice, state, ip);
						}catch (Exception e){
							System.out.println(e);
						}

						break;
				}
				break;

			case "control dimmableLight":
				switch (jsonObject.get("methodName").getAsString()){
					case "setbright":
						try {
							Device controlDevice = new Device();
							controlDevice.setShortAddress(jsonObject.get("shortAddress").getAsString());
							controlDevice.setEndpoint(jsonObject.get("Endpoint").getAsByte());

							byte bright;
							bright = (byte)(0xFF & jsonObject.get("bright").getAsInt());
							//System.out.println("进入控制");

							String ip = gatewayGroupService.getGatewayIp(controlDevice.getShortAddress(), Integer.parseInt(String.valueOf(controlDevice.getEndpoint())));
							if (ip == null) {
								System.out.println("Gateway offline");
							}

							System.out.println(ip);
							gatewayMethod.setDeviceLevel(controlDevice, bright, 0, ip);
						}catch (Exception e){
							System.out.println(e);
						}

						break;

				}
				break;
		}


	}
}
