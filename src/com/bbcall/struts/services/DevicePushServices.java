package com.bbcall.struts.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbcall.functions.ResultCode;
import com.bbcall.functions.Tools;
import com.bbcall.mybatis.dao.PushMessageMapper;
import com.bbcall.mybatis.table.PushMessage;

@Service("devicePushServices")
public class DevicePushServices {

	@Autowired
	private GcmServices gcmServices;
	@Autowired
	private IosPushServices iosPushServices;
	@Autowired
	private PushMessageMapper pushMessageMapper;
	
	/**
	 * devicePush By message
	 * @param deviceType
	 * @param deviceToken
	 * @param msg
	 * @param userType
	 * @param orderId
	 * @return
	 */
	public int devicePush(Integer deviceType, List<String> deviceTokens, String msg,
			Integer userType, Integer orderId, String msgType) {
		if (deviceType == null || deviceTokens.size() <= 0)
			return ResultCode.REQUIREINFO_NOTENOUGH;

		if (deviceType != 1 && deviceType != 2)
			return ResultCode.REQUIREINFO_ERROR;

		try {
			if (deviceType.equals(1)) {
				// 谷歌推送
				for (int i = 0; i < deviceTokens.size(); i++) {
					gcmServices.sendtouser(msg, deviceTokens.get(i), orderId, msgType);
				}
			} else if (deviceType.equals(2)) {
				// 苹果推送
				if (userType == null) {
					return ResultCode.REQUIREINFO_NOTENOUGH;
				}
//				List<String> deviceTokens = new ArrayList<String>();
//				deviceTokens.add(deviceToken);
				iosPushServices.iosPush(deviceTokens, msg, userType, orderId, msgType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultCode.SUCCESS;
	}
	
	/**
	 * devicePush By message ID
	 * @param deviceType
	 * @param deviceToken
	 * @param msgId
	 * @param userType
	 * @param orderId
	 * @return
	 */
	public int devicePush(Integer deviceType, List<String> deviceTokens, Integer msgId, Integer userType, Integer orderId) {
		String msgContent = pushMessageMapper.getPushMessageByMsgId(msgId).getMsg_content();
		String msgType = pushMessageMapper.getPushMessageByMsgId(msgId).getMsg_type();
		return devicePush(deviceType, deviceTokens, msgContent, userType,orderId, msgType);
	}
	
	/**
	 * Get All push Message from system.
	 * @return
	 */
	public List<PushMessage> getPushMessage() {
		return pushMessageMapper.getAll();
	}
	
	/**
	 * update Push Message.
	 * @param msgId
	 * @param msgContents
	 * @return
	 */
	public int updatePushMessage(Integer msgId, String msgContents) {
		
		if (msgId == null || Tools.isEmpty(msgContents)) {
			return ResultCode.REQUIREINFO_NOTENOUGH;
		}
		
		PushMessage pushMessage = new PushMessage();
		pushMessage = pushMessageMapper.getPushMessageByMsgId(msgId);
		
		if (pushMessage == null) {
			return ResultCode.REQUIREINFO_ERROR;
		} else {
			pushMessage.setMsg_content(msgContents);
			pushMessageMapper.updatePushMessage(pushMessage);
		}
		
		return ResultCode.SUCCESS;
	}
}