package com.bbcall.struts.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import com.bbcall.functions.ResultCode;

@Service("mailServices")
public class MailServices {
//	@Resource(name="mailSender")
//	private MailSender mailSender;
	@Autowired
	private MailSender mailSender;
	@Autowired
	private SimpleMailMessage simpleMail;
	
	public int sendSimpleMail(String toAds, String username, String password) {
//		simpleMail.setFrom("xxxxxx@126.com"); // applicationContext.xml已定义此参数
//		simpleMail.setSubject(" 测试spring Mail"); // applicationContext.xml已定义此参数
		simpleMail.setTo(toAds);
		String header = "Dear " + username + ",\n";
		String body_pwd = "Your new password is : " + password + "\n";
		String body_reminder = "Please use this password to login BBCall and change your password IMMEDIATELY!\n";
		String footer = "\nBest Regards, \nBBCall Admin";
		String text = header + body_pwd + body_reminder + footer;
		simpleMail.setText(text);
		mailSender.send(simpleMail);
		return ResultCode.SUCCESS;
	}
}
