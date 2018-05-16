package com.jinhui.model;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * <p>Title:Menus</p>
 * <p>Description:菜单表VO类</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: 金汇金融有限公司</p>
 * @author 谷一帅- 75423426@qq.com
 * @version v1.0 2017-02-03
 */
public class Menus implements Serializable {
	
	/**类的版本号*/
	private static final long serialVersionUID = 2037409579583488L;

	private Long id;
	
	private String token;
	
	private List<Menus> node;
	public List<Menus> getNode() {
		return node;
	}
	public void setNode(List<Menus> node) {
		this.node = node;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}