package com.jinhui.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jinhui.controller.base.WebConstants;
import com.jinhui.mapper.authorizing.entity.AuthorizingEntity;
import com.jinhui.service.authorizing.AuthorizingService;
import com.jinhui.service.authorizing.shiro.PermissionUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.subject.WebSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jinhui.constant.ConstantEntity;
import com.jinhui.model.BaseObject;
import com.jinhui.model.Users;
import com.jinhui.service.actionLog.ActionLogService;
import com.jinhui.service.user.UsersService;
import com.jinhui.util.RedisUtils;
import com.jinhui.util.UtilTool;

import java.util.*;

/**
 * 登录
 * @author jinhui
 *
 */
@Controller
@RequestMapping("/admin")
public class LoginController {
	Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private UsersService usersService;
	@Autowired
	private ActionLogService actionLogService;

	@Autowired
	private AuthorizingService authorizingService;

	/**
	 * 登录
	 * @return
	 */
	@RequestMapping("/login")
	@ResponseBody
	public BaseObject login(String userName, String password,String code,HttpServletRequest request,HttpServletResponse response){
		BaseObject bo = new BaseObject();
		Users us = new Users ();
		
		try {
			us.setCode(code);
			us.setUsername(userName);
			us.setPassword(UtilTool.md5Tool(password));
			bo =  usersService.queryUsersInfo(us, request);
			if(!"".equals(bo.getJson()) && null != bo.getJson()){
				//记录操作日志
				actionLogService.insertActionLog(request, bo.getJson());
			}
			if(bo.getCode() == Long.parseLong(WebConstants.RESULT_SUCCESS_CODE)) {
				Subject subject = new Subject.Builder().buildSubject();
				UsernamePasswordToken token = new UsernamePasswordToken(userName, UtilTool.md5Tool(password));
				token.setRememberMe(true);
				subject.login(token);
				//返回sessionId
				Session session = subject.getSession();
				bo.getJson().setToken((String) session.getId());
				bo.setMessage((String) session.getId());
				//返回用户权限信息
				List<AuthorizingEntity> authorizingEntities = authorizingService.findAuthorization(userName);
				Map<String,Boolean> permissions = new HashMap<String, Boolean>();
				for(AuthorizingEntity entity:authorizingEntities){
					String uri = entity.getFeatureUri();
					if(StringUtils.isEmpty(uri))
						continue;
					int argIndex = uri.indexOf(":");
					if(argIndex == -1) {
						permissions.put(uri, Boolean.TRUE);
					} else {
						String url = uri.substring(0, argIndex).trim();
						String[] args = uri.substring(argIndex+1).split("\\|");
						for(int i=0;i<args.length;i++){
							if(!"".equals(args[i].trim())){
								permissions.put(url+":"+args[i], Boolean.TRUE);
							}
						}
					}
				}
				bo.getJson().setPermissions(permissions);
			}

			return bo;
		} catch (Exception e) {
			logger.error("【登录login】异常："+e);
			bo.setCode(1);
			bo.setMessage("用户没有后台权限!!");
			return bo;
		}	
	}
	
	/**
	 * 测试验证是否存在
	 * @param ticket
	 * @param request
	 * @return
	 */
	@RequestMapping("/existInfo")
	@ResponseBody
	public String existInfo(String ticket ,HttpServletRequest request){
		String us =  (String) request.getSession().getAttribute(ticket);
		 
		return us;
		
	}
	/**
	 * 退出用户
	 * @param ticket
	 * @return
	 */
	@RequestMapping("/exitUser")
	@ResponseBody
	public BaseObject exitUser(String ticket ){
		BaseObject bo = new BaseObject();
		try {
			//RedisUtils.removeValue(ticket, ConstantEntity.USER_TICKET);
			PermissionUtils.logout();
			bo.setCode(0);
			bo.setMessage("已退出");
			return bo;
		} catch (Exception e) {
			logger.error("【退出exitUser】RedisUtils.removeValue异常："+e);
			bo.setCode(1);
			bo.setMessage("");
			return bo;
		}		
	}

}
