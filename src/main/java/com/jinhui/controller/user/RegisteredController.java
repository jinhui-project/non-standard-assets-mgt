package com.jinhui.controller.user;

import javax.servlet.http.HttpServletRequest;






import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.jinhui.constant.ConstantEntity;
import com.jinhui.enums.AuditStatus;
import com.jinhui.enums.ImgOrFile;
import com.jinhui.model.BaseObject;
import com.jinhui.model.Users;
import com.jinhui.service.user.UsersService;
import com.jinhui.service.workflow.WorkflowService;
import com.jinhui.util.RedisUtils;
import com.jinhui.util.UpLoadUtil;
/**
 * 注册Controller
 * @author jinhui
 *
 */
@Controller
@RequestMapping("/admin")
public class RegisteredController {
	Logger logger = LoggerFactory.getLogger(RegisteredController.class);
	
	@Autowired
	private UsersService usersService;
	
	/**
	 * 文件开头地址
	 */
	@Value("${uploadPath}")
	private  String uploadPath;
	

	
	/**
	 * 查询用户是否存在
	 * @param userName
	 * @return
	 */
	@RequestMapping("/queryUserInfo")
	@ResponseBody
	public BaseObject queryUserInfo(String userName){
		Users u =new Users();
		BaseObject obj = new BaseObject();
		u = usersService.selectByName(userName);
		if( null == u ){
			obj.setMessage("用户可以注册"); 
			obj.setCode(0);
		}else{
			obj.setMessage("用户已存在"); 
			obj.setCode(1);
		}
		return obj;	
	}
	/**
	 * 用户注册
	 * @return
	 */
	@RequestMapping("/registeredInfo")
	@ResponseBody
	public BaseObject registeredInfo(Users record,MultipartFile imgcontent,HttpServletRequest request){
		BaseObject obj = new BaseObject();
		Users u =new Users();
		u = usersService.selectByName(record.getUsername());
		String phoneCode ="";
//		try {
//			phoneCode = request.getSession().getAttribute("phoneCode").toString();
//			if( null == phoneCode || "".equals(phoneCode)){
//				obj.setCode(1);
//				obj.setMessage("手机验证码失效!");
//				return obj;	
//			}
//		} catch (Exception e) {
//			obj.setCode(1);
//			obj.setMessage("手机验证码失效!");
//			return obj;
//		}
		
		if(!record.getPhoneCode().equals("1111")){
			obj.setCode(1);
			obj.setMessage("验证码不正确!");
			return obj;
		}
		
//		if(!record.getPwd().equals(record.getPassword())){
//			obj.setMessage("两次密码输入不一致"); 
//			obj.setCode(1);
//			return obj;
//		}
		String code = "";
		try {
			code = (String) RedisUtils.getValue(record.getCode().toUpperCase(),ConstantEntity.IMG_CODE);
		} catch (Exception e1) {
			logger.error("【用户注册 图形验证码 registeredInfo】RedisUtils异常："+e1);
			obj.setCode(1);
			obj.setMessage("图形验证码不存在!");
			return obj;
		}
		if(!record.getCode().equalsIgnoreCase(code)){
			obj.setCode(1);
			obj.setMessage("图形验证码不正确!");
			return obj;
		}
		
		if( null != u ){
			obj.setMessage("用户已注册"); 
			obj.setCode(1);
			return obj;
		}
		
		try {
			UpLoadUtil upLoadUrl =new UpLoadUtil();
//			record.setCardPath(upLoadUrl.upLoad(imgcontent, ImgOrFile.IMG_TYPE.code,uploadPath,request));//获取文件路径
			record.setPower("000");
			record.setAuditStatus(AuditStatus.NOT_ACTIVATION.code);//初始化状态：未激活
			usersService.registered(record);
			obj.setMessage("注册成功"); 
			obj.setCode(0);
		} catch (Exception e) {
			logger.error("【用户注册失败registeredInfo】异常为："+e);
			obj.setMessage("注册失败"); 
			obj.setCode(1);
		}
		
		return obj;
		
	}
	
	@Autowired
	private WorkflowService workflowService;
	/**
	 * 测试方法  不需要关注
	 * @return
	 */
	@RequestMapping("/test")
	@ResponseBody
	public ModelAndView test(Users record){
		logger.info("【】【】【】【】【】【】【】【】【】【测试日志】");
		ModelMap m = null;
		System.out.println("【uploadPath】Controller"+uploadPath);
		
		System.out.println("【uploadPath】service"+workflowService.upPath());

		
		System.out.println(uploadPath+uploadPath);
		return new ModelAndView("index",m);
//		return "ssss";

	}
	
	
	
	/**
	 * 测试方法  不需要关注
	 * @return
	 */
//	@RequestMapping("/uploadInfo")
//	@ResponseBody
//	public String uploadInfo(MultipartFile imgcontent,HttpServletRequest request){
//		logger.info("【】【】【】【】【】【】【】【】【】【uploadInfo】");
////		if(imgcontent.getSize()>1024){
////			return "文件过大不能上传";
////		}
//		
//		UpLoadUtil u = new UpLoadUtil();
//		String a ="";
//		try {
//			a = u.upLoad(imgcontent, 0, uploadPath,request);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return a;
//
//
//	}



	

	
	
	

    

	

}
