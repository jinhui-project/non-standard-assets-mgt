package com.jinhui.model;

import java.io.Serializable;

/**
 * <p>Title:AgreementPowerTemplate</p>
 * <p>Description:(权限)协议模板里三个分类方式和可选值表VO类</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: 金汇金融有限公司</p>
 * @author 谷一帅- 75423426@qq.com
 * @version v1.0 2017-03-29
 */
public class AgreementPowerTemplate implements Serializable {
	
	/**类的版本号*/
	private static final long serialVersionUID = 2113703840385024L;

	

 */
	public Long getGid() {
		return gid;
	}
	public void setGid(Long gid) {
		this.gid = gid;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getRecordInstitutionsType() {
		return recordInstitutionsType;
	}
	public void setRecordInstitutionsType(String recordInstitutionsType) {
		this.recordInstitutionsType = recordInstitutionsType;
	}
	public Integer getSigningType() {
		return signingType;
	}
	public void setSigningType(Integer signingType) {
		this.signingType = signingType;
	}
	public Integer getMoneySideType() {
		return moneySideType;
	}
	public void setMoneySideType(Integer moneySideType) {
		this.moneySideType = moneySideType;
	}
	public Integer getAssetType() {
		return assetType;
	}
	public void setAssetType(Integer assetType) {
		this.assetType = assetType;
	}
	public Integer getOrganizationType() {
		return organizationType;
	}
	public void setOrganizationType(Integer organizationType) {
		this.organizationType = organizationType;
	}
	public Integer getTrusteeType() {
		return trusteeType;
	}
	public void setTrusteeType(Integer trusteeType) {
		this.trusteeType = trusteeType;
	}
	public Integer getServiceType() {
		return serviceType;
	}
	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}
	public Integer getOtherType() {
		return otherType;
	}
	public void setOtherType(Integer otherType) {
		this.otherType = otherType;
	}
}