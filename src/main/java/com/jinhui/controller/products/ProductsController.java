package com.jinhui.controller.products;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageInfo;
import com.jinhui.mapper.assets.AssetsMapper;
import com.jinhui.mapper.user.UserMapper;
import com.jinhui.model.Products;
import com.jinhui.model.Sales;
import com.jinhui.service.products.ProductsService;
import com.jinhui.service.sales.SalesService;
import com.jinhui.util.FormatReturn;
import com.jinhui.util.TokenUtil;

@Controller
@RequestMapping("/products")
public class ProductsController {
	private static Logger logger = LoggerFactory.getLogger(ProductsController.class);

	@Autowired
	private ProductsService productsService;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private SalesService salesService;
	
	@Autowired
	private AssetsMapper assetsMapper;
	
	/**
	 * 查询单个产品详情
	 * @param productId
	 * @return
	 */
	@RequestMapping(value="/queryProductInfo",method=RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String queryProductInfo(@RequestParam("productId") int productId){
		logger.info("输入：  "+ "productId="+productId);
		Products products = productsService.getProductsById(productId);
		logger.info("输出：   "+JSON.toJSONString(products));
		return JSON.toJSONString(products,SerializerFeature.WriteMapNullValue);
	}
	
	 /**
		 * 产品管理列表
		 * @param pageNum
		 * @param pageSize
		 * @param productName  产品名称
		 * @param scale  产品规模
		 * @param leftScale  剩余规模
		 * @param beginAmount  起投金额
		 * @param term  产品期限
		 * @param fixedRatio  预期收益率
		 * @param fromTime  开始修改时间
	     * @param toTime  结束修改时间
		 * @return
		 */
	@RequestMapping(value="/queryProductListBySelect",method=RequestMethod.POST,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String queryAllProductList(@RequestParam("pageNum") int pageNum,@RequestParam("pageSize") int pageSize,
			@RequestParam("productName") String productName,@RequestParam("scale") String scale,@RequestParam("leftScale") String leftScale,
			@RequestParam("beginAmount") String beginAmount,@RequestParam("term") String term,@RequestParam("fixedRatio") String fixedRatio,
			@RequestParam("status") String status,@RequestParam("fromTime") String fromTime,@RequestParam("toTime") String toTime){
		logger.info("输入：  "+ "pageNum="+pageNum+"  pageSize="+pageSize+" productName="+productName+" scale="+scale+
				" leftScale="+leftScale+"  beginAmount="+beginAmount+"  term="+term+"  fixedRatio="+fixedRatio+"  status="+status
				+"  fromTime="+fromTime+"  toTime="+toTime);
		PageInfo<Products> productsList = productsService.queryProductListBySelect(pageNum,pageSize,productName,scale,leftScale,beginAmount,term,fixedRatio,status,fromTime,toTime);
		logger.info("输出：   "+JSON.toJSONString(productsList));
		return JSON.toJSONString(productsList,SerializerFeature.WriteMapNullValue);
	}
	
	/**
	 * 保存产品
	 * @param productInfo
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/saveProduct",method=RequestMethod.POST,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String saveProduct(HttpServletRequest request,Products productInfo) throws Exception{
		logger.info("输入：  "+ "productInfo="+productInfo);
		TokenUtil.tokenOperate(request,userMapper);
		int flag = 0;
		if(StringUtils.isEmpty(productInfo.getId())){  //如果没传产品id，说明是直接提交新增产品
	    	flag = productsService.saveProduct(productInfo);
	    }else if(!StringUtils.isEmpty(productInfo.getId())){      //如果传了产品id，说明编辑产品信息
	    	flag = productsService.updateProductBySelective(productInfo);
	    }
		logger.info("输出：   "+JSON.toJSONString(FormatReturn.format(flag)));
		return JSON.toJSONString(FormatReturn.format(flag));
	}
	
	/**
	 * 提交产品
	 * @param productInfo
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/commitProduct",method=RequestMethod.POST,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String commitProduct(HttpServletRequest request,Products productInfo) throws Exception{
		logger.info("输入：  "+ "productInfo="+productInfo);
		TokenUtil.tokenOperate(request,userMapper);
		int flag = 0;
	    if(StringUtils.isEmpty(productInfo.getId())){  //如果没传产品id，说明是直接提交新增产品
	    	flag = productsService.commitProduct(productInfo);
	    }else if(!StringUtils.isEmpty(productInfo.getId())){      //如果传了产品id，说明是由“草稿”到“提交”，则更新产品信息和状态
	    	productInfo.setStatus(6);    //更新产品状态为“提交”
	    	flag = productsService.updateProductBySelective(productInfo);
	    }
		logger.info("输出：   "+JSON.toJSONString(FormatReturn.format(flag)));
		return JSON.toJSONString(FormatReturn.format(flag));
	}
	
	/**
	 * 发布或撤回产品
	 * @param productInfo
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/publishOrWithdrawProduct",method=RequestMethod.POST,produces={"text/html;charset=UTF-8;","application/json;charset=UTF-8;"})
	@ResponseBody
	public String publishOrWithdrawProduct(HttpServletRequest request,@RequestParam("productId") int productId,@RequestParam("type") int type,@RequestParam("editorId") int editorId) throws Exception{
		logger.info("输入：  "+ "productId= "+productId +" type= "+type);
		TokenUtil.tokenOperate(request,userMapper);
		int flag = 0;
		if(type == 1){  //发布
			flag = productsService.publishProduct(productId,editorId);
			Products productInfo = productsService.getProductsById(productId);
			if(flag == 1 && !StringUtils.isEmpty(productInfo.getAssetId())){  //如果发布产品成功且关联了资产，则将改资产状态改为5“形成产品”状态
				assetsMapper.updateToProductStatusByAssetsId(productInfo.getAssetId());
			}
		}else if(type == 2){  //撤回
			flag = productsService.withdrawProduct(productId,editorId);
		}
		logger.info("输出：   "+JSON.toJSONString(FormatReturn.format(flag)));
		return JSON.toJSONString(FormatReturn.format(flag));
	}
	
	/**
	 * 完结 产品
	 * @param productId 产品Id
	 * * @param editorId 编辑人Id
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/finishProduct",method=RequestMethod.POST)
	@ResponseBody
	public String finishProduct(HttpServletRequest request,@RequestParam("productId") int productId,@RequestParam("editorId") int editorId) throws Exception{
		logger.info("输入：  "+ "  productId: "+ productId+ "  editorId: "+ editorId);
		TokenUtil.tokenOperate(request,userMapper);
		int flag = productsService.finishProduct(productId, editorId);
		logger.info("输出：   "+JSON.toJSONString(FormatReturn.format(flag)));
		return JSON.toJSONString(FormatReturn.format(flag));
	}
	
	/**
	 * 修改产品
	 * @param productInfo
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/updateProduct",method=RequestMethod.POST)
	@ResponseBody
	public String updateProduct(HttpServletRequest request,Products productInfo) throws Exception{
		logger.info("输入：  "+ "productInfo="+productInfo);
		TokenUtil.tokenOperate(request,userMapper);
		int flag = productsService.updateProductBySelective(productInfo);
		logger.info("输出：   "+JSON.toJSONString(FormatReturn.format(flag)));
		return JSON.toJSONString(FormatReturn.format(flag));
	}
	
	/**
	 * 删除 产品
	 * @param productId 产品Id
	 * * @param editorId 编辑人Id
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteProduct",method=RequestMethod.POST)
	@ResponseBody
	public String updateProduct(HttpServletRequest request,@RequestParam("productId") int productId,@RequestParam("editorId") int editorId) throws Exception{
		logger.info("输入：  "+ "  productId: "+ productId+ "  editorId: "+ editorId);
		TokenUtil.tokenOperate(request,userMapper);
		int flag = productsService.deleteProduct(productId,editorId);
		logger.info("输出：   "+JSON.toJSONString(FormatReturn.format(flag)));
		return JSON.toJSONString(FormatReturn.format(flag));
	}
	
	/**
	 * 结束产品(销售结束)
	 * @param productId 产品Id
	 * @param editorId 编辑人Id
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/overProduct",method=RequestMethod.POST)
	@ResponseBody
	public String overProduct(HttpServletRequest request,@RequestParam("saleList") String saleList,@RequestParam("editorId") int editorId) throws Exception{
		logger.info("输入：  "+ "  saleList: "+ saleList+ "  editorId: "+ editorId);
		TokenUtil.tokenOperate(request,userMapper);
		List<Sales> lists = JSON.parseArray(saleList, Sales.class);	
		int flag = salesService.addSales(lists);  //新增所有的销售记录，如果存在，不新增（service层处理）
		flag = productsService.overProduct(lists.get(0).getProductId(),editorId);
		logger.info("输出：   "+JSON.toJSONString(FormatReturn.format(flag)));
		return JSON.toJSONString(FormatReturn.format(flag));
	}
	
	/**
	 * 火爆销售
	 * @param request
	 * @param id
	 * @param type  1 资产 2 产品
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/toHot",method=RequestMethod.POST)
	@ResponseBody
	public String toHot(HttpServletRequest request,@RequestParam("id") int id,@RequestParam("type") int type) throws Exception{
		logger.info("输入：  "+ "  id: "+ id+ "  type: "+ type);
		TokenUtil.tokenOperate(request,userMapper);
		int flag = productsService.toHot(id, type);
		logger.info("输出：   "+JSON.toJSONString(FormatReturn.format(flag)));
		return JSON.toJSONString(FormatReturn.format(flag));
	}
	
}
