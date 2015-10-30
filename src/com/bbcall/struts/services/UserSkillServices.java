package com.bbcall.struts.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bbcall.functions.ResultCode;
import com.bbcall.functions.Tools;
import com.bbcall.mybatis.dao.UserMapper;
import com.bbcall.mybatis.dao.UserSkillMapper;
import com.bbcall.mybatis.table.User;
import com.bbcall.mybatis.table.UserSkill;
import com.github.pagehelper.PageHelper;
@Scope("prototype")
@Service("userSkillServices")
public class UserSkillServices {
	
	private static Logger logger = Logger.getLogger(UserSkillServices.class);  
	
	@Autowired
	private UserSkillMapper userSkillMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private ReferdocServices referdocServices;
	@Autowired
	private FileUploadServices fileUploadServices;
	@Autowired
	private DevicePushServices devicePushServices;
	
	private List<UserSkill> userSkillList;
	
	/**
	 * getUserSkillByUserId 得到用户技能表
	 * @param userid
	 * @return
	 */
	public int getUserSkillByUserId(Integer userid) {
		if (userid == null)
			return ResultCode.REQUIREINFO_NOTENOUGH;
		
		userSkillList = userSkillMapper.getUserSkillByUserId(userid);
		if (userSkillList != null) {
			return ResultCode.SUCCESS;
		} else {
			return ResultCode.USERSKILLLIST_NULL;
		}
	}
	
	/**
	 * getAllUserSkill 获取全部技能列表
	 * @return
	 */
	public List<UserSkill> getAllUserSkill(Integer pagenum){
		//当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;
		
	    //PageHelper.startPage(PageNum, PageSize) 
		//获取第1页，10条内容，当PageSize=0时会查询出全部的结果
	    PageHelper.startPage(pagenum, 20);

	    //紧跟着的第一个select方法会被分页
		return userSkillMapper.getAll();
	}
	
	/**
	 * addUserSkill 增加用户技能信息
	 * @param userid
	 * @param userskill
	 * @param userskillurl
	 * @return
	 */
	public int addUserSkill(Integer userid, Integer userskill,
			String userskillurl) {
		if (userid == null || userskill == null || Tools.isEmpty(userskillurl))
			return ResultCode.REQUIREINFO_NOTENOUGH;

		referdocServices.getReferdoc(userskill); // 检测userskill是否正确
		if (referdocServices.referdocinfo() == null)
			return ResultCode.REQUIREINFO_ERROR;
		List<UserSkill> existUserSkill = userSkillMapper
				.getUserSkillByUserIdAndSkill(userid, userskill);
		for (int i = 0; i < existUserSkill.size(); i++) {
			if (existUserSkill.get(i) != null
					&& !existUserSkill.get(i).getUser_skill_status().equals(2)) {
				switch (existUserSkill.get(i).getUser_skill_status()) {
				case 0:
					return ResultCode.USERSKILL_PENDING;
				case 1:
					return ResultCode.USERSKILL_ACTIVE;
				case 2:
					return ResultCode.USERSKILL_REJECT;
				default:
					return ResultCode.UNKNOWN_ERROR;
				}
			}
		}
		UserSkill tempUserSkill = new UserSkill();
		tempUserSkill.setUser_id(userid);
		tempUserSkill.setUser_skill(userskill);
		// 清除开头为;的符号
		while (userskillurl.startsWith(";")) {
			userskillurl = userskillurl.substring(1, userskillurl.length());
		}
		// 清除结尾为;的符号
		while (userskillurl.endsWith(";")) {
			userskillurl = userskillurl.substring(0, userskillurl.length() - 1);
		}
		tempUserSkill.setUser_skill_url(userskillurl);
		userSkillMapper.addUserSkill(tempUserSkill);
		return ResultCode.SUCCESS;

	}
	
	/**
	 * updateUserSkill 更新用户技能表信息
	 * @param userid
	 * @param userskill
	 * @param userskillurl
	 * @return
	 */
	public int updateUserSkill(Integer userid, Integer userskill, String userskillurl) {
		if (userid == null || userskill == null || Tools.isEmpty(userskillurl))
			return ResultCode.REQUIREINFO_NOTENOUGH;
		
		referdocServices.getReferdoc(userskill); // 检测userskill是否正确
		if (referdocServices.referdocinfo() == null) 
			return ResultCode.REQUIREINFO_ERROR;
//		UserSkill tempUserSkill = new UserSkill();
		List<UserSkill> tempUserSkill = userSkillMapper.getUserSkillByUserIdAndSkill(userid, userskill);
		
		for (int i = 0; i < tempUserSkill.size(); i++) {
			if (tempUserSkill.get(i) == null) {
				return ResultCode.USERSKILLINFO_NOTEXIST;
			}
			if (tempUserSkill.get(i).getUser_skill_status().equals(0)) {
				if(!userskillurl.equals(tempUserSkill.get(i).getUser_skill_url())){
					String[] tempUrl = tempUserSkill.get(i).getUser_skill_url().split(";");
					for (int j = 0; j < tempUrl.length; j++) {
						fileUploadServices.deleteFile(tempUrl[j]);// 先删除之前的证书文件
					}
					tempUserSkill.get(i).setUser_skill_url(userskillurl);
					tempUserSkill.get(i).setUser_skill_status(0); // 设置为未审核状态
					userSkillMapper.updateUserSkillById(tempUserSkill.get(i));
					return ResultCode.SUCCESS;
				} else {
					return ResultCode.USERSKILLINFO_ISSAME;
				}
			}
		}
		return ResultCode.USERSKILLINFO_NOTEXIST;
	}
	
	/**
	 * verifyUserSkill 审核用户技能
	 * @param userid
	 * @param userskill
	 * @param userskillstatus
	 * @return
	 */
//	public int verifyUserSkill(Integer userid, Integer userskill, Integer userskillstatus) {
//		if (userid == null || userskill == null || userskillstatus == null)
//			return ResultCode.REQUIREINFO_NOTENOUGH;
//		
//		UserSkill tempUserSkill = new UserSkill();
//		tempUserSkill = userSkillMapper.getUserSkillByUserIdAndSkill(userid, userskill);
//		User tempUser = userMapper.getUserById(userid);
//		String pushMsg = "";
//		if (tempUserSkill != null) {
//			switch (userskillstatus) {
//			case 0: // 未审核状态
//				return ResultCode.SUCCESS;
//			case 1: // 审核通过
//				String orgUserSkill = tempUser.getUser_skill();
//				if (!orgUserSkill.contains(userskill.toString())) {
//					orgUserSkill = orgUserSkill + ";" + userskill.toString();
//				}
//				
//				// 清除开头为;的符号
//				while(orgUserSkill.startsWith(";")){
//					orgUserSkill = orgUserSkill.substring(1, orgUserSkill.length());
//				}
//				// 清除结尾为;的符号
//				while(orgUserSkill.endsWith(";")){
//					orgUserSkill = orgUserSkill.substring(0, orgUserSkill.length() - 1);
//				}
//				tempUser.setUser_skill(orgUserSkill); // 更新对应用户的技能信息
//				
//				String skillName = referdocServices.getReferlist(orgUserSkill);
//				// 清除开头为;的符号
//				while(skillName.startsWith(";")){
//					skillName = skillName.substring(1, skillName.length());
//				}
//				// 清除结尾为;的符号
//				while(skillName.endsWith(";")){
//					skillName = skillName.substring(0, skillName.length() - 1);
//				}
//				tempUser.setUser_skill_name(skillName); // 更新对应用户的技能信息
//				userMapper.updateUser(tempUser);
//				
//				// 推送的文字
//				pushMsg = "Dear " + tempUser.getUser_name() + ", Your Skill has been approved by BBCall Admin.";
//				break;
//			case 2: // 审核不通过
//				
//				// 推送的文字
//				pushMsg = "Dear " + tempUser.getUser_name() + ", Your Skill has been rejected by BBCall Admin, please re-submit again.";
//				break;
//			default:
//				return ResultCode.REQUIREINFO_ERROR;
//			}
//			tempUserSkill.setUser_skill_status(userskillstatus);
//			userSkillMapper.updateUserSkillById(tempUserSkill);
//			
//			// 审核消息推送
//			if (tempUser.getUser_driver() != 0 && !Tools.isEmpty(tempUser.getUser_push_token())) {
//				devicePushServices.devicePush(tempUser.getUser_driver(), tempUser.getUser_push_token(), pushMsg, tempUser.getUser_type(), null, "master_skill");
//			}
//			
//			return ResultCode.SUCCESS;
//		} else {
//			return ResultCode.USERSKILLINFO_NOTEXIST;
//		}
//	}
	
	/**
	 * verifyUserSkillBySkillId 通过skillid审批用户技能
	 * @param skillid
	 * @param userskillstatus
	 * @return
	 */
	public int verifyUserSkillBySkillId(Integer skillid, Integer userskillstatus) {
		if (skillid == null || userskillstatus == null)
			return ResultCode.REQUIREINFO_NOTENOUGH;
		
		UserSkill tempUserSkill = new UserSkill();
		tempUserSkill = userSkillMapper.getUserSkillBySkillId(skillid);
		
		if (tempUserSkill != null) {
			Integer userid = tempUserSkill.getUser_id();
			Integer userskill = tempUserSkill.getUser_skill();
			User tempUser = userMapper.getUserById(userid);
			Integer pushMsgId = null;
			switch (userskillstatus) {
			case 0: // 未审核状态
				break;
			case 1: // 审核通过
				String orgUserSkill = tempUser.getUser_skill();
				if (!orgUserSkill.contains(userskill.toString())) {
					orgUserSkill = orgUserSkill + ";" + userskill.toString();
				}
				
				// 清除开头为;的符号
				while(orgUserSkill.startsWith(";")){
					orgUserSkill = orgUserSkill.substring(1, orgUserSkill.length());
				}
				// 清除结尾为;的符号
				while(orgUserSkill.endsWith(";")){
					orgUserSkill = orgUserSkill.substring(0, orgUserSkill.length() - 1);
				}
				tempUser.setUser_skill(orgUserSkill); // 更新对应用户的技能信息
				
				String skillName = referdocServices.getReferlist(orgUserSkill);
				// 清除开头为;的符号
				while(skillName.startsWith(";")){
					skillName = skillName.substring(1, skillName.length());
				}
				// 清除结尾为;的符号
				while(skillName.endsWith(";")){
					skillName = skillName.substring(0, skillName.length() - 1);
				}
				tempUser.setUser_skill_name(skillName); // 更新对应用户的技能信息
				userMapper.updateUser(tempUser);
				
				// pushMsgId = 4 : BBCall notification - Your skill has been approved by BBCall admin.
				pushMsgId = 4;
				break;
			case 2: // 审核不通过
				
				// pushMsgId = 5 : BBCall notification - Your skill has been rejected by BBCall admin, please re-submit again.
				pushMsgId = 5;
				break;
			default:
				return ResultCode.REQUIREINFO_ERROR;
			}
			tempUserSkill.setUser_skill_status(userskillstatus);
			userSkillMapper.updateUserSkillById(tempUserSkill);
			
			// 审核通过时，删除同一用户下，相同技能的，状态为不通过的列表
			if (userskillstatus.equals(1)) {
				List<UserSkill> userSkillList = userSkillMapper.getUserSkillByUserIdAndSkill(userid, userskill);
				for (int i = 0; i < userSkillList.size(); i++) {
					if (userSkillList.get(i) != null && userSkillList.get(i).getUser_skill_status().equals(2)) {
						String[] tempUrl = userSkillList.get(i).getUser_skill_url().split(";");
						for (int j = 0; j < tempUrl.length; j++) {
							fileUploadServices.deleteFile(tempUrl[j]);// 先删除之前的证书文件
						}
						userSkillMapper.deleteUserSkillBySkillId(userSkillList.get(i).getUserskill_id()); // 删除列表记录
					}
				}
			}
			
			// 审核消息推送
			if (tempUser.getUser_driver() != 0 && !Tools.isEmpty(tempUser.getUser_push_token())) {
				List<String> deviceTokens = new ArrayList<String>();
				deviceTokens.add(tempUser.getUser_push_token());
				devicePushServices.devicePush(tempUser.getUser_driver(), deviceTokens, pushMsgId, tempUser.getUser_type(), null);
			}
			return ResultCode.SUCCESS;
		} else {
			return ResultCode.USERSKILLINFO_NOTEXIST;
		}
	}

	/**
	 * deleteUserSkillBySkillId 刪除一個技能
	 * @param skillid
	 * @return
	 */
	public int deleteUserSkillBySkillId(Integer skillid) {
		if (skillid == null)
			return ResultCode.REQUIREINFO_NOTENOUGH;
		
		userSkillMapper.deleteUserSkillBySkillId(skillid);
		return ResultCode.SUCCESS;
	}
	
	/**
	 * deleteUserSkillByUserId 刪除userid下的全部技能
	 * @param userid
	 * @return
	 */
	public int deleteUserSkillByUserId(Integer userid) {
		if (userid == null)
			return ResultCode.REQUIREINFO_NOTENOUGH;
		
		userSkillMapper.deleteUserSkillBySkillId(userid);
		return ResultCode.SUCCESS;
	}
	
	/**
	 * getter & setter
	 * @return
	 */
	public List<UserSkill> getUserSkillList() {
		return userSkillList;
	}

}
