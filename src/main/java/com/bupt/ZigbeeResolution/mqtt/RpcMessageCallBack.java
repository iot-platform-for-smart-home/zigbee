package com.bupt.ZigbeeResolution.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class RpcMessageCallBack implements MqttCallback{
	private String token;
	private RpcMqttClient rpcMqttClient;

	public RpcMessageCallBack(MqttClient rpcMqtt,String token){
		this.token = token;
		this.rpcMqttClient = new RpcMqttClient(token);
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
	}
}
