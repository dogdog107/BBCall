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

	public int sendtogoogle(String datamsg, List<String> registeridList, Integer orderId)
			throws Exception {

		int constant1 = 100;
		int constant2 = 1;
		int constant3 = 0;
		int constant4 = 0;

		JSONObject notification = new JSONObject();
		notification.put("title", "BBCall notification");
		notification.put("text", datamsg);
		notification.put("body", datamsg);
		if (orderId != null)
			notification.put("orderid", orderId);

		JSONObject data = new JSONObject();
		data.put("data", datamsg);
		data.put("message", datamsg);
		data.put("msg", datamsg);
		if (orderId != null)
			data.put("orderid", orderId);

		List<String> reslist = new ArrayList<String>();
		List<String> nullString = new ArrayList<String>();
		nullString.add(null);

		registeridList.removeAll(nullString);

		int size = registeridList.size() / constant1 + constant2;

		System.out.println("size : " + size);

		for (int i = 0; i < size; i++) {

			constant3 = i * constant1;
			constant4 = (i + 1) * constant1;

			if (constant4 > registeridList.size()) {
				constant4 = registeridList.size() - 1;
			}

			System.out.println("constant3 : " + constant3);
			System.out.println("constant4 : " + constant4);

			reslist = registeridList.subList(constant3, constant4);

			for (int k = 0; k < reslist.size(); k++) {
				System.out.println("k : " + reslist.get(k));
			}

			JSONArray jsonArray = new JSONArray(reslist);

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("notification", notification);
			jsonObject.put("registration_ids", jsonArray);
			jsonObject.put("data", data);

			System.out.println(jsonObject);

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
			System.out.println("dos : " + dos);
			dos.flush();
			System.out.println("dos.flush()");
			dos.close();
			System.out.println("dos.close()");
			InputStream inputStream = httpUrlConnection.getInputStream();
			System.out.println("result : "
					+ httpUrlConnection.getResponseCode());
			byte[] buffer = new byte[1024 * 100];
			inputStream.read(buffer);
			System.out.println(new String(buffer));
		}

		// list.add("APA91bF_M3T1dQe1imzS7j0bshAbxanfjFWo5UH8PrVkIYPxyTFFzn38yeIt1abe1cHRyNT-ZQ7XkQREfSDMCkyGLvMCfvKhlwUQ9te4wC0xAPYZAX60hE2CCC3Rz0p9BngDrjvypn1V");
		// list.add("APA91bF_M3T1dQe1imzS7j0bshAbxanfjFWo5UH8PrVkIYPxyTFFzn38yeIt1abe1cHRyNT-ZQ7XkQREfSDMCkyGLvMCfvKhlwUQ9te4wC0xAPYZAX60hE2CCC3Rz0p9BngDrjvypn1V");
		// list.add("APA91bF_M3T1dQe1imzS7j0bshAbxanfjFWo5UH8PrVkIYPxyTFFzn38yeIt1abe1cHRyNT-ZQ7XkQREfSDMCkyGLvMCfvKhlwUQ9te4wC0xAPYZAX60hE2CCC3Rz0p9BngDrjvypn1V");

		// int constant1 = 100;
		// int constant2 = 1;
		// int constant3 = 0;
		// int constant4 = 0;
		//
		// List<String> list = new ArrayList<String>();
		// //
		// registeridList.add("APA91bFzVWxAx5OLieyezzzOBnRbuaAOalhrPVYLeZj5ap1QbFdRzXUnqYVkXxNgJch0fXG7Z9Z-a1ULloR4ke8hmeWezLAZKrVOiAJLIu22HgeT69J1Tn4BeA7nyRF4xUjoGhxWkQ5x");
		// List<String> nullString = new ArrayList<String>();
		// nullString.add(null);
		//
		// registeridList.removeAll(nullString);
		//
		// int size = registeridList.size() / constant1 + constant2;
		//
		// System.out.println("size : " + size);
		//
		// JSONObject notification = new JSONObject();
		// notification.put("title", "BBCall notification");
		// notification.put("text", datamsg);
		// notification.put("body", datamsg);
		//
		// JSONObject data = new JSONObject();
		// data.put("data", datamsg);
		//
		// for (int i = 0; i < size; i++) {
		//
		// constant3 = i * constant1;
		// constant4 = (i + 1) * constant1;
		//
		// if (constant4 > registeridList.size()) {
		// constant4 = registeridList.size() -1 ;
		// }
		//
		// System.out.println("constant3 : " + constant3);
		// System.out.println("constant4 : " + constant4);
		//
		// list = registeridList.subList(constant3, constant4);
		//
		// //
		// list.add("APA91bFzVWxAx5OLieyezzzOBnRbuaAOalhrPVYLeZj5ap1QbFdRzXUnqYVkXxNgJch0fXG7Z9Z-a1ULloR4ke8hmeWezLAZKrVOiAJLIu22HgeT69J1Tn4BeA7nyRF4xUjoGhxWkQ5x");
		// //
		// list.add("APA91bFzVWxAx5OLieyezzzOBnRbuaAOalhrPVYLeZj5ap1QbFdRzXUnqYVkXxNgJch0fXG7Z9Z-a1ULloR4ke8hmeWezLAZKrVOiAJLIu22HgeT69J1Tn4BeA7nyRF4xUjoGhxWkQ5x");
		// //
		// list.add("APA91bFzVWxAx5OLieyezzzOBnRbuaAOalhrPVYLeZj5ap1QbFdRzXUnqYVkXxNgJch0fXG7Z9Z-a1ULloR4ke8hmeWezLAZKrVOiAJLIu22HgeT69J1Tn4BeA7nyRF4xUjoGhxWkQ5x");
		// //
		// list.add("APA91bFzVWxAx5OLieyezzzOBnRbuaAOalhrPVYLeZj5ap1QbFdRzXUnqYVkXxNgJch0fXG7Z9Z-a1ULloR4ke8hmeWezLAZKrVOiAJLIu22HgeT69J1Tn4BeA7nyRF4xUjoGhxWkQ5x");
		//
		// JSONArray jsonArray = new JSONArray(list);
		//
		// JSONObject jsonObject = new JSONObject();
		// jsonObject.put("notification", notification);
		// jsonObject.put("registration_ids", jsonArray);
		// jsonObject.put("data", data);
		//
		// System.out.println(jsonObject);
		//
		// URL url = new URL("https://gcm-http.googleapis.com/gcm/send");
		// HttpURLConnection httpUrlConnection = (HttpURLConnection) url
		// .openConnection();
		// httpUrlConnection.setUseCaches(false);
		// httpUrlConnection.setRequestMethod("POST");
		// httpUrlConnection.addRequestProperty("Content-Type",
		// "application/json");
		// httpUrlConnection.addRequestProperty("Authorization", "key="
		// + API_KEY);
		// httpUrlConnection.setConnectTimeout(5000);
		// httpUrlConnection.setDoOutput(true);
		//
		// System.out.println(httpUrlConnection);
		//
		// httpUrlConnection.connect();
		// OutputStream outSteam = httpUrlConnection.getOutputStream();
		// System.out.println("outSteam: " + outSteam);
		// DataOutputStream dos = new DataOutputStream(outSteam);
		// System.out.println("dos: " + dos);
		// dos.writeBytes(jsonObject.toString());
		// dos.flush();
		// dos.close();
		// InputStream inputStream = httpUrlConnection.getInputStream();
		// System.out.println("result : "
		// + httpUrlConnection.getResponseCode());
		// byte[] buffer = new byte[1024 * 100];
		// inputStream.read(buffer);
		// System.out.println(new String(buffer));
		//
		// }

		return ResultCode.SUCCESS;

	}

	public int sendtouser(String datamsg, String registerid, Integer orderId) throws Exception {

		JSONObject notification = new JSONObject();
		notification.put("title", "BBCall notification");
		notification.put("text", datamsg);
		notification.put("body", datamsg);
		if (orderId != null)
			notification.put("orderid", orderId);

		JSONObject data = new JSONObject();
		data.put("data", datamsg);
		data.put("message", datamsg);
		data.put("msg", datamsg);
		if (orderId != null)
			data.put("orderid", orderId);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("notification", notification);
		jsonObject.put("to", registerid);
		jsonObject.put("data", data);
		if (orderId != null)
			jsonObject.put("orderid", orderId);
		
		System.out.println(jsonObject);

		URL url = new URL("https://gcm-http.googleapis.com/gcm/send");
		HttpURLConnection httpUrlConnection = (HttpURLConnection) url
				.openConnection();
		httpUrlConnection.setUseCaches(false);
		httpUrlConnection.setRequestMethod("POST");
		httpUrlConnection
				.addRequestProperty("Content-Type", "application/json");
		httpUrlConnection.addRequestProperty("Authorization", "key=" + API_KEY);
		httpUrlConnection.setConnectTimeout(5000);
		httpUrlConnection.setDoOutput(true);

		httpUrlConnection.connect();
		OutputStream outSteam = httpUrlConnection.getOutputStream();
		DataOutputStream dos = new DataOutputStream(outSteam);
		dos.writeBytes(jsonObject.toString());
		System.out.println("dos : " + dos);
		dos.flush();
		System.out.println("dos.flush()");
		dos.close();
		System.out.println("dos.close()");
		InputStream inputStream = httpUrlConnection.getInputStream();
		System.out.println("result : " + httpUrlConnection.getResponseCode());
		byte[] buffer = new byte[1024 * 100];
		inputStream.read(buffer);
		System.out.println(new String(buffer));

		return ResultCode.SUCCESS;

	}

}
