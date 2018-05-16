package com.jinhui.mapper.dict;

import com.jinhui.model.DataDictionary;
import org.apache.ibatis.annotations.Param;

public interface DataDictionaryMapper {

    int insertSelective(DataDictionary record);

    DataDictionary selectByKey(@Param("dictCode") String dictCode);

}