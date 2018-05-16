package com.jinhui.mapper.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jinhui.model.Users;

public interface UserMapper {
	
	Users selectByuserName(@Param("record") Users record );
	
	void insert(@Param("record") Users record );
	
	Users queryUsersInfo(@Param("record") Users record );
	
	Users selectById(@Param("id")Integer id);
	
	List<Users>queryUsersPage(Users users);
	
	Integer queryUsersCount(Users users);
	
	void updateNotNullInfo(@Param("record") Users record );
	
	
	Users selectByName(@Param("record") Users record );
	
	List<Users>findList(Users users);
	
	List<Users> queryUsersAndUsRolePage(Users users);
	Integer queryUsersAndUsRoleCount(Users users);

}
