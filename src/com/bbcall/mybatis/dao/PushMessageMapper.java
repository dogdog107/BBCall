package com.bbcall.mybatis.dao;

import java.util.List;

import com.bbcall.mybatis.table.PushMessage;

public interface PushMessageMapper {

	/**
	 * Add PushMessage to Database.
	 * 
	 * @param pushMessage
	 */
	public void addPushMessage(PushMessage pushMessage);

	/**
	 * Update PushMessage record.
	 * 
	 * @param pushMessage
	 */
	public void updatePushMessage(PushMessage pushMessage);

	/**
	 * Delete PushMessage by msg_id.
	 * 
	 * @param msg_id
	 */
	public void deletePushMessageByMsgId(Integer msg_id);

	/**
	 * Get PushMessage by msg_id.
	 * 
	 * @param msg_id
	 * @return
	 */
	public PushMessage getPushMessageByMsgId(Integer msg_id);
	
	/**
	 * Get All records from PushMessage table.
	 * @return
	 */
	public List<PushMessage> getAll();
}
