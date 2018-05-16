package com.jinhui.controller.sales;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jinhui.controller.products.ProductsController;
import com.jinhui.mapper.user.UserMapper;
import com.jinhui.model.Products;
import com.jinhui.model.Sales;
import com.jinhui.service.sales.SalesService;
import com.jinhui.util.FormatReturn;
import com.jinhui.util.TokenUtil;

@Controller
@RequestMapping("/sales")
public class SalesController {
	private static Logger logger = LoggerFactory.getLogger(ProductsController.class);

	@Autowired
	private SalesService salesService;
	
	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 查询单个产品的销售计划列表
	 * @param productId
	 * @return
	 */
	@RequestMapping(value="/querySalesListByProductId",method=RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String querySalesListByProductId(@RequestParam("productId") int productId){
		logger.info("输入：  "+ "productId="+productId);
		List<Sales> saleList = salesService.getSalesListByProductId(productId);
		logger.info("输出：   "+JSON.toJSONString(saleList));
		return JSON.toJSONString(saleList,SerializerFeature.WriteMapNullValue);
	}
	
	/**
	 * 添加销售计划
	 * @param saleInfo
	 * @return
	 * @throws Exception 
	 */
	/*@RequestMapping(value="/addSale",method=RequestMethod.POST,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String addSale(HttpServletRequest request,@RequestParam("saleList") String saleList) throws Exception{			
		logger.info("输入：  "+ "saleList="+saleList);
		TokenUtil.tokenOperate(request,userMapper);
		List<Sales> lists = JSON.parseArray(saleList, Sales.class);	
		int flag = salesService.addSales(lists);
		flag = salesService.updateLeftScale(lists);
		logger.info("输出：   "+JSON.toJSONString(FormatReturn.format(flag)));
		return JSON.toJSONString(FormatReturn.format(flag));
	}*/
	
	/**
	 * 修改销售计划
	 * @param saleInfo
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/updateSale",method=RequestMethod.POST,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String updateSale(HttpServletRequest request,@RequestParam("saleList") String saleList) throws Exception{
		logger.info("输入：  "+ "saleList="+saleList);
		TokenUtil.tokenOperate(request,userMapper);
		List<Sales> lists = JSON.parseArray(saleList, Sales.class);	
		int flag = salesService.updateSalesBySelective(lists);
		flag = salesService.updateLeftScale(lists);
		logger.info("输出：   "+JSON.toJSONString(FormatReturn.format(flag)));
		return JSON.toJSONString(FormatReturn.format(flag));
	}
	
	/**
	 * 删除销售计划
	 * @param salesId 销售计划Id
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteSale",method=RequestMethod.POST)
	@ResponseBody
	public String deleteSale(HttpServletRequest request,@RequestParam("salesId") int salesId) throws Exception{
		logger.info("输入：  "+ "salesId="+salesId);
		TokenUtil.tokenOperate(request,userMapper);
		int flag = salesService.deleteSales(salesId);
		logger.info("输出：   "+JSON.toJSONString(FormatReturn.format(flag)));
		return JSON.toJSONString(FormatReturn.format(flag));
	}
	
	/**
	 * 开放销售
	 * @param productId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/updateOpenSales",method=RequestMethod.POST,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String updateOpenSales(HttpServletRequest request,@RequestParam("saleList") String saleList) throws Exception{
		logger.info("输入：  "+ "saleList="+saleList);
		TokenUtil.tokenOperate(request,userMapper);
		List<Sales> lists = JSON.parseArray(saleList, Sales.class);	
		Products product = new Products();
		product.setStatus(1);
		product.setId(lists.get(0).getProductId());
		int flag = salesService.addSales(lists);  //新增所有的销售记录，如果存在，不新增（service层处理）
		flag = salesService.updateOpenSales(product);  //更新产品状态为“开放销售”
		flag = salesService.updateLeftScale(lists);  //更新剩余规模
		logger.info("输出：   "+JSON.toJSONString(FormatReturn.format(flag)));
		return JSON.toJSONString(FormatReturn.format(flag));
	}
	
}
