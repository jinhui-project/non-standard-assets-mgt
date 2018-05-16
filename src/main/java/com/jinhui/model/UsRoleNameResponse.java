package com.jinhui.model;

import java.io.Serializable;
import java.util.List;


public class UsRoleNameResponse implements Serializable{
    /** 
     * TODO
     */ 
    private static final long serialVersionUID = 3110627975275808304L;

    private List<UsRoleName> list;
    
    private int totalCount;

    
    public List<UsRoleName> getList() {
        return list;
    }

    
    public void setList(List<UsRoleName> list) {
        this.list = list;
    }

    
    public int getTotalCount() {
        return totalCount;
    }

    
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

}
