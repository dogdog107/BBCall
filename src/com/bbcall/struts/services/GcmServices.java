package com.bbcall.struts.services;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bbcall.functions.ResultCode;
import com.bbcall.mybatis.dao.UserMapper;
@Scope("prototype")
@Service("gcmServices")
public class GcmServices {

	public static final String API_KEY = "AIzaSyBrHbssXTdu0kMSV75BWciQiwMKsvLkTz0";
	// private static String deviceRegId =
	// "APA91bFzVWxAx5OLieyezzzOBnRbuaAOalhrPVYLeZj5ap1QbFdRzXUnqYVkXxNgJch0fXG7Z9Z-a1ULloR4ke8hmeWezLAZKrVOiAJLIu22HgeT69J1Tn4BeA7nyRF4xUjoGhxWkQ5x";

	@Autowired
	private UserMapper userMapper;

	public int sendtogoogle(String datamsg, List<String> registeridList) {

		int constant1 = 100;
		int constant2 = 1;
		int constant3 = 0;
		int constant4 = 0;

		List<String> list = new ArrayList<String>();
		// registeridList.add("APA91bFzVWxAx5OLieyezzzOBnRbuaAOalhrPVYLeZj5ap1QbFdRzXUnqYVkXxNgJch0fXG7Z9Z-a1ULloR4ke8hmeWezLAZKrVOiAJLIu22HgeT69J1Tn4BeA7nyRF4xUjoGhxWkQ5x");
		List<String> nullString = new ArrayList<String>();
		nullString.add(null);

		registeridList.removeAll(nullString);

		int size = registeridList.size() / constant1 + constant2;

		System.out.println("size : " + size);
		
		JSONObject notification = new JSONObject();
		notification.put("title", "BBCall notification");
		notification.put("text", datamsg);
		notification.put("body", datamsg);

		
		JSONObject data = new JSONObject();
		data.put("message", datamsg);
		
		for (int i = 0; i < size; i++) {

			constant3 = i * constant1;
			constant4 = (i + 1) * constant1;

			if (constant4 > registeridList.size()) {
				constant4 = registeridList.size();
			}

			System.out.println("constant3 : " + constant3);
			System.out.println("constant4 : " + constant4);

			list = registeridList.subList(constant3, constant4);

			// list.add("APA91bFzVWxAx5OLieyezzzOBnRbuaAOalhrPVYLeZj5ap1QbFdRzXUnqYVkXxNgJch0fXG7Z9Z-a1ULloR4ke8hmeWezLAZKrVOiAJLIu22HgeT69J1Tn4BeA7nyRF4xUjoGhxWkQ5x");
			// list.add("APA91bFzVWxAx5OLieyezzzOBnRbuaAOalhrPVYLeZj5ap1QbFdRzXUnqYVkXxNgJch0fXG7Z9Z-a1ULloR4ke8hmeWezLAZKrVOiAJLIu22HgeT69J1Tn4BeA7nyRF4xUjoGhxWkQ5x");
			// list.add("APA91bFzVWxAx5OLieyezzzOBnRbuaAOalhrPVYLeZj5ap1QbFdRzXUnqYVkXxNgJch0fXG7Z9Z-a1ULloR4ke8hmeWezLAZKrVOiAJLIu22HgeT69J1Tn4BeA7nyRF4xUjoGhxWkQ5x");
			// list.add("APA91bFzVWxAx5OLieyezzzOBnRbuaAOalhrPVYLeZj5ap1QbFdRzXUnqYVkXxNgJch0fXG7Z9Z-a1ULloR4ke8hmeWezLAZKrVOiAJLIu22HgeT69J1Tn4BeA7nyRF4xUjoGhxWkQ5x");

			JSONArray jsonArray = new JSONArray(list);

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("notification", notification);
			jsonObject.put("registration_ids", jsonArray);
			jsonObject.put("data", data);

			try {
				URL url = new URL("https://gcm-http.googleapis.com/gcm/send");
				HttpURLConnection httpUrlConnection = (HttpURLConnection) url
						.openConnection();
				httpUrlConnection.setUseCaches(false);
				httpUrlConnection.setRequestMethod("POST");
				httpUrlConnection.addRequestProperty("Content-Type",
						"application/json");
				httpUrlConnection.addRequestProperty("Authorization", "key="
						+ API_KEY);
				httpUrlConnection.setConnectTimeout(5000);
				httpUrlConnection.setDoOutput(true);

				httpUrlConnection.connect();
				OutputStream outSteam = httpUrlConnection.getOutputStream();
				DataOutputStream dos = new DataOutputStream(outSteam);
				dos.writeBytes(jsonObject.toString());
				dos.flush();
				dos.close();
				InputStream inputStream = httpUrlConnection.getInputStream();
				System.out.println("result : "
						+ httpUrlConnection.getResponseCode());
				byte[] buffer = new byte[1024 * 100];
				inputStream.read(buffer);
				System.out.println(new String(buffer));

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();

			}
		}

		return ResultCode.SUCCESS;

		// Sender sender = new Sender(API_KEY);
		// Message message = new Message.Builder().addData("sendmessage",
		// datamsg)
		// .build();
		//
		// Result result = null;
		//
		// try {
		// result = sender.send(message, deviceRegId, 5);
		//
		// System.out.println(result.getMessageId());
		// System.out.println(result.getCanonicalRegistrationId());
		// System.out.println(result.getErrorCodeName());
		//
		// } catch (Exception e) {
		// // TODO: handle exception
		// e.printStackTrace();
		// }
		//
		// // 为空，则消息未发送给任何设备
		// if (result.getMessageId() != null) {
		// String canonicalRegId = result.getCanonicalRegistrationId();
		// // 用户注册了新的注册id，或者谷歌服务器刷新了注册id。
		// // 用户注册了新id，旧的id会被保存一段时间。此时使用旧id发送消息，设备即使已使用新id，依然可以收到
		// if (canonicalRegId != null) {
		// // same device has more than on registration ID: update database
		// return ResultCode.REGISTER_ID_UPDATE_SUCCSESS;
		// }
		// return ResultCode.SUCCESS;
		// } else {
		// String error = result.getErrorCodeName();
		// if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
		// // application has been removed from device - unregister
		// // database
		// return ResultCode.DEVICE_NOT_REGISTERED;
		// }
		// return ResultCode.SEND_MESSAGE_FAILED;
		// }
		//
	}

	public int sendtouser(String datamsg, String registerid) {

		JSONObject notification = new JSONObject();
		notification.put("title", "BBCall notification");
		notification.put("text", datamsg);
		notification.put("body", datamsg);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("notification", notification);
		jsonObject.put("to", registerid);

		try {
			URL url = new URL("https://gcm-http.googleapis.com/gcm/send");
			HttpURLConnection httpUrlConnection = (HttpURLConnection) url
					.openConnection();
			httpUrlConnection.setUseCaches(false);
			httpUrlConnection.setRequestMethod("POST");
			httpUrlConnection.addRequestProperty("Content-Type",
					"application/json");
			httpUrlConnection.addRequestProperty("Authorization", "key="
					+ API_KEY);
			httpUrlConnection.setConnectTimeout(5000);
			httpUrlConnection.setDoOutput(true);

			httpUrlConnection.connect();
			OutputStream outSteam = httpUrlConnection.getOutputStream();
			DataOutputStream dos = new DataOutputStream(outSteam);
			dos.writeBytes(jsonObject.toString());
			dos.flush();
			dos.close();
			InputStream inputStream = httpUrlConnection.getInputStream();
			System.out.println("result : "
					+ httpUrlConnection.getResponseCode());
			byte[] buffer = new byte[1024 * 100];
			inputStream.read(buffer);
			System.out.println(new String(buffer));

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		}

		return ResultCode.SUCCESS;

	}

}
