package com.jinhui.controller.abs.vo;

import com.jinhui.model.PeAccountsReceivableAll;
import com.jinhui.model.PeBackpaymentRecordAll;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Administrator on 2017/10/19 0019.
 */
public class BackPaymentFileResult {

    private String fileName;

    private String totalCount;

    private String matchCount;

    private String errorCount;

    private String matchRate;

    private List<PeBackpaymentRecordAll> list;


    /**
     * 计算匹配率
     * @param matchNum
     * @param totalNum
     * @return
     */
    public String calcMatchRate(int matchNum, int totalNum) {
        DecimalFormat df = new DecimalFormat("0.00");
        if(matchNum==0||totalNum==0){
            return "0%";
        }
        double rate=(double)matchNum/(double)totalNum *100;

        return df.format(rate)+"%";


    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(String matchCount) {
        this.matchCount = matchCount;
    }

    public String getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(String errorCount) {
        this.errorCount = errorCount;
    }

    public String getMatchRate() {
        return matchRate;
    }

    public void setMatchRate(String matchRate) {
        this.matchRate = matchRate;
    }

    public List<PeBackpaymentRecordAll> getList() {
        return list;
    }

    public void setList(List<PeBackpaymentRecordAll> list) {
        this.list = list;
    }


    @Override
    public String toString() {
        return "BackPaymentFileResult{" +
                "fileName='" + fileName + '\'' +
                ", totalCount='" + totalCount + '\'' +
                ", matchCount='" + matchCount + '\'' +
                ", errorCount='" + errorCount + '\'' +
                ", matchRate='" + matchRate + '\'' +
                ", list=" + list +
                '}';
    }
}
