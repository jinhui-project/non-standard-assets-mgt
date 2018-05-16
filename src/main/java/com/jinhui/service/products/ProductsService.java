package com.jinhui.service.products;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.jinhui.model.Products;

/**
 * 产品服务层接口
 * @author wsc
 *
 */
public interface ProductsService {
    /**
     * 查询单个产品详情
     * @param productId
     * @return
     */
	public Products getProductsById(int productId);
	
	/**
	 * 分页查询我的工作台--》产品列表
	 * @param pageNo
	 * @param pageSize
	 * @param roleType  角色类型    1 备案机构   2 资金方(我反馈的) 3资金方(我销售的)  4 资产方 5 其他角色 
	 * @param orgId   机构代码
	 * @return
	 */
	public PageInfo<Products> getProductList(Integer pageNum,Integer pageSize,String roleType,String orgId);
	
	/**
	 * 分页查询产品专场所有产品
	 * @param pageNum
	 * @param pageSize
	 * @param productName  产品名称
	 * @param scale  产品规模
	 * @param leftScale  剩余规模
	 * @param beginAmount  起投金额
	 * @param term  产品期限
	 * @param fixedRatio  预期收益率
	 * @param status  产品状态
	 * @param fromTime  开始修改时间
	 * @param toTime  结束修改时间
	 * @return
	 */
	public PageInfo<Products> queryProductListBySelect(Integer pageNum,Integer pageSize,String productName,String scale,String leftScale,
			String beginAmount,String term,String fixedRatio,String status,String fromTime,String toTime);
	
	/**
     * 查询“销售结束”状态的产品，用于“收益中”的定时任务
     * @return
     */
    List<Products> getProductsForIncomeQuartz();
    
    /**
     * 查询“收益中”状态的产品，用于“已兑付”的定时任务
     * @return
     */
    List<Products> getProductsForOverQuartz();
	
	/**
	 * 保存产品
	 * @param product
	 */
	public int saveProduct(Products product);
	
	/**
	 * 提交产品
	 * @param product
	 */
	public int commitProduct(Products product);
	
	/**
	 * 修改产品
	 * @param product
	 * @return
	 */
	public int updateProductBySelective(Products product);
	
	/**
	 * 发布产品
	 * @param productId
	 */
	public int publishProduct(int productId,int editorId);
	
	/**
	 * 撤回产品
	 * @param productId
	 */
	public int withdrawProduct(int productId,int editorId);
	
	/**
	 * 完结产品
	 * @param productId
	 */
	public int finishProduct(int productId,int editorId);
	
	/**
     * 删除 产品
     * @param id
     * @return
     */
	public int deleteProduct(Integer id,Integer editorId);
	
	/**
     * 结束产品(销售结束)
     * @param id
     * @return
     */
	public int overProduct(Integer id,Integer editorId);
	
	/**
	 * 火爆销售
	 * @param id
	 * @param type 1 资产  2 产品
	 * @return
	 */
	public int toHot(Integer id,Integer type);
}
