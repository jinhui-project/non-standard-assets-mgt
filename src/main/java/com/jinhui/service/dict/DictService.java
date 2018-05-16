package com.jinhui.service.dict;

import com.jinhui.model.DataDictionary;

/**
 * 数据字典服务层
 *
 * @autor wsc
 * @create 2017-11-08 14:22
 **/
public interface DictService {

    /**
     * 根据字典代码查询数据字典
     * @param dictCode
     * @return
     */
    DataDictionary queryDictByCode(String dictCode);
}
