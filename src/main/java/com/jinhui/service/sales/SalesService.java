package com.jinhui.service.sales;

import java.util.List;

import com.jinhui.model.Products;
import com.jinhui.model.Sales;

/**
 * 销售计划服务层接口
 * @author wsc
 *
 */
public interface SalesService {
	/**
	 * 查询单个产品的销售计划列表
	 * @return
	 */
	public List<Sales> getSalesListByProductId(Integer productId);
	
	/**
	 * 添加销售计划
	 * @param sales
	 */
	public int addSales(List<Sales> saleList);
	
	/**
	 * 修改销售计划
	 * @param sales
	 * @return
	 */
	public int updateSalesBySelective(List<Sales> saleList);
	/**
	 * 删除销售计划
	 * @param salesId
	 */
	public int deleteSales(Integer salesId);
	
	/**
	 * 开放销售
	 * @param sale
	 * @return
	 */
	public int updateOpenSales(Products product);
	
	/**
     * 更新产品剩余规模 
     * @param dealShares
     * @param productId
     * @return
     */
	public int updateLeftScale(List<Sales> saleList);
		
}
