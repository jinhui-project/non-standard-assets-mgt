package com.jinhui.service.sales.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jinhui.controller.products.ProductsController;
import com.jinhui.mapper.products.ProductsMapper;
import com.jinhui.mapper.sales.SalesMapper;
import com.jinhui.model.Products;
import com.jinhui.model.Sales;
import com.jinhui.service.sales.SalesService;

/**
 * 销售计划服务层实现类
 * @author wsc
 *
 */
@Service("salesService")
public class SalesServiceImpl implements SalesService {
	private static Logger logger = LoggerFactory.getLogger(SalesServiceImpl.class);
	@Resource
	private SalesMapper salesMapper;
	
	@Resource
	private ProductsMapper productsMapper;

	public List<Sales> getSalesListByProductId(Integer productId) {
		return this.salesMapper.getSalesListByProductId(productId);
	}
	
	public int updateOpenSales(Products product){
		return this.productsMapper.updateOpenSales(product);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public int addSales(List<Sales> saleList) {
		
		int flag = 0;
		for(Sales sale:saleList){
			/**
			 * "确认"开放销售的时候，如果已经存在的销售记录就更新
			 */
			if(sale.getId() == null || "".equals(sale.getId()) || "null".equals(sale.getId())){  //不存在
				flag = this.salesMapper.insertSelective(sale);
			}else{
				 flag = this.salesMapper.updateByPrimaryKeySelective(sale);
			}
			if(flag == 0){
				return 0;
			}			
		}
		return flag;
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public int updateSalesBySelective(List<Sales> saleList) {
		int flag = 0;
		for(Sales sale:saleList){
			if(sale.getId() == null || "".equals(sale.getId()) || "null".equals(sale.getId())){  //不存在
				flag = this.salesMapper.insertSelective(sale);
			}else{
				 flag = this.salesMapper.updateByPrimaryKeySelective(sale);
			}
			if(flag == 0){
				return 0;
			}			
		}
		return flag;
	}

	public int deleteSales(Integer salesId) {
		return this.salesMapper.deleteByPrimaryKey(salesId);
	}

	/**
     * 更新产品剩余规模 
     * @param dealShares
     * @param productId
     * @return
     */
	public int updateLeftScale(List<Sales> saleList) {
		BigDecimal dealShares = new BigDecimal(0);
		for(Sales sale:saleList){
			dealShares = dealShares.add(sale.getDealShares());
		}
		return this.salesMapper.updateLeftScale(dealShares, saleList.get(0).getProductId());
	}

}
