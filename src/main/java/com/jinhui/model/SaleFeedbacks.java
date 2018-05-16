package com.jinhui.model;

import java.math.BigDecimal;

public class SaleFeedbacks {
	
	private String token;
	
    private Integer id;

    private Integer assetId;

    private String contents;

    private Integer feedbackerId;

    private Integer feedbackTime;

    private Integer gid;

    private String orgName;

    private byte saleType;

    private Boolean isUnderwriting;

    private BigDecimal packPrice;

    private BigDecimal numDistribution;

    private String commission;

    private byte selfFunk;

    private String contactInfo;

    private String presented;

    private String createAt;

    private String updateAt;

    private byte feedType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents == null ? null : contents.trim();
    }

    public Integer getFeedbackerId() {
        return feedbackerId;
    }

    public void setFeedbackerId(Integer feedbackerId) {
        this.feedbackerId = feedbackerId;
    }

    public Integer getFeedbackTime() {
        return feedbackTime;
    }

    public void setFeedbackTime(Integer feedbackTime) {
        this.feedbackTime = feedbackTime;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    

    public Boolean getIsUnderwriting() {
        return isUnderwriting;
    }

    public void setIsUnderwriting(Boolean isUnderwriting) {
        this.isUnderwriting = isUnderwriting;
    }

    public BigDecimal getPackPrice() {
        return packPrice;
    }

    public void setPackPrice(BigDecimal packPrice) {
        this.packPrice = packPrice;
    }

    public BigDecimal getNumDistribution() {
        return numDistribution;
    }

    public void setNumDistribution(BigDecimal numDistribution) {
        this.numDistribution = numDistribution;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission == null ? null : commission.trim();
    }

   

    public byte getSaleType() {
		return saleType;
	}

	public void setSaleType(byte saleType) {
		this.saleType = saleType;
	}

	public byte getSelfFunk() {
		return selfFunk;
	}

	public void setSelfFunk(byte selfFunk) {
		this.selfFunk = selfFunk;
	}

	public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo == null ? null : contactInfo.trim();
    }

    public String getPresented() {
        return presented;
    }

    public void setPresented(String presented) {
        this.presented = presented == null ? null : presented.trim();
    }

    public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	public String getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(String updateAt) {
		this.updateAt = updateAt;
	}

	

	public byte getFeedType() {
		return feedType;
	}

	public void setFeedType(byte feedType) {
		this.feedType = feedType;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
    
    
}