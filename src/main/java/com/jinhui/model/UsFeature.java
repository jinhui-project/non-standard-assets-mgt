package com.jinhui.model;

public class UsFeature {
    private Integer id;

    private Integer featureId;

    private Integer roleId;

    private Integer editorId;

    private String editorTime;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFeatureId() {
        return featureId;
    }

    public void setFeatureId(Integer featureId) {
        this.featureId = featureId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getEditorId() {
        return editorId;
    }

    public void setEditorId(Integer editorId) {
        this.editorId = editorId;
    }

	public String getEditorTime() {
		return editorTime;
	}

	public void setEditorTime(String editorTime) {
		this.editorTime = editorTime;
	}

	@Override
	public String toString() {
		return "UsFeature [id=" + id + ", featureId=" + featureId + ", roleId="
				+ roleId + ", editorId=" + editorId + ", editorTime="
				+ editorTime + "]";
	}

}