package com.jinhui.mapper.workflow;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jinhui.model.Workflow;
import com.jinhui.model.WorkflowStep;

/**
 * 
 * @author jinhui
 *
 */
public interface WorkflowMapper {
	
	List<Workflow> getWorkflowListByGid(Integer orgId);
	
	Long saveWorkflow(@Param("record")Workflow workflow);
	
	
	List<Workflow> queryList(@Param("gid")Long gid);

}
