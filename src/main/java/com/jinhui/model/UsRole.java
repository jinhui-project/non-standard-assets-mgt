
package com.jinhui.model;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * Title:UsRole
 * </p>
 * <p>
 * Description:用户角色表VO类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2017
 * </p>
 * <p>
 * Company: 金汇金融有限公司
 * </p>
 * 
 * @author 谷一帅- 75423426@qq.com
 * @version v1.0 2017-04-18
 */
public class UsRole implements Serializable {

    /** 类的版本号 */
    private static final long serialVersionUID = 2142195805816832L;

    private Long id;

    /** 角色id */
    private Long roleId;

    /** 角色名称 */
    private String roleName;

    /** 用户id */
    private Long usId;
    /** 角色id 用逗号隔开 */
    private String  roleIds;
    
    /** 用户id 用逗号隔开 */
    private String  usIds;
    
    /** 删除用户id 用逗号隔开 */
    private String  delUsIds;
    
    
    
    private  List<UsFeature> usFeatures;
    
    private List<Users> userList; 
    private int pageIndex ;
    private int pageSize;
    
    private int offset;
    private int limit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Long getUsId() {
        return usId;
    }

    public void setUsId(Long usId) {
        this.usId = usId;
    }

    
    public String getRoleIds() {
        return roleIds;
    }

    
    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    
    public List<UsFeature> getUsFeatures() {
        return usFeatures;
    }

    
    public void setUsFeatures(List<UsFeature> usFeatures) {
        this.usFeatures = usFeatures;
    }

    
    public String getUsIds() {
        return usIds;
    }

    
    public void setUsIds(String usIds) {
        this.usIds = usIds;
    }

    
    public int getPageIndex() {
        return pageIndex;
    }

    
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    
    public int getPageSize() {
        return pageSize;
    }

    
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    
    public int getOffset() {
        return offset;
    }

    
    public void setOffset(int offset) {
        this.offset = offset;
    }

    
    public int getLimit() {
        return limit;
    }

    
    public void setLimit(int limit) {
        this.limit = limit;
    }

    
    public List<Users> getUserList() {
        return userList;
    }

    
    public void setUserList(List<Users> userList) {
        this.userList = userList;
    }

    
    public String getDelUsIds() {
        return delUsIds;
    }

    
    public void setDelUsIds(String delUsIds) {
        this.delUsIds = delUsIds;
    }

}
