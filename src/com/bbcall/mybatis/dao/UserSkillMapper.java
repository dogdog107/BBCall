package com.bbcall.mybatis.dao;

import java.util.List;

import com.bbcall.mybatis.table.UserSkill;

public interface UserSkillMapper {
	// 创建用户技能
	public void addUserSkill(UserSkill userskill);

	// 更新用户技能
	public void updateUserSkill(UserSkill userskill);
	
	// 删除用户技能
	public void deleteUserSkill(Integer user_id);

	// 通过id获取用户技能
	public List<UserSkill> getUserSkillById(Integer user_id);
}
