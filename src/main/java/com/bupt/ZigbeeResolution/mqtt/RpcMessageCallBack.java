package com.bupt.ZigbeeResolution.mqtt;

import com.bupt.ZigbeeResolution.data.Device;
import com.bupt.ZigbeeResolution.method.GatewayMethod;
import com.bupt.ZigbeeResolution.method.GatewayMethodImpl;
import com.bupt.ZigbeeResolution.service.GatewayGroupService;
import com.bupt.ZigbeeResolution.transform.TransportHandler;
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

	public RpcMessageCallBack(MqttClient rpcMqtt,String token, GatewayGroupService gatewayGroupService, String gatewayName){
		this.token = token;
		this.rpcMqttClient = new RpcMqttClient(gatewayName,token, gatewayGroupService);
		this.gatewayGroupService = gatewayGroupService;
	}

	@Override
	public void connectionLost(Throwable arg0) {
		//SecondActivity.wrapper.init();
		while (!rpcMqttClient.init()){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
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
		Integer position = topic.lastIndexOf("/");
		Integer requestId = Integer.parseInt(topic.substring(position+1));
		System.out.println(requestId);


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
								//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"error");
							}

							System.out.println(ip);
							gatewayMethod.setDeviceState(controlDevice, state, ip);
							//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"success");
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
							bright = (byte)(0xFF & Integer.parseInt(jsonObject.get("bright").getAsString()));
							//System.out.println("进入控制");

							String ip = gatewayGroupService.getGatewayIp(controlDevice.getShortAddress(), Integer.parseInt(String.valueOf(controlDevice.getEndpoint())));
							if (ip == null) {
								System.out.println("Gateway offline");
								//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"error");
							}

							System.out.println(ip);
							gatewayMethod.setDeviceLevel(controlDevice, bright, 0, ip);
							//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"success");
						}catch (Exception e){
							System.out.println(e);
						}

						break;

				}
				break;

			case "control curtain":
				switch (jsonObject.get("methodName").getAsString()){
					case "setstate":
						try {
							Device controlDevice = new Device();
							controlDevice.setShortAddress(jsonObject.get("shortAddress").getAsString());
							controlDevice.setEndpoint(jsonObject.get("Endpoint").getAsByte());

							byte state;
							state = (byte)(0xFF & Integer.parseInt(jsonObject.get("status").getAsString()));
							//System.out.println("进入控制");

							String ip = gatewayGroupService.getGatewayIp(controlDevice.getShortAddress(), Integer.parseInt(String.valueOf(controlDevice.getEndpoint())));
							if (ip == null) {
								System.out.println("Gateway offline");
								//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"error");
							}

							System.out.println(ip);
							gatewayMethod.setDeviceState(controlDevice, state, ip);
							//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"success");
						}catch (Exception e){
							System.out.println(e);
						}

						break;
				}
				break;

			case "control alarm":
				switch (jsonObject.get("methodName").getAsString()){
					case "setstate":
						try {
							Device controlDevice = new Device();
							controlDevice.setShortAddress(jsonObject.get("shortAddress").getAsString());
							controlDevice.setEndpoint(jsonObject.get("Endpoint").getAsByte());

							byte state;
							state = (byte)(0xFF & Integer.parseInt(jsonObject.get("status").getAsString()));
							int time = jsonObject.get("time").getAsInt();

									//System.out.println("进入控制");

							String ip = gatewayGroupService.getGatewayIp(controlDevice.getShortAddress(), Integer.parseInt(String.valueOf(controlDevice.getEndpoint())));
							if (ip == null) {
								System.out.println("Gateway offline");
								//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"error");
							}


							System.out.println(ip);
							gatewayMethod.setAlarmState(controlDevice,state,ip,time);

							//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"success");
						}catch (Exception e){
							System.out.println(e);
						}

						break;
				}
				break;

			case "control lock":
				switch (jsonObject.get("methodName").getAsString()){
					case "setstate":
						Device controlDevice = new Device();
						controlDevice.setShortAddress(jsonObject.get("shortAddress").getAsString());
						controlDevice.setEndpoint(jsonObject.get("Endpoint").getAsByte());

						byte state;
						state = (byte)(0xFF & Integer.parseInt(jsonObject.get("status").getAsString()));


						String passwordStr = jsonObject.get("password").getAsString();
						int[] password = new int[passwordStr.length()/2];

						for (int i=0; i<passwordStr.length(); i=i+2) {
							password[i/2] = (passwordStr.charAt(i) - '0')*10+(passwordStr.charAt(i+1) - '0');
						}

						String ip = gatewayGroupService.getGatewayIp(controlDevice.getShortAddress(), Integer.parseInt(String.valueOf(controlDevice.getEndpoint())));
						if (ip == null) {
							System.out.println("Gateway offline");
							//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"error");
						}

						System.out.println(ip);
						gatewayMethod.setLock(controlDevice, password, ip, state);
						break;
				}
				break;

            case "control IR":
				Device controlDevice = new Device();
				controlDevice.setShortAddress(jsonObject.get("shortAddress").getAsString());
				controlDevice.setEndpoint(jsonObject.get("Endpoint").getAsByte());
				String ip = gatewayGroupService.getGatewayIp(controlDevice.getShortAddress(), Integer.parseInt(String.valueOf(controlDevice.getEndpoint())));

                switch (jsonObject.get("methodName").getAsString()){
                    case "learn":
                        byte learnMethod = 0x02;

                        byte[] learnData = {0x03, 0x00, 0x10, 0x40};

                        //String ip = gatewayGroupService.getGatewayIp(controlDevice.getShortAddress(), Integer.parseInt(String.valueOf(controlDevice.getEndpoint())));
                        if (ip == null) {
                            System.out.println("Gateway offline");
                            //rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"error");
                        }
                        gatewayMethod.controlIR(controlDevice, ip, learnData, learnMethod);

                        break;

					case "penetrate":
						byte penetrateMethod = 0x03;

						byte[] penetrateData = TransportHandler.toBytes(jsonObject.get("penetrateData").getAsString());


						if (ip == null) {
							System.out.println("Gateway offline");
							//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"error");
						}
						gatewayMethod.controlIR(controlDevice, ip, penetrateData, penetrateMethod);

						break;

					case "saveDataInGateway":
						byte saveMethod = 0x04;
						//TODO 拼接红外数据包

						break;

					case "selectDataInGateway":
						byte selectMethod = 0x05;
						controlDevice.setShortAddress("0000");
						controlDevice.setEndpoint((byte)0x00);

						if (ip == null) {
							System.out.println("Gateway offline");
							//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"error");
						}
						gatewayMethod.controlIR(controlDevice, ip, null, selectMethod);
						break;

					case "sendData":
						byte sendMethod = 0x06;
						byte[] sendData = TransportHandler.toBytes(jsonObject.get("sendData").getAsString());

						if (ip == null) {
							System.out.println("Gateway offline");
							//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"error");
						}
						gatewayMethod.controlIR(controlDevice, ip, sendData, sendMethod);
						break;

					case "deleteDataInGateway":
						byte deleteMethod = 0x07;
						byte[] deleteData = TransportHandler.toBytes(jsonObject.get("sendData").getAsString());

						if (ip == null) {
							System.out.println("Gateway offline");
							//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"error");
						}
						gatewayMethod.controlIR(controlDevice, ip, deleteData, deleteMethod);
						break;

					case "cachePenetrate":
						byte cacheMethod = 0x09;
						//TODO 数据包到底是啥？
						break;

					case "selectCache":
						byte selectCacheMethod = 0x0A;
						if (ip == null) {
							System.out.println("Gateway offline");
							//rpcMqttClient.publicResponce(Config.RPC_RESPONSE_TOPIC+requestId,"error");
						}
						gatewayMethod.controlIR(controlDevice, ip, null, selectCacheMethod);
						break;
                }
                break;

		}


	}
}
