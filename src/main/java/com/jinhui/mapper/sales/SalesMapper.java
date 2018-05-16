package com.jinhui.mapper.sales;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jinhui.model.Sales;

public interface SalesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Sales record);

    int insertSelective(Sales record);

    Sales selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Sales record);

    int updateByPrimaryKey(Sales record);
    
    List<Sales> getSalesListByProductId(@Param("productId") Integer productId);
    
    /**
     * 更新产品剩余规模 
     * @param dealShares
     * @param productId
     * @return
     */
    int updateLeftScale(@Param("dealShares") BigDecimal dealShares,@Param("productId") Integer productId);
}