package com.envisioniot.enos.iot_mqtt_sdk.sample;

import com.envisioniot.enos.iot_mqtt_sdk.core.IConnectCallback;
import com.envisioniot.enos.iot_mqtt_sdk.core.MqttClient;
import com.envisioniot.enos.iot_mqtt_sdk.core.exception.EnvisionException;
import com.envisioniot.enos.iot_mqtt_sdk.core.msg.IMessageHandler;
import com.envisioniot.enos.iot_mqtt_sdk.message.downstream.device.DisableDeviceCommand;
import com.envisioniot.enos.iot_mqtt_sdk.message.downstream.device.DisableDeviceCommandReply;
import com.envisioniot.enos.iot_mqtt_sdk.message.downstream.tsl.*;
import com.envisioniot.enos.iot_mqtt_sdk.message.upstream.register.SubDeviceDynamicRegRequest;
import com.envisioniot.enos.iot_mqtt_sdk.message.upstream.register.SubDeviceDynamicRegResponse;
import com.envisioniot.enos.iot_mqtt_sdk.message.upstream.status.SubDeviceLoginRequest;
import com.envisioniot.enos.iot_mqtt_sdk.message.upstream.status.SubDeviceLoginResponse;
import com.envisioniot.enos.iot_mqtt_sdk.message.upstream.status.SubDeviceLogoutRequest;
import com.envisioniot.enos.iot_mqtt_sdk.message.upstream.status.SubDeviceLogoutResponse;
import com.envisioniot.enos.iot_mqtt_sdk.message.upstream.topo.*;
import com.envisioniot.enos.iot_mqtt_sdk.message.upstream.tsl.EventPostRequest;
import com.envisioniot.enos.iot_mqtt_sdk.message.upstream.tsl.MeasurepointPostRequest;
import com.envisioniot.enos.iot_mqtt_sdk.message.upstream.tsl.TslTemplateGetRequest;
import com.envisioniot.enos.iot_mqtt_sdk.message.upstream.tsl.TslTemplateGetResponse;
import com.envisioniot.enos.iot_mqtt_sdk.util.Pair;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author zhensheng.cai
 * @date 2018/8/3.
 */
public class SimpleSendReceive
{
	private static final String local = "tcp://localhost:11883";
	private static final String alpha = "tcp://10.24.10.56:11883";
	private static final String prd = "tcp://10.24.8.76:11883";
//	private static final String productKey = "8myRluG6";
//	public static final String deviceKey = "zscai-test-device";
//	public static final String deviceSecret = "KDZLIqVCn6rU11TKp8XO";
	private static final String productKey = "NyDmJcbZ";
	public static final String deviceKey = "xCPLxZtLKg";
	public static final String deviceSecret = "0sfmTw2c9gY5JcopMrvd";

//	public static final String subProductKey = "iM3Zf8uF";
//	public static final String subDeviceKey = "zscai-sub-device";
//	public static final String subDeviceSecret = "ZnH5DJvo3uE9c5fxoXug";
	public static final String subProductKey = "muB7helV";
	public static final String subDeviceKey = "UKaQFBAemf";
	public static final String subDeviceSecret = "MEwVRDFptW6YctnS2GlF";


	private static MqttClient client;


	public static void init(){
		try
		{
			client= new MqttClient(prd, productKey, deviceKey, deviceSecret);
			client.connect(new IConnectCallback()
			{
				@Override
				public void onConnectSuccess()
				{
					try
					{
						System.out.println("start register login sub-device , current status : " + client.isConnected());
						SubDeviceLoginRequest request = SubDeviceLoginRequest.builder()
								.setSubDeviceInfo(subProductKey, subDeviceKey, subDeviceSecret)
								.build();
						SubDeviceLoginResponse rsp = client.publish(request);;
						System.out.println(rsp);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}

				@Override public void onConnectLost()
				{

				}

				@Override public void onConnectFailed(int reasonCode)
				{

				}
			});
		}
		catch (EnvisionException e)
		{
			e.printStackTrace();
		}
		System.out.println("connect:" + client.isConnected());
	}

	public static void initWithCallback(){
		System.out.println("start connect with callback ... ");
		try
		{
			client= new MqttClient(prd, productKey, deviceKey, deviceSecret);
			client.getProfile().setConnectionTimeout(10);
			client.connect(new IConnectCallback()
			{
				@Override public void onConnectSuccess()
				{
					System.out.println("connect success");
				}

				@Override public void onConnectLost()
				{
					System.out.println("onConnectLost");
				}

				@Override public void onConnectFailed(int reasonCode)
				{
					System.out.println("onConnectFailed : "+ reasonCode);
				}

			});
		}
		catch (EnvisionException e)
		{
			//e.printStackTrace();
		}
		System.out.println("connect result :" + client.isConnected());
	}

	public static void subDeviceRegister(){
		System.out.println("start register register sub-device , current status : "+client.isConnected());
		SubDeviceDynamicRegRequest request = SubDeviceDynamicRegRequest.builder()
				.addSubRegisterInfo("eb27piAg", "zscai-sub-device-1", "zscai-sub-device-1", "zscai-sub-device-1")
				.addSubRegisterInfo("eb27piAg", "zscai-sub-device-2", "zscai-sub-device-2", "zscai-sub-device-2")
				.addSubRegisterInfo("eb27piAg", "zscai-sub-device-3", "zscai-sub-device-3", "zscai-sub-device-3")
				.build();
//		request.setRegProductKey("eb27piAg");
		SubDeviceDynamicRegResponse rsp = null;
		try
		{
		    
			rsp = client.publish(request);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("--->" + rsp);
	}

	public static void subDeviceLogin(){
		System.out.println("start register login sub-device , current status : "+client.isConnected());
		SubDeviceLoginRequest request = SubDeviceLoginRequest.builder()
				.setSubDeviceInfo(subProductKey, subDeviceKey, subDeviceSecret).build();
		SubDeviceLoginResponse rsp = null;

		try
		{
			rsp = client.publish(request);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println(rsp);
	}

	public static void subdeviceLogout() throws InterruptedException
	{
		System.out.println("start logout sub device...");
		SubDeviceLogoutRequest request = SubDeviceLogoutRequest.builder()
				.setSubProductKey(subProductKey)
				.setSubDeviceKey(subDeviceKey).build();
		SubDeviceLogoutResponse rsp = null;
		try
		{
			rsp = client.publish(request);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println(rsp);

		System.out.println("Assert sub device logout ,post subdevice event");
		Thread.sleep(3000);
		postSubEvent();

	}





	public static void addTopo() throws Exception
	{
		System.out.println("start add topo ...");
		AddTopoRequest request = AddTopoRequest.builder().
				addSubDevice(new SubDeviceInfo(subProductKey, subDeviceKey, subDeviceSecret)).build();
		AddTopoResponse rsp = client.publish(request);
		System.out.println("-->" + rsp);
		getTopo();
//		subDeviceLogin();
//		subdeviceLogout();

	}
	public static void deleteTopo() throws Exception
	{
		System.out.println("start delete topo...");
		DeleteTopoRequest request = DeleteTopoRequest.builder()
				.setSubDevices(Lists.newArrayList(Pair.makePair(subProductKey, subDeviceKey))).build();
		DeleteTopoResponse rsp = client.publish(request);
		System.out.println("-->" + rsp);
		getTopo();
//		subDeviceLogin();
	}


	public static void getTopo() throws Exception
	{
		System.out.println("start get topo...");
		GetTopoRequest request = GetTopoRequest.builder().build();

		GetTopoResponse rsp = client.publish(request);
		System.out.println("-->" + rsp);

	}


	/*

	BaseMqttResponse{id='1', code=200, data={tslModelId=xfgBHRXU, tslAttributeMap={}, tslMeasurepointMap={}, tslServiceMap={}, tslEventMap={}, tag={tagMap={}}, allowAdditionalAttribute=false, inheritedAttributeIds=[], inheritedMeasurepointIds=[], inheritedServiceIds=[], inheritedEventIds=[]}, message='null', method='null'}
	*/
	public static void getTslTemplete()
	{
		System.out.println("start get tsl template... ");
		TslTemplateGetRequest request = TslTemplateGetRequest.builder().build();
		TslTemplateGetResponse rsp = null;
		try
		{
			rsp = client.publish(request);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println(rsp);
	}

	public static void postEvent() throws InterruptedException
	{
		System.out.println("start post");
		EventPostRequest postRequest = EventPostRequest.builder()
				.setEventIdentifier("PowerTooHigh")
				.addValue("PowerAlarm", 60)
				.build();
		try
		{
			/*
			 * 当前版本Post消息没有对应的reply
			 */
			client.fastPublish(postRequest);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("post finish");

		while(true){
			Thread.sleep(1000);
		}
	}


	public static void postMeasurepoint(){
		System.out.println("start post measurepoint ...");
		MeasurepointPostRequest request = MeasurepointPostRequest.builder().setProductKey(subProductKey).setDeviceKey(subDeviceKey)
				.addMeasurePoint("p1", "string").addMeasurePoint("p2", "{'value':123.4,  quality:2}").build();
		try
		{
			client.fastPublish(request);
//			System.out.println("-->" + rsp);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}




	public static void postSubEvent(){
		System.out.println("start post");
		EventPostRequest postRequest = EventPostRequest.builder()
				.setEventIdentifier("PowerTooHigh")
				.addValue("PowerAlarm", 60)
				.setDeviceKey(deviceKey)
				.setProductKey(subProductKey)
				.build();
		try
		{
			/**
			 * 当前版本Post消息没有对应的reply
			 */
			client.fastPublish(postRequest);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("post finish");
	}



	public  static void handleServiceInvocation()
	{
	    IMessageHandler<ServiceInvocationCommand, ServiceInvocationReply> handler = new IMessageHandler<ServiceInvocationCommand, ServiceInvocationReply>()
        {
            @Override
            public ServiceInvocationReply onMessage(ServiceInvocationCommand request, List<String> argList) throws Exception
            {
                System.out.println("rcvn async serevice invocation command" +  request + " topic " + argList);
                return ServiceInvocationReply.builder().addOutputData("pointA", "valueA")
						.build();
			}
            
        };
        
        
        client.setArrivedMsgHandler(ServiceInvocationCommand.class, handler);
        client.setArrivedMsgHandler(RrpcInvocationCommand.class, new IMessageHandler<RrpcInvocationCommand, RrpcInvocationReply>()
		{
			@Override public RrpcInvocationReply onMessage(RrpcInvocationCommand arrivedMessage, List<String> argList)
					throws Exception
			{
				System.out.println("rcv rrpc commadnd " + arrivedMessage + "topic " + argList);
				return RrpcInvocationReply.builder().build();
			}
		});
        
        //client.addCommandHandler(handler);

		//hold the thread
		while(true){
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	public static void postMeasurepointV2(){
		MeasurepointPostRequest request = MeasurepointPostRequest.builder()
				.addMeasurePoint("a", "b").build();

	}


	public static void measurepointSetHandler(){
		client.setArrivedMsgHandler(MeasurepointSetCommand.class, new IMessageHandler<MeasurepointSetCommand, MeasurepointSetReply>()
		{
			@Override
			public MeasurepointSetReply onMessage(MeasurepointSetCommand arrivedMessage, List<String> argList) throws Exception
			{
				return null;
			}
		});

		client.setArrivedMsgHandler(DisableDeviceCommand.class, new IMessageHandler<DisableDeviceCommand, DisableDeviceCommandReply>()
		{

			@Override
			public DisableDeviceCommandReply onMessage(DisableDeviceCommand command, List<String> argList) throws Exception
			{
				// disable device and ret 201 reply
				return DisableDeviceCommandReply.builder().setCode(201).build();
			}
		});
	}


	public static void main(String[] args) throws Exception
	{
		initWithCallback();
//		init();
		addTopo();
		deleteTopo();
		postMeasurepoint();
		postEvent();
		postSubEvent();
		subDeviceRegister();
		subDeviceLogin();
		subdeviceLogout();
		getTslTemplete();
		handleServiceInvocation();

	}





}
