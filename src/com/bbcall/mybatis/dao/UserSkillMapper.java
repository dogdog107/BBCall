package com.bbcall.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bbcall.mybatis.table.UserSkill;

public interface UserSkillMapper {
	// 创建用户技能
	public void addUserSkill(UserSkill userskill);

	// 更新用户技能
	public void updateUserSkillById(UserSkill userskill);
	
	// 删除用户技能
	public void deleteUserSkillByUserId(Integer user_id);
	
	// 删除用户技能
	public void deleteUserSkillBySkillId(Integer userskill_id);

	// 通过userid获取用户技能
	public List<UserSkill> getUserSkillByUserId(Integer user_id);
	
	// 获取用户技能
	public List<UserSkill> getAll();
	
	// 通过skillid获取技能信息
	public UserSkill getUserSkillBySkillId(Integer userskill_id);
	
	// 通过userid获取用户技能
	public UserSkill getUserSkillByUserIdAndSkill(
			@Param("user_id") Integer user_id,
			@Param("user_skill") Integer user_skill);
}
