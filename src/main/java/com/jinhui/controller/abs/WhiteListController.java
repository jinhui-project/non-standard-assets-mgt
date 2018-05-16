package com.jinhui.controller.abs;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jinhui.controller.abs.vo.AccountsReceivableFileVo;
import com.jinhui.mapper.assets.PeWhiteListMapper;
import com.jinhui.model.PeWhiteList;
import com.jinhui.model.WebResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/abs/white")
public class WhiteListController {

    private static Logger logger = LoggerFactory.getLogger(WhiteListController.class);

    @Autowired
    private PeWhiteListMapper peWhiteListMapper;


    @RequestMapping(value = "/queryWhiteList", method = RequestMethod.GET)
    @ResponseBody
    public WebResult queryWhiteList(@RequestParam("pageNum") Integer pageNum,
                                    @RequestParam("pageSize") Integer pageSize,
                                    String debtor, String status, String period, String creator) {

        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;
        PageHelper.startPage(pageNum, pageSize);
        List<PeWhiteList> whiteLists = peWhiteListMapper.selectByQueryParam(debtor, status, period, creator);


        PageInfo<AccountsReceivableFileVo> pageInfo=new PageInfo(whiteLists);

        WebResult result = WebResult.ok();

        result.setData(pageInfo);

        return result;
    }


    @RequestMapping(value = "/queryStatistics", method = RequestMethod.GET)
    @ResponseBody
    public WebResult queryStatistics() {


        int count = peWhiteListMapper.selectCount();

        HashMap map = new HashMap();
        map.put("count",count);

        WebResult result = WebResult.ok();

        result.setData(map);

        return result;
    }

    @RequestMapping(value = "/queryAllWhiteName", method = RequestMethod.GET)
    @ResponseBody
    public WebResult queryWhiteName() {

        List<PeWhiteList> list = peWhiteListMapper.selectAll();

        ArrayList<String> nameList = new ArrayList();
        for (PeWhiteList white : list) {
            nameList.add(white.getReceivableDebtor());
        }

        WebResult result = WebResult.ok();
        result.setData(nameList);

        return result;

    }

    /**
     * 获取白名单内的不同的原始权益人列表
     * @return
     */
    @RequestMapping(value = "/queryOriginalHolders", method = RequestMethod.GET)
    @ResponseBody
    public WebResult queryOriginalHolders() {

        List<PeWhiteList> list = peWhiteListMapper.selectAll();
        List<String> originalHolders = list.stream().map(w -> w.getOriginalHolder()).distinct().collect(Collectors.toList());

        WebResult result = WebResult.ok();
        result.setData(originalHolders);

        return result;

    }

}
