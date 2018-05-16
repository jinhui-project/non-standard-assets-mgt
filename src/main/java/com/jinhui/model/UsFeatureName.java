package com.jinhui.model;

public class UsFeatureName {
    private Integer id;

    private String moduleCode;

    private String moduleName;

    private String featureCode;

    private String featureName;

    private String description;
    
    private String editor;
    
    private String editorTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode == null ? null : moduleCode.trim();
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName == null ? null : moduleName.trim();
    }

    public String getFeatureCode() {
        return featureCode;
    }

    public void setFeatureCode(String featureCode) {
        this.featureCode = featureCode == null ? null : featureCode.trim();
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName == null ? null : featureName.trim();
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public String getEditorTime() {
		return editorTime;
	}

	public void setEditorTime(String editorTime) {
		this.editorTime = editorTime;
	}

	@Override
	public String toString() {
		return "UsFeatureName [id=" + id + ", moduleCode=" + moduleCode
				+ ", moduleName=" + moduleName + ", featureCode=" + featureCode
				+ ", featureName=" + featureName + ", description="
				+ description + ", editor=" + editor + ", editorTime="
				+ editorTime + "]";
	}

	
}