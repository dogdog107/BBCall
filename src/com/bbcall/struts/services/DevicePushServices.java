package com.bbcall.struts.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbcall.functions.ResultCode;
import com.bbcall.functions.Tools;

@Service("devicePushServices")
public class DevicePushServices {

	@Autowired
	private GcmServices gcmServices;
	@Autowired
	private IosPushServices iosPushServices;

	public int devicePush(Integer deviceType, String deviceToken, String msg,
			Integer userType) {
		if (deviceType == null || Tools.isEmpty(deviceToken))
			return ResultCode.REQUIREINFO_NOTENOUGH;

		if (deviceType != 1 && deviceType != 2)
			return ResultCode.REQUIREINFO_ERROR;

		try {
			if (deviceType.equals(1)) {
				// 谷歌推送
				gcmServices.sendtouser(msg, deviceToken);
			} else if (deviceType.equals(2)) {
				// 苹果推送
				if (userType == null) {
					return ResultCode.REQUIREINFO_NOTENOUGH;
				}
				List<String> deviceTokens = new ArrayList<String>();
				deviceTokens.add(deviceToken);
				iosPushServices.iosPush(deviceTokens, msg, userType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultCode.SUCCESS;
	}
}