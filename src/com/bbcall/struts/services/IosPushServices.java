package com.bbcall.struts.services;

import java.util.ArrayList;
import java.util.List;

import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;

import org.springframework.stereotype.Service;

import com.bbcall.functions.ResultCode;

@Service("iosPushServices")
public class IosPushServices {

	/************************************************
	 * 测试推送服务器地址：gateway.sandbox.push.apple.com /2195
	 * 产品推送服务器地址：gateway.push.apple.com / 2195
	 * 
	 * 需要javaPNS_2.2.jar包
	 ***************************************************/
	private static String rootPath = System.getProperty("webApp.root");
	private static final String p12FilePath_customer = rootPath + "WEB-INF/IosCert/customer.p12";
	private static final String p12Password_customer = "4753"; //此处注意导出的证书密码不能为空因为空密码会报错
	private static final String p12FilePath_master = rootPath + "WEB-INF/IosCert/master.p12";
	private static final String p12Password_master = "4753"; //此处注意导出的证书密码不能为空因为空密码会报错
	
	/**
	 * iosPush ios推送服务
	 * @param deviceTokens
	 * @param msg
	 * @throws Exception
	 */
	public int iosPush(List<String> deviceTokens, String msg, Integer usertype) throws Exception {
		
		if (usertype == null) {
			return ResultCode.REQUIREINFO_NOTENOUGH;
		}
		
		String p12FilePath = null;
		String p12Password = null;
		
		switch (usertype) {
		case 1:
			p12FilePath = p12FilePath_customer;
			p12Password = p12Password_customer;
			break;
		case 2:
			p12FilePath = p12FilePath_master;
			p12Password = p12Password_master;
			break;

		default:
			return ResultCode.USERTYPE_ERROR;
		}
		
		PushNotificationPayload payLoad = new PushNotificationPayload();
		payLoad.addAlert(msg); // 消息内容
		payLoad.addBadge(1); // iphone应用图标上小红圈上的数值
		payLoad.addSound("default");// 铃音
		PushNotificationManager pushManager = new PushNotificationManager();
		// true：表示的是产品发布推送服务 false：表示的是产品测试推送服务
		pushManager.initializeConnection(new AppleNotificationServerBasicImpl(p12FilePath, p12Password, false));
		List<PushedNotification> notifications = new ArrayList<PushedNotification>();
		// 发送push消息
		if (deviceTokens.size() == 1) {
			Device device = new BasicDevice();
			device.setToken(deviceTokens.get(0));
			PushedNotification notification = pushManager.sendNotification(device, payLoad, true);
			notifications.add(notification);
		} else {
			List<Device> device = new ArrayList<Device>();
			for (int i = 0; i < deviceTokens.size(); i++) {
				device.add(new BasicDevice(deviceTokens.get(i)));
			}
			notifications = pushManager.sendNotifications(payLoad, device);
		}
		List<PushedNotification> failedNotifications = PushedNotification.findFailedNotifications(notifications);
		List<PushedNotification> successfulNotifications = PushedNotification.findSuccessfulNotifications(notifications);
		int failed = failedNotifications.size();
		int successful = successfulNotifications.size();
		System.out.println("IOS Push Success:" + successful + "  Failed:" + failed);
		pushManager.stopConnection();
		return ResultCode.SUCCESS;
	}
}
