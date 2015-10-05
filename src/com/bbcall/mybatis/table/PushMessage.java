package com.bbcall.mybatis.table;

/* Create sql
CREATE TABLE PUSHMESSAGE (
		msg_id INT NOT NULL auto_increment PRIMARY KEY,
		msg_type VARCHAR (100),
		msg_content VARCHAR (255)
	) DEFAULT CHARSET = utf8;
 */

/**
 * Table to store the push message content.
 * @author Roger Luo
 * @since 1.0
 */
public class PushMessage {
	private int msg_id;
	private String msg_type;
	private String msg_content;

	/**
	 * Getter & Setter
	 * 
	 * @return
	 */
	public int getMsg_id() {
		return msg_id;
	}

	public void setMsg_id(int msg_id) {
		this.msg_id = msg_id;
	}

	public String getMsg_content() {
		return msg_content;
	}

	public void setMsg_content(String msg_content) {
		this.msg_content = msg_content;
	}

	public String getMsg_type() {
		return msg_type;
	}

	public void setMsg_type(String msg_type) {
		this.msg_type = msg_type;
	}
}
