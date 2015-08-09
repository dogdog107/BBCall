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

@Service("iosPushServices")
public class IosPushServices {

	/************************************************
	 * 测试推送服务器地址：gateway.sandbox.push.apple.com /2195
	 * 产品推送服务器地址：gateway.push.apple.com / 2195
	 * 
	 * 需要javaPNS_2.2.jar包
	 ***************************************************/
	private static final String p12FilePath = "";
	private static final String p12Password = ""; //此处注意导出的证书密码不能为空因为空密码会报错
	
	/**
	 * iosPush ios推送服务
	 * @param deviceTokens
	 * @param msg
	 * @throws Exception
	 */
	public static void iosPush(List<String> deviceTokens, String msg) throws Exception {
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
		pushManager.stopConnection();
	}
}
