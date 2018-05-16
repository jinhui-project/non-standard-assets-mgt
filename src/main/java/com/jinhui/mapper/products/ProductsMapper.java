package com.jinhui.mapper.products;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jinhui.model.Products;;

/**
 * 产品DAO层
 * @author wsc
 *
 */
public interface ProductsMapper {
    int deleteByPrimaryKey(Integer id);

    //保存产品，“草稿”状态
    int saveProduct(Products record);
    
    //提交产品，“提交”状态
    int commitProduct(Products record);
    
    //发布产品
    int publishProduct(@Param("id")Integer id,@Param("editorId")Integer editorId);
    
    //撤回产品
    int withdrawProduct(@Param("id")Integer id,@Param("editorId")Integer editorId);
    
    //完结产品
    int finishProduct(@Param("id")Integer id,@Param("editorId")Integer editorId);

    Products selectByPrimaryKey(Integer id);
    
    int updateOpenSales(Products record);

    int updateByPrimaryKeySelective(Products record);

    /**
	 * 分页查询我的工作台--》产品列表(备案机构方)
	 * @param orgId 机构代码
	 * @return
	 */
    List<Products> getRecordProductList(@Param("orgId") String orgId);
    
    /**
	 * 分页查询我的工作台--》产品列表(资金方_我反馈的)
	 * @param orgId 机构代码
	 * @return
	 */
    List<Products> getFeedBackProductList(@Param("orgId") String orgId);
    
    /**
	 * 分页查询我的工作台--》产品列表(资金方_我销售的)
	 * @param orgId 机构代码
	 * @return
	 */
    List<Products> getSaleProductList(@Param("orgId") String orgId);
    
    /**
	 * 分页查询我的工作台--》产品列表(资产方)
	 * @param orgId 机构代码
	 * @return
	 */
    List<Products> getPropertyProductList(@Param("orgId") String orgId);
    
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
	 * @param status  产品状态
	 * @param fromTime  开始修改时间
	 * @param toTime  结束修改时间
	 * @return
	 */
    List<Products> queryProductListBySelect(@Param("productName")String productName,@Param("scale")String scale,@Param("leftScale")String leftScale,
    		@Param("beginAmount")String beginAmount,@Param("term")String term,@Param("fixedRatio")String fixedRatio,
    		@Param("status")String status,@Param("fromTime")String fromTime,@Param("toTime")String toTime);
    
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
     * 删除产品
     * @param id
     * @return
     */
    int deleteProduct(@Param("id")Integer id,@Param("editorId")Integer editorId);
    
    /**
     * 结束产品(销售结束)
     * @param id
     * @return
     */
    int overProduct(@Param("id")Integer id,@Param("editorId")Integer editorId);
    
    /**
     * 火爆销售
     * @param id
     * @return
     */
    int toHot(@Param("id")Integer id);
    
}