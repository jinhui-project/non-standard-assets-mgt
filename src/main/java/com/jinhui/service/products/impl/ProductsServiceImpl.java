package com.jinhui.service.products.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jinhui.mapper.assets.AssetsMapper;
import com.jinhui.mapper.products.ProductsMapper;
import com.jinhui.model.Products;
import com.jinhui.service.products.ProductsService;

/**
 *  产品服务层实现类
 * @author wsc
 *
 */
@Service("productsService")
public class ProductsServiceImpl implements ProductsService {

	@Resource
	private ProductsMapper productsMapper;
	@Resource
	private AssetsMapper assetsMapper;

	public Products getProductsById(int productId) {
		return this.productsMapper.selectByPrimaryKey(productId);
	}
    
	/**
	 * 分页查询我的工作台--》产品列表
	 * @param pageNo
	 * @param pageSize
	 * @param roleType  角色类型    1 备案机构   2 资金方(我反馈的) 3资金方(我销售的)  4 资产方 5 其他角色 
	 * @param orgId   机构代码
	 * @return
	 */
	public PageInfo<Products> getProductList(Integer pageNum, Integer pageSize,String roleType,String orgId) {
		pageNum = pageNum == null ? 1 : pageNum;
		pageSize = pageSize == null ? 10 : pageSize;
		PageHelper.startPage(pageNum, pageSize);
		
		List<Products> list = null;
		if("1".equals(roleType)){
			list = productsMapper.getRecordProductList(orgId);   //查询我的工作台--》产品列表(备案机构方)
		}else if("2".equals(roleType)){
			list = productsMapper.getFeedBackProductList(orgId);   //查询我的工作台--》产品列表(资金方_我反馈的)
		}else if("3".equals(roleType)){
			list = productsMapper.getSaleProductList(orgId);   //查询我的工作台--》产品列表(资金方_我销售的)
		}else if("4".equals(roleType)){
			list = productsMapper.getPropertyProductList(orgId);  //查询我的工作台--》产品列表(资产方)
		}else{
			list = new ArrayList();
		}
		return new PageInfo<Products>(list);
	}

	//保存产品，“草稿”状态
	public int saveProduct(Products product) {
		return this.productsMapper.saveProduct(product);
	}
	
	//提交产品，“提交”状态
	public int commitProduct(Products product) {
		return this.productsMapper.commitProduct(product);
	}
	
	// 发布产品，变成“销售策划”状态 
	public int publishProduct(int productId,int editorId) {
		return this.productsMapper.publishProduct(productId,editorId);
	}
	
	// 撤回产品，变成“草稿”状态 
	public int withdrawProduct(int productId,int editorId) {
		return this.productsMapper.withdrawProduct(productId,editorId);
	}
	
	// 完结产品，变成“完结”状态 
	public int finishProduct(int productId,int editorId) {
		return this.productsMapper.finishProduct(productId,editorId);
	}

	public int updateProductBySelective(Products product) {
		return this.productsMapper.updateByPrimaryKeySelective(product);
	}

	public int deleteProduct(Integer id,Integer editorId) {
		return this.productsMapper.deleteProduct(id,editorId);
	}

	public List<Products> getProductsForIncomeQuartz() {
		return productsMapper.getProductsForIncomeQuartz();
	}
	
	public List<Products> getProductsForOverQuartz() {
		return productsMapper.getProductsForOverQuartz();
	}

	public PageInfo<Products> queryProductListBySelect(Integer pageNum,Integer pageSize, String productName, String scale,
			String leftScale, String beginAmount, String term,String fixedRatio, String status, String fromTime, String toTime) {
		pageNum = pageNum == null ? 1 : pageNum;
		pageSize = pageSize == null ? 10 : pageSize;
		PageHelper.startPage(pageNum, pageSize);
		if("".equals(productName) || productName == null || "null".equals(productName)){
			productName = null;
		}
		if("".equals(scale) || scale == null || "null".equals(scale)){
			scale = null;
		}
		if("".equals(leftScale) || leftScale == null || "null".equals(leftScale)){
			leftScale = null;
		}
		if("".equals(beginAmount) || beginAmount == null || "null".equals(beginAmount)){
			beginAmount = null;
		}
		if("".equals(term) || term == null || "null".equals(term)){
			term = null;
		}
		if("".equals(fixedRatio) || fixedRatio == null || "null".equals(fixedRatio)){
			fixedRatio = null;
		}
		if("".equals(status) || status == null || "null".equals(status)){
			status = null;
		}
		if("".equals(fromTime) || fromTime == null || "null".equals(fromTime)){
			fromTime = null;
		}
		if("".equals(toTime) || toTime == null || "null".equals(toTime)){
			toTime = null;
		}
		List<Products> list = productsMapper.queryProductListBySelect(productName,scale,leftScale,beginAmount,term,fixedRatio,status,fromTime,toTime);
		return new PageInfo<Products>(list);
	}

	public int overProduct(Integer id,Integer editorId) {
		return productsMapper.overProduct(id,editorId);
	}

	/**
	 * 火爆销售
	 * @param id
	 * @param type 1 资产  2 产品
	 * @return
	 */
	@Override
	public int toHot(Integer id, Integer type) {
		int flag = 0;
		if(type == 1){  //资产 火爆销售
			flag = assetsMapper.toHot(id);
		}else{
			flag = productsMapper.toHot(id);
		}
		return flag;
	}

}
