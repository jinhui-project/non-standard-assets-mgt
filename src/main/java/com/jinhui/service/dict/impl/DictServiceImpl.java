package com.jinhui.service.dict.impl;

import com.jinhui.mapper.dict.DataDictionaryMapper;
import com.jinhui.model.DataDictionary;
import com.jinhui.service.dict.DictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 数据字典实现层
 *
 * @autor wsc
 * @create 2017-11-08 14:24
 **/
@Service("dictService")
public class DictServiceImpl implements DictService {

    @Resource
    private DataDictionaryMapper dataDictionaryMapper;

    @Override
    public DataDictionary queryDictByCode(String dictCode) {
        return dataDictionaryMapper.selectByKey(dictCode);
    }
}
