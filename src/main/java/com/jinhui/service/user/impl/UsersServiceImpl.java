package com.jinhui.service.user.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.jinhui.service.authorizing.SecurityCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.jinhui.constant.ConstantEntity;
import com.jinhui.mapper.user.UsRoleMapper;
import com.jinhui.mapper.user.UserMapper;
import com.jinhui.model.BaseObject;
import com.jinhui.model.Organization;
import com.jinhui.model.UsRole;
import com.jinhui.model.Users;
import com.jinhui.service.organization.OrganizationService;
import com.jinhui.service.user.UsersService;
import com.jinhui.util.RedisUtils;
import com.jinhui.util.UtilTool;

@Service
public class UsersServiceImpl implements UsersService {
	Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
    private UsRoleMapper usRoleMapper;

	@Autowired
	private SecurityCodeService securityCodeService;
	/**
	 * 注册时查询用户名称是否已经存在
	 */
	public Users selectByName(String usersName) {
		Users user = new Users();
		user.setUsername(usersName);
		Users u = userMapper.selectByName(user);
		return u;
	}

	/**
	 * 注册用户信息
	 */
	public void registered(Users record) throws Exception {
		record.setPassword(UtilTool.md5Tool(record.getPassword()));
		record.setCreateTime(UtilTool.dateConLon());
		userMapper.insert(record);
	}

	/**
	 * 登录查询
	 */
	public BaseObject queryUsersInfo(Users record,HttpServletRequest request)throws Exception {
		BaseObject bo = new BaseObject();
		String code = record.getCode();
		String redisNum = null;
		redisNum =   (String) RedisUtils.getValue( record.getMobile() ,ConstantEntity.LOGIN_NUM);
		if(null != redisNum && !"".equals(redisNum)){
			if(Integer.valueOf(redisNum) >=3){
				if(null == code || "".equals(code)){
					bo.setCode(1);
					bo.setMessage("手机号码或密码输入错误!");
					return bo;
				}
				if(!securityCodeService.exist(code)){
					bo.setCode(1);
					bo.setMessage("验证码不正确!");
					return bo;
				}
				/*String sessionCode = "";
				try {
					sessionCode = (String) RedisUtils.getValue(code.toUpperCase(),ConstantEntity.IMG_CODE);
				} catch (Exception e1) {
						logger.error("【后台登录login】RedisUtils异常："+e1);
						bo.setCode(1);
						bo.setMessage("验证码不存在!");
						return bo;		
				}
				if( null != sessionCode || !"".equals(sessionCode)){
					if(!code.equalsIgnoreCase(sessionCode)){
						bo.setCode(1);
						bo.setMessage("验证码不正确!");
						return bo;
					}
				}*/

			}
		}
		bo = loginCk(record,request);
		return bo;
	}
	
	/**
	 * 根据id查询
	 */
	public Users selectById(Integer id) {
		Users user = userMapper.selectById(id);
		user.setCreateTimeStr(UtilTool.stampToDate(user.getCreateTime()));
		return user;
	}
	
	/**
	 * 修改不等于null的实体
	 */
	public void updateNotNullInfo(Users record)throws Exception {
		record.setEditorTime(UtilTool.dateConLon());
		userMapper.updateNotNullInfo(record);
	}
	
	/**
	 * 分页查询
	 */
	public List<Users> queryUsersPage(Users users,int pageIndex, int pageSize) {
		 List<Users> list = new  ArrayList<Users>();
		int begin = pageIndex * pageSize;
		users.setOffset(begin);
		users.setLimit(pageSize);	
		 List<Users> listUser  = userMapper.queryUsersPage(users);
		if( null != listUser && listUser.size() >0 ){
			for( Users u:listUser ){
				u.setCreateTimeStr(UtilTool.stampToDate(u.getCreateTime()));
				u.setEditorTimeStr(UtilTool.stampToDate(u.getEditorTime()));
				list.add(u);
			}
		}
		return list;
	}
	/**
	 * 查询总数量
	 */
	public Integer queryUsersCount(Users users) {
		return userMapper.queryUsersCount(users);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 根据用户信息生成ticket
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	private String generateTicket(HttpServletRequest request,Users user ) 
	{
		long current  = System.currentTimeMillis();	
		StringBuilder bf = new StringBuilder();
		 bf.append(user.getId());
		 bf.append(user.getGid());
		 bf.append(user.getUsername());
		 bf.append(request.getHeader("x-forwarded-for")); //ticket包含客户端ip信息，同一个帐号在不同机器同时登录的情况应该生成不同的ticket
		 bf.append(current);
		 String msg="";
		 System.out.println("【bf.toString()】："+bf.toString());
		 msg =UtilTool.md5Tool(bf.toString());	
		return msg;
	}



	/**
	 * 登录是次数验证   
	 * @param record
	 * @param request
	 * @return
	 * @throws Exception
	 */
    private BaseObject loginCk(Users record,HttpServletRequest request) throws Exception{
    	BaseObject bo = new BaseObject();
    	UsRole usRole = new UsRole();
  		Users users = userMapper.queryUsersInfo(record);
  		
  		if( null != users){
  			if(users.getAuditStatus() == 2){
  				bo.setCode(1);
  				bo.setMessage("用户是禁用状态，无法登录!");
  				return bo;
  			}
  	
  			String ticket = generateTicket( request, users );	
  			RedisUtils.setValue( ticket ,ConstantEntity.USER_TICKET, JSON.toJSONString(users),30);
  			Organization ot = new Organization();
  			if(null != users.getGid()){
  				try {
  					ot = organizationService.queryOrganizationDetail(users.getGid());
  				} catch (Exception e) {
  					bo.setCode(1);
  					bo.setMessage("用户机构信息查询不到，无法登录!");
  					return bo;
  				}
  			}
  			//判断当前用户是否分配了角色
  			usRole.setUsId(Long.parseLong(users.getId().toString()));
  	  		List<UsRole> usRoleList = usRoleMapper.findList(usRole);
  	  		if(usRoleList.size() == 0){
  	  		    bo.setCode(2);
				bo.setMessage("您尚未分配角色，请联系相关人员开通账号权限，谢谢!");
				return bo;
  	  		}
  			
  			users.setOrganization(ot);
  			bo.setCode(0);
  			bo.setMessage(ticket);
  			bo.setJson(users);
  			bo.setExpire(System.currentTimeMillis() / 1000);
  			bo.setRedisutils_expire(ConstantEntity.REDISUTILS_EXPIRE);
  			RedisUtils.removeValue(record.getMobile() ,ConstantEntity.LOGIN_NUM);
  		}else{
  			int num=0;
  			num++;
  			String temp = null;
  			temp = (String) RedisUtils.getValue(record.getMobile() ,ConstantEntity.LOGIN_NUM);
  			logger.info("【后台 登录帐号】"+record.getMobile()+",【获取错误次数】："+temp);
  			if(null!=temp&&!"".equals(temp)){
  				num =num+Integer.valueOf(temp);
  			}
  			logger.info("【后台 登录帐号】"+record.getMobile()+",【错误次数】："+num);
  			RedisUtils.setValue(record.getMobile() ,ConstantEntity.LOGIN_NUM, num+"",60*24);
  			bo.setCode(1);
  			bo.setMessage("手机号码或者密码不正确!");
  			bo.setLoginNum(num);
  		}
  		return bo;
      }

    @Override
    public List<Users> findList(Users users) {
        return userMapper.findList(users);
    }

    public List<Users> queryUsersAndUsRolePage(Users users,int pageIndex, int pageSize){
        int begin = pageIndex * pageSize;
        users.setOffset(begin);
        users.setLimit(pageSize); 
        return userMapper.queryUsersAndUsRolePage(users);
    }
    public Integer queryUsersAndUsRoleCount(Users users){
        return userMapper.queryUsersAndUsRoleCount(users);
    }
}
