package com.jinhui.controller.abs;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jinhui.controller.abs.vo.AccountsReceivableFileVo;
import com.jinhui.controller.abs.vo.BackPaymentFileResult;
import com.jinhui.controller.abs.vo.BackPaymentFileVo;
import com.jinhui.controller.abs.vo.ReceivableFileResult;
import com.jinhui.mapper.abs.PeAccountsReceivableAllMapper;
import com.jinhui.mapper.abs.PeBackpaymentRecordAllMapper;
import com.jinhui.model.PeAccountsReceivableAll;
import com.jinhui.model.PeBackpaymentRecordAll;
import com.jinhui.model.WebResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2017/9/28 0028.
 */

@Controller
@RequestMapping("/abs")
public class BackPaymentController {

    private static Logger logger = LoggerFactory.getLogger(BackPaymentController.class);


    @Autowired
    private PeBackpaymentRecordAllMapper peBackpaymentRecordAllMapper;



    /**
     * 查询回款文件列表
     *
     * @return
     */
    @RequestMapping(value = "/queryPaymentFiles", method = RequestMethod.GET)
    @ResponseBody
    public WebResult queryFiles(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {

        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;
        PageHelper.startPage(pageNum, pageSize);
        List<BackPaymentFileVo> files = peBackpaymentRecordAllMapper.selectDistinctByFileName();


        for (BackPaymentFileVo file : files) {
            String fileId = file.getFileId();
            List<PeBackpaymentRecordAll> alls = peBackpaymentRecordAllMapper.selectByFileId(fileId);
            long count = alls.stream().filter(a -> a.getIsException().equals("1")).count();
            if(count>0){
                file.setState("有"+count+"处问题");
            }else{
                file.setState("成功");
            }
        }
        PageInfo<BackPaymentFileVo> pageInfo=new PageInfo(files);

        WebResult result = WebResult.ok();

        result.setData(pageInfo);

        return result;

    }



    @RequestMapping(value = "/queryPaymentFileByFileId", method = RequestMethod.GET)
    @ResponseBody
    public WebResult queryFiles(@RequestParam("fileId") String fileId,@RequestParam("fileName") String fileName){


        List<PeBackpaymentRecordAll> PaymentsAlls = peBackpaymentRecordAllMapper.selectByFileId(fileId);

        int totalCount=PaymentsAlls.size();
        long errorCount = PaymentsAlls.stream().filter(ra -> ra.getIsException().equals("1")).count();
        long matchCount = PaymentsAlls.stream().filter(ra -> ra.getIsException().equals("0")).count();

        BackPaymentFileResult fileResult = new BackPaymentFileResult();

        fileResult.setFileName(fileName);
        fileResult.setTotalCount(totalCount+"条");
        fileResult.setErrorCount(errorCount+"条");
        fileResult.setMatchCount(matchCount+"条");

        String matchRate = fileResult.calcMatchRate((int) matchCount, totalCount);
        fileResult.setMatchRate(matchRate);
        fileResult.setList(PaymentsAlls);
        WebResult ok = WebResult.ok();
        ok.setData(fileResult);





        return ok;


    }


}
