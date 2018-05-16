package com.jinhui.controller.abs;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jinhui.mapper.abs.PeTransferConfirmationMapper;
import com.jinhui.mapper.assets.PeFundChangeHistoryMapper;
import com.jinhui.model.PeFundChangeHistory;
import com.jinhui.model.PeTransferConfirmation;
import com.jinhui.model.WebResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * 划款详情确认函录入和查询
 * Created by luoyuanq on 2017/11/7 0007.
 */

@Controller
@RequestMapping("/abs")
public class TransferConfirmationController {

    private static Logger logger = LoggerFactory.getLogger(TransferConfirmationController.class);


    @Autowired
    private PeTransferConfirmationMapper peTransferConfirmationMapper;

    @Autowired
    private PeFundChangeHistoryMapper peFundChangeHistoryMapper;


    //条件查询文件列表
    @RequestMapping(value = "/queryConfirmFiles", method = RequestMethod.GET)
    @ResponseBody
    public WebResult queryFiles(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize,
                                @RequestParam("aid") Integer aid, String fileName, String createDateBegin, String createDateEnd) {

        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;
        PageHelper.startPage(pageNum, pageSize);

        List<PeTransferConfirmation> list = peTransferConfirmationMapper.selectByFileNameAndCreateDate(aid, fileName, createDateBegin, createDateEnd);


        PageInfo<PeTransferConfirmation> pageInfo = new PageInfo(list);

        WebResult result = WebResult.ok();
        result.setData(pageInfo);


        return result;
    }


    //新增
    @RequestMapping(value = "/saveConfirmFiles", method = RequestMethod.POST)
    @ResponseBody
    public WebResult saveConfirmFile(@RequestBody PeTransferConfirmation peTransferConfirmation) {


        peTransferConfirmation.setFileState("0");
        peTransferConfirmation.setUpdateDate(new Date());
        peTransferConfirmation.setCreateDate(new Date());
        peTransferConfirmationMapper.insert(peTransferConfirmation);


        return WebResult.ok();
    }


    //上传文件，更新状态为已确认
    @RequestMapping(value = "/uploadConfirmFile", method = RequestMethod.POST)
    @ResponseBody
    public WebResult uploadConfirmFile(@RequestParam("id") Integer id, @RequestParam("fileId") String fileId) {


        PeTransferConfirmation transferConfirmation = peTransferConfirmationMapper.selectByPrimaryKey(id);

        //在“资产包的资金变更历史表”下插入一条新的记录
        PeFundChangeHistory fundChangeHistory = new PeFundChangeHistory();
        fundChangeHistory.setAid(transferConfirmation.getAid());
        fundChangeHistory.setAccountName(transferConfirmation.getPayerName());
        fundChangeHistory.setAccountNo(transferConfirmation.getPayerAccount());
        fundChangeHistory.setCreateName(transferConfirmation.getCreator());
        fundChangeHistory.setCreateTime(LocalDate.now().toString());
        fundChangeHistory.setIsEnabled("0");

        fundChangeHistory.setChangeType("2"); //记录操作类别为划款

        //资产变动：出款金额=划款金额，余额=资产包余额+划款金额
        fundChangeHistory.setPaytoAmount(transferConfirmation.getTransferAmount());
        BigDecimal balanceAmount;
        PeFundChangeHistory newsChangeHistory = peFundChangeHistoryMapper.selectNewsByAid(transferConfirmation.getAid());
        if (newsChangeHistory != null) {
            balanceAmount = transferConfirmation.getTransferAmount().add(newsChangeHistory.getBalanceAmount());
        } else {
            balanceAmount = transferConfirmation.getTransferAmount();
        }
        fundChangeHistory.setBalanceAmount(balanceAmount);

        peFundChangeHistoryMapper.insertSelective(fundChangeHistory);


        //获取到刚保存的资金id
        PeFundChangeHistory newsChangeHistory2 = peFundChangeHistoryMapper.selectNewsByAid(transferConfirmation.getAid());

        //更新状态和关联的资金id
        PeTransferConfirmation peTransferConfirmation = new PeTransferConfirmation();
        peTransferConfirmation.setId(id);
        peTransferConfirmation.setUploadFileId(fileId);
        peTransferConfirmation.setFundId(newsChangeHistory2.getId());
        peTransferConfirmation.setFileState("1");//已确认

        peTransferConfirmationMapper.updateByPrimaryKeySelective(peTransferConfirmation);

        return WebResult.ok();
    }


    //更新文件状态为失效
    @RequestMapping(value = "/updateConfirmFileState", method = RequestMethod.POST)
    @ResponseBody
    public WebResult updateFileState(@RequestParam("id") Integer id, @RequestParam("state") String state) {

        if (!state.equals("2")) {
            return WebResult.error("参数错误");
        }


        //找到"资产包的资金变更历史表"对应的那条资金数据
        PeTransferConfirmation peTransferConfirmation = peTransferConfirmationMapper.selectByPrimaryKey(id);


        if (peTransferConfirmation.getFileState().equals("1")) {
            ///如果文件状态是已确认的情况下，新插入一条新的资金变更记录
            PeFundChangeHistory fundChangeHistory = new PeFundChangeHistory();
            fundChangeHistory.setAid(peTransferConfirmation.getAid());
            fundChangeHistory.setAccountName(peTransferConfirmation.getPayerName());
            fundChangeHistory.setAccountNo(peTransferConfirmation.getPayerAccount());
            fundChangeHistory.setCreateName(peTransferConfirmation.getCreator());
            fundChangeHistory.setCreateTime(LocalDate.now().toString());
            fundChangeHistory.setIsEnabled("0");
            fundChangeHistory.setPaybackAmount(peTransferConfirmation.getTransferAmount());
            BigDecimal balanceAmount;

            fundChangeHistory.setChangeType("3");  //记录操作类别为划款失效

            //资产变动：回款金额=失效金额，余额=资产包余额-失效金额
            fundChangeHistory.setPaybackAmount(peTransferConfirmation.getTransferAmount());
            PeFundChangeHistory newsChangeHistory = peFundChangeHistoryMapper.selectNewsByAid(peTransferConfirmation.getAid());
            if (newsChangeHistory != null) {
                balanceAmount = newsChangeHistory.getBalanceAmount().subtract(peTransferConfirmation.getTransferAmount());
            } else {
                balanceAmount = peTransferConfirmation.getTransferAmount();
            }
            fundChangeHistory.setBalanceAmount(balanceAmount);

            peFundChangeHistoryMapper.insertSelective(fundChangeHistory);

        }

        //划款详情状态改为失效
        peTransferConfirmation.setFileState("2");
        peTransferConfirmationMapper.updateByPrimaryKeySelective(peTransferConfirmation);


        return WebResult.ok();
    }


}
