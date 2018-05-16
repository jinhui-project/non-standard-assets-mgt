package com.jinhui.model;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Title:Workflow</p>
 * <p>Description:机构审核流程表VO类</p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: 金汇金融有限公司</p>
 * @author 谷一帅- 75423426@qq.com
 * @version v1.0 2016-12-20
 */
public class Workflow implements Serializable {
	
	/**类的版本号*/
	private static final long serialVersionUID = 1973367569664000L;

	private Long id;
	/**
	 * 上传类型：0图片，1文件
	 */
	private int fileType;
	
	private String token;
	
	private List<WorkflowStep> workflowStepList;
	
	public List<WorkflowStep> getWorkflowStepList() {
		return workflowStepList;
	}
	public void setWorkflowStepList(List<WorkflowStep> workflowStepList) {
		this.workflowStepList = workflowStepList;
	}
	public int getFileType() {
		return fileType;
	}
	public void setFileType(int fileType) {
		this.fileType = fileType;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}