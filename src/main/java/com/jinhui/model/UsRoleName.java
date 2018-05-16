
package com.jinhui.model;

import java.io.Serializable;

/**
 * <p>
 * Title:UsRoleName
 * </p>
 * <p>
 * Description:角色表VO类
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
public class UsRoleName implements Serializable {

    /** 类的版本号 */
    private static final long serialVersionUID = 2142125779732480L;

    public Long id;

    /** 角色名称 */
    private String rName;

    /** 描述 */
    private String rDescribe;
    /** 角色状态：0启用，1禁用*/
    private Integer rState;
    
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

    
    public String getrName() {
        return rName;
    }

    
    public void setrName(String rName) {
        this.rName = rName;
    }

    
    public String getrDescribe() {
        return rDescribe;
    }

    
    public void setrDescribe(String rDescribe) {
        this.rDescribe = rDescribe;
    }


    
    public Integer getrState() {
        return rState;
    }


    
    public void setrState(Integer rState) {
        this.rState = rState;
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



}
