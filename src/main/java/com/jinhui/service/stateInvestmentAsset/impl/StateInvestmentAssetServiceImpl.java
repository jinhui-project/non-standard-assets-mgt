package com.jinhui.service.stateInvestmentAsset.impl;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jinhui.constant.ConstantSA;
import com.jinhui.mapper.assets.StateInvestmentAssetMapper;
import com.jinhui.model.StateInvestmentAsset;
import com.jinhui.service.stateInvestmentAsset.StateInvestmentAssetService;

@Service
public class StateInvestmentAssetServiceImpl implements StateInvestmentAssetService {
	Logger logger = LoggerFactory.getLogger(StateInvestmentAssetServiceImpl.class);
	@Autowired
	private StateInvestmentAssetMapper stateInvestmentAssetMapper;

	
	public String queryByIdScore(Integer id){
		StateInvestmentAsset s = stateInvestmentAssetMapper.selectByPrimaryKey(id);
		BigDecimal score =  new BigDecimal(0);
		if( null != s){
//			 人大决议：govDecision
//			 同意融资批复：govAgreement
//			 财政安排函：financeArrangement
			//财政总收入
			score = govTotalIncome(s)
	        .add(generalIncome(s)
			.add(gdp(s)
			.add(totalAsset(s)
			.add(mainIncome(s)
		    .add(regionalStatus()
    		.add(admGrade(s)
			.add(liabilitiesRatio(s)
			.add(netProfit(s)
			.add(alRatio(s)
			.add(netProfitNetAsset(s)
			.add(pledgeRatio(s)
			.add(scaleLandValue(s)
			.add(scaleReceivable(s)
			.add(govDecision(s)
			.add(govAgreement(s)
			.add(financeArrangement(s)))))))))))))))));
			logger.info("【财政总收入】："+govTotalIncome(s)+",【一般预算收入】"+generalIncome(s)+",【GDP】"+gdp(s)+
					",【总资产】"+totalAsset(s)+",【营业收入】"+mainIncome(s)+",【区域地位】"+regionalStatus()+",【省市区 ，行政级别】"+admGrade(s)+
					",【政府负债率】"+liabilitiesRatio(s)+",【净利润】"+netProfit(s)+",【资产负债率】"+alRatio(s)
					+",【净资产收益率 】"+netProfitNetAsset(s)+""+",【股权抵押率】"+pledgeRatio(s)
					+",【土地质押率】"+scaleLandValue(s)+""+",【应收账款质押率 】"+scaleReceivable(s)
					+",【 人大决议】"+govDecision(s)+",【 同意融资批复】"+govAgreement(s)+",【 财政安排函】"+financeArrangement(s)+",【评分总和值为】："+score);
		}
		return score.toString() ;
	}
	
	/**
	 * 财政总收入
	 * @return
	 */
	public  BigDecimal govTotalIncome(StateInvestmentAsset sa){
		logger.info("【财政总收入 运算】   开始----值为："+sa.getGovTotalIncome());
		BigDecimal govTotalIncome = new BigDecimal(0);
		if( null != sa.getGovTotalIncome() ){
			//-1 小于  ,0 等于,1 大于
			if( ckCompareTo(sa.getGovTotalIncome(),0) == -1){
				return govTotalIncome;
			}
			//大于等于0小于10亿的判断
			else if( ckCompareTo(sa.getGovTotalIncome(),0) == 1 && ckCompareTo(sa.getGovTotalIncome(),ConstantSA.lv1) == -1 ){
				logger.info("【财政总收入 运算】大于0小于十亿的判断 BigDecimal govTotalIncome = new BigDecimal() 的初始值为 ："+govTotalIncome);
				logger.info("【财政总收入 运算】进入大于0小于十亿的判断     权重0.15    * govTotalIncome参数的  2分之5 ");
				
				govTotalIncome =  sa.getGovTotalIncome().multiply( new BigDecimal(5.0) ).
						divide( new BigDecimal(2.0),2,BigDecimal.ROUND_HALF_UP ).
						divide(new BigDecimal(ConstantSA.bigDecimal),2,BigDecimal.ROUND_HALF_UP).
						multiply(new BigDecimal(ConstantSA.gov_total_income_weight)).setScale(2,BigDecimal.ROUND_HALF_UP);
				logger.info("【财政总收入 运算】进入大于0小于十亿的判断     权重 0.15   * govTotalIncome参数的  2分之5的  【结果】值为："+govTotalIncome);
				return govTotalIncome;
			}
			//大于等于10亿小于40亿的判断
			else if( ( ckCompareTo(sa.getGovTotalIncome(),ConstantSA.lv1) == 0 || ckCompareTo(sa.getGovTotalIncome(),ConstantSA.lv1) == 1 ) && ckCompareTo(sa.getGovTotalIncome(),ConstantSA.lv4) == -1){
				logger.info("【财政总收入 运算】大于10亿小于40亿的判断 BigDecimal govTotalIncome = new BigDecimal() 的初始值为 ："+govTotalIncome);
				logger.info("【财政总收入 运算】进入大于10亿小于40亿的判断     权重 0.15   * govTotalIncome参数的 6分之5 + 3分之50 ");
				govTotalIncome =BD(sa.getGovTotalIncome())
				 .multiply(new BigDecimal(5.0).setScale(2,BigDecimal.ROUND_HALF_UP)
				 .divide(new BigDecimal(6.0) ,2,BigDecimal.ROUND_HALF_UP))
				 .add(new BigDecimal(50.0)
				 .divide(new BigDecimal(3.0) ,2,BigDecimal.ROUND_HALF_UP))
				 .multiply(new BigDecimal(ConstantSA.gov_total_income_weight)).setScale(2,BigDecimal.ROUND_HALF_UP);
				logger.info("【财政总收入 运算】进入大于10亿小于40亿的判断     权重 0.15   * govTotalIncome参数的 6分之5 + 3分之50 【结果】值为："+govTotalIncome);
				return govTotalIncome;
			}
			//大于等于40亿小于80亿的判断
			else if( ( ckCompareTo(sa.getGovTotalIncome(),ConstantSA.lv4) == 0 || ckCompareTo(sa.getGovTotalIncome(),ConstantSA.lv4) == 1 ) && ckCompareTo(sa.getGovTotalIncome(),ConstantSA.lv8) == -1){
				logger.info("【财政总收入 运算】大于等于40亿小于80亿的判断 BigDecimal govTotalIncome = new BigDecimal() 的初始值为 ："+govTotalIncome);
				logger.info("【财政总收入 运算】进入大于等于40亿小于80亿的判断     权重0.15    * govTotalIncome参数的 8分之5 + 25 ");
				govTotalIncome = BD(sa.getGovTotalIncome())
						.multiply(new BigDecimal(5.0).setScale(2,BigDecimal.ROUND_HALF_UP)
						.divide(new BigDecimal(8.0) ,2,BigDecimal.ROUND_HALF_UP)) 
						.add(new BigDecimal(25.0))
						.multiply(new BigDecimal(ConstantSA.gov_total_income_weight)).setScale(2,BigDecimal.ROUND_HALF_UP);
				logger.info("【财政总收入 运算】进入大于等于40亿小于80亿的判断     权重 0.15   * govTotalIncome参数的  8分之5 + 25  【结果】值为："+govTotalIncome);
				return govTotalIncome;
			}
			
			//大于等于80亿小于300亿的判断
			else if( ( ckCompareTo(sa.getGovTotalIncome(),ConstantSA.lv8) == 0 || ckCompareTo(sa.getGovTotalIncome(),ConstantSA.lv8) == 1 ) && ckCompareTo(sa.getGovTotalIncome(),ConstantSA.lv30) == -1){
				logger.info("【财政总收入 运算】大于等于80亿小于300亿的判断 BigDecimal govTotalIncome = new BigDecimal() 的初始值为 ："+govTotalIncome);
				logger.info("【财政总收入 运算】进入大于等于80亿小于300亿的判断     权重 0.15   * govTotalIncome参数的 44分之5 + 11分之725 ");
				govTotalIncome = BD(sa.getGovTotalIncome())
						.multiply(new BigDecimal(5.0)).setScale(2,BigDecimal.ROUND_HALF_UP)
						.divide(new BigDecimal(44.0) ,2,BigDecimal.ROUND_HALF_UP)
						.add(new BigDecimal(725.0).divide(new BigDecimal(11.0),2,BigDecimal.ROUND_HALF_UP))
						.multiply(new BigDecimal(ConstantSA.gov_total_income_weight)).setScale(2,BigDecimal.ROUND_HALF_UP);
				logger.info("【财政总收入 运算】进入大于等于80亿小于300亿的判断     权重 0.15   * govTotalIncome参数的  44分之5 + 11分之725  【结果】值为："+govTotalIncome);
				return govTotalIncome;
			}
			
			//大于等于300亿的判断
			else if( ckCompareTo(sa.getGovTotalIncome(),ConstantSA.lv30) == 0 || ckCompareTo(sa.getGovTotalIncome(),ConstantSA.lv30) == 1){
				logger.info("【财政总收入 运算】大于等于300亿的判断 BigDecimal govTotalIncome = new BigDecimal() 的初始值为 ："+govTotalIncome);
				logger.info("【财政总收入 运算】进入大于等于300亿的判断     权重0.15    * govTotalIncome * 100 ");
				govTotalIncome =  new BigDecimal(ConstantSA.gov_total_income_weight*100);
				logger.info("【财政总收入 运算】进入大于等于300亿的判断     权重 0.15   * govTotalIncome * 100   【结果】值为："+govTotalIncome);
				return govTotalIncome;
			}
			
		}
		logger.info("【财政总收入 运算】   结束");
		return govTotalIncome;
	}
	/**
	 * 一般预算收入
	 * @param sa
	 * @return
	 */
	public  BigDecimal generalIncome (StateInvestmentAsset sa){
		logger.info("【一般预算收入 运算】   开始----值为："+sa.getGeneralIncome());
		BigDecimal generalIncome = new BigDecimal(0);
		if(null != sa.getGeneralIncome()){
			if( ckCompareTo(sa.getGeneralIncome(),0) == -1){
				return generalIncome;
			}
			//大于等于0小于5亿的判断
			if( ckCompareTo(sa.getGeneralIncome(),0) == 1 && ckCompareTo(sa.getGeneralIncome(),ConstantSA.hundred_million_5) == -1 ){
				logger.info("【一般预算收入  运算】大于等于0小于5亿的判断 BigDecimal generalIncome = new BigDecimal() 的初始值为 ："+generalIncome);
				logger.info("【一般预算收入  运算】进入大于等于0小于5亿的判断             权重 0.2*(参数*5)   ");
				generalIncome = BD(sa.getGeneralIncome()).multiply(new BigDecimal(5.0)).setScale(2,BigDecimal.ROUND_HALF_UP)
						.multiply(new BigDecimal(ConstantSA.general_income_weight)).setScale(2,BigDecimal.ROUND_HALF_UP);
				logger.info("【一般预算收入  运算】大于等于0小于5亿的判断       权重 0.2*(参数*5)     【结果】值为："+generalIncome);
				return generalIncome;
			}
			//大于等于5小于20亿的判断
			else if(( ckCompareTo(sa.getGeneralIncome(),ConstantSA.hundred_million_5) == 0 || ckCompareTo(sa.getGeneralIncome(),ConstantSA.hundred_million_5) == 1 ) && ckCompareTo(sa.getGeneralIncome(),ConstantSA.hundred_million_20) == -1){
				logger.info("【一般预算收入  运算】大于等于5小于20亿的判断 BigDecimal generalIncome = new BigDecimal() 的初始值为 ："+generalIncome);
				logger.info("【一般预算收入  运算】进入大于等于5小于20亿的判断        权重 0.2*( (参数*5/3) + (50/3) )   ");
				generalIncome =BD(sa.getGeneralIncome())
						.multiply(new BigDecimal(5.0)).setScale(2,BigDecimal.ROUND_HALF_UP)
						.divide(new BigDecimal(3.0),2,BigDecimal.ROUND_HALF_UP)
						.add(new BigDecimal(50.0)
						.divide(new BigDecimal(3.0),2,BigDecimal.ROUND_HALF_UP))
						.multiply(new BigDecimal(ConstantSA.general_income_weight)).setScale(2,BigDecimal.ROUND_HALF_UP);
				logger.info("【一般预算收入  运算】大于等于5小于20亿的判断   权重 0.2*( (参数*5/3) + (50/3) )  【结果】值为："+generalIncome);
				return generalIncome;
			}
			//大于等于20小于40亿的判断
			else if(( ckCompareTo(sa.getGeneralIncome(),ConstantSA.hundred_million_20) == 0 || ckCompareTo(sa.getGeneralIncome(),ConstantSA.hundred_million_20) == 1 ) && ckCompareTo(sa.getGeneralIncome(),ConstantSA.hundred_million_40) == -1){
				logger.info("【一般预算收入  运算】大于等于20小于40亿的判断 BigDecimal generalIncome = new BigDecimal() 的初始值为 ："+generalIncome);
				logger.info("【一般预算收入  运算】进入大于等于20小于40亿的判断     权重 0.2*( (参数*5/4) + 25 )   ");
				generalIncome =BD(sa.getGeneralIncome())
						.multiply(new BigDecimal(5.0)).setScale(2,BigDecimal.ROUND_HALF_UP)
						.divide(new BigDecimal(4.0),2,BigDecimal.ROUND_HALF_UP)
						.add(new BigDecimal(25.0))
						.multiply(new BigDecimal(ConstantSA.general_income_weight)).setScale(2,BigDecimal.ROUND_HALF_UP);
				logger.info("【一般预算收入  运算】大于等于20小于40亿的判断    权重 0.2*( (参数*5/4) + 25 )  【结果】值为："+generalIncome);
				return generalIncome;
			}
			//大于等于40小于100亿的判断
			else if(( ckCompareTo(sa.getGeneralIncome(),ConstantSA.hundred_million_40) == 0 || ckCompareTo(sa.getGeneralIncome(),ConstantSA.hundred_million_40) == 1 ) && ckCompareTo(sa.getGeneralIncome(),ConstantSA.hundred_million_100) == -1){
				logger.info("【一般预算收入  运算】大于等于40小于100亿的判断 BigDecimal generalIncome = new BigDecimal() 的初始值为 ："+generalIncome);
				logger.info("【一般预算收入  运算】进入大于等于40小于100亿的判断   权重 0.2*( (参数*5/12) + (175/3) )  ");
				generalIncome =BD(sa.getGeneralIncome())
						.multiply(new BigDecimal(5.0)).setScale(2,BigDecimal.ROUND_HALF_UP)
						.divide(new BigDecimal(12.0),2,BigDecimal.ROUND_HALF_UP)
						.add(new BigDecimal(175.0)
						.divide(new BigDecimal(3.0) ,2,BigDecimal.ROUND_HALF_UP))
						.multiply(new BigDecimal(ConstantSA.general_income_weight)).setScale(2,BigDecimal.ROUND_HALF_UP);
				logger.info("【一般预算收入  运算】大于等于40小于100亿的判断    权重 0.2*( (参数*5/12) + (175/3) )  【结果】值为："+generalIncome);
				return generalIncome;
			}
			//大于等于100亿的判断
			else if(ckCompareTo(sa.getGeneralIncome(),ConstantSA.hundred_million_100) == 0 || ckCompareTo(sa.getGeneralIncome(),ConstantSA.hundred_million_100) == 1){
				logger.info("【一般预算收入  运算】大于等于100亿的判断 BigDecimal generalIncome = new BigDecimal() 的初始值为 ："+generalIncome);
				logger.info("【一般预算收入  运算】进入大于等于100亿的判断     ");
				generalIncome =new BigDecimal(ConstantSA.general_income_weight*100);
				logger.info("【一般预算收入  运算】大于等于100亿的判断    【结果】值为："+generalIncome);
				return generalIncome;
			}
		}
		logger.info("【一般预算收入  运算】   结束");
		return generalIncome;
		
	}
	
	/**
	 * GDP
	 * @param sa
	 * @return
	 */
	public  BigDecimal gdp (StateInvestmentAsset sa){
		logger.info("【GDP 运算】   开始----值为："+sa.getGdp());
		BigDecimal gdp = new BigDecimal(0);
		if(null != sa.getGdp()){
			//大于等于0小于50亿的判断
			if( ckCompareTo(sa.getGdp(),0) == 1 && ckCompareTo(sa.getGdp(),ConstantSA.hundred_million_50) == -1 ){
				logger.info("【GDP  运算】大于等于0小于50亿的判断 BigDecimal gdp = new BigDecimal() 的初始值为 ："+gdp);
				logger.info("【GDP  运算】进入大于等于0小于50亿的判断   权重   0.07*(参数*1/2)  ");
				gdp = BD(sa.getGdp())
						.multiply(new BigDecimal(1.0).setScale(2,BigDecimal.ROUND_HALF_UP))
						.divide(new BigDecimal(2.0),2,BigDecimal.ROUND_HALF_UP)
						.multiply(new BigDecimal(ConstantSA.gdp_weight)).setScale(2,BigDecimal.ROUND_HALF_UP);
				logger.info("【GDP  运算】大于等于0小于50亿的判断      权重   0.07*(参数*1/2)  【结果】值为："+gdp);
				return gdp;
			}
			//大于等于50小于200亿的判断
			else if(( ckCompareTo(sa.getGdp(),ConstantSA.hundred_million_50) == 0 || ckCompareTo(sa.getGdp(),ConstantSA.hundred_million_50) == 1 ) && ckCompareTo(sa.getGdp(),ConstantSA.hundred_million_200) == -1){
				logger.info("【GDP  运算】大于等于50小于200亿的判断  BigDecimal gdp = new BigDecimal() 的初始值为 ："+gdp);
				logger.info("【GDP  运算】大于等于50小于200亿的判断   权重   0.07 * ((参数*1/6) + (50/3)) ");
				gdp = BD(sa.getGdp())
						.multiply(new BigDecimal(1.0).setScale(2,BigDecimal.ROUND_HALF_UP))
						.divide(new BigDecimal(6.0),2,BigDecimal.ROUND_HALF_UP)
						.add(new BigDecimal( 50.0)
						.divide(new BigDecimal(3.0),2,BigDecimal.ROUND_HALF_UP))
						.multiply(new BigDecimal(ConstantSA.gdp_weight)).setScale(2,BigDecimal.ROUND_HALF_UP);
				logger.info("【GDP  运算】大于等于50小于200亿的判断   权重  0.07 * ((参数*1/6) + (50/3))   【结果】值为："+gdp);
				return gdp;
			}
			//大于等于200小于800亿的判断
			else if(( ckCompareTo(sa.getGdp(),ConstantSA.hundred_million_200) == 0 || ckCompareTo(sa.getGdp(),ConstantSA.hundred_million_200) == 1 ) && ckCompareTo(sa.getGdp(),ConstantSA.hundred_million_800) == -1){
				logger.info("【GDP  运算】大于等于200小于800亿的判断  BigDecimal gdp = new BigDecimal() 的初始值为 ："+gdp);
				logger.info("【GDP  运算】进入大于等于200小于800亿的判断   权重       0.07 * ((参数*1/24) + (125/3)) ");
				gdp = BD(sa.getGdp())
						.multiply(new BigDecimal(1.0).setScale(2,BigDecimal.ROUND_HALF_UP))
						.divide(new BigDecimal(24.0),2,BigDecimal.ROUND_HALF_UP)
						.add(new BigDecimal( 125.0)
						.divide(new BigDecimal(3.0),2,BigDecimal.ROUND_HALF_UP))
						.multiply(new BigDecimal(ConstantSA.gdp_weight)).setScale(2,BigDecimal.ROUND_HALF_UP);
				logger.info("【GDP  运算】大于等于200小于800亿的判断    权重    0.07 * ((参数*1/24) + (125/3)) 【结果】值为："+gdp);
				return gdp;
			}
			//大于等于800小于5000亿的判断
			else if(( ckCompareTo(sa.getGdp(),ConstantSA.hundred_million_800) == 0 || ckCompareTo(sa.getGdp(),ConstantSA.hundred_million_800) == 1 ) && ckCompareTo(sa.getGdp(),ConstantSA.hundred_million_5000) == -1){
				logger.info("【GDP  运算】大于等于800小于5000亿的判断 BigDecimal gdp = new BigDecimal() 的初始值为 ："+gdp);
				logger.info("【GDP  运算】进入大于等于800小于5000亿的判断   权重    0.07 * ((参数*1/168) + (1475/21)) 权重    ");
				gdp = BD(sa.getGdp())
						.multiply(new BigDecimal(1.0).setScale(2,BigDecimal.ROUND_HALF_UP))
						.divide(new BigDecimal(167.0),2,BigDecimal.ROUND_HALF_UP)
						.add(new BigDecimal( 1475.0)
						.divide(new BigDecimal(21.0),2,BigDecimal.ROUND_HALF_UP))
						.multiply(new BigDecimal(ConstantSA.gdp_weight)).setScale(2,BigDecimal.ROUND_HALF_UP);
				logger.info("【GDP  运算】大于等于0小于50亿的判断    权重   权重       0.07 * ((参数*1/168) + (1475/21)) 【结果】值为："+gdp);
				return gdp;
			}
			//大于等于5000亿的判断
			else if(ckCompareTo(sa.getGdp(),ConstantSA.hundred_million_5000) == 0 || ckCompareTo(sa.getGdp(),ConstantSA.hundred_million_5000) == 1){
				logger.info("【GDP  运算】大于大于等于5000亿的判断 BigDecimal gdp = new BigDecimal() 的初始值为 ："+gdp);
				logger.info("【GDP  运算】进入大于等于5000亿的判断   权重    ");
				gdp = new BigDecimal(ConstantSA.gdp_weight*100).setScale(2,BigDecimal.ROUND_HALF_UP);
				logger.info("【GDP  运算】大于等于5000亿的判断    权重   【结果】值为："+gdp);
				return gdp;
			}
		}
		logger.info("【GDP 运算】   结束");
		return gdp;
		
	}
	
	/**
	 * 总资产
	 * @param sa
	 * @return
	 */
	public BigDecimal totalAsset(StateInvestmentAsset sa){
		logger.info("【总资产 运算】   开始----值为："+sa.getTotalAsset());
		BigDecimal totalAsset = new BigDecimal(0);
		if( null!=sa.getTotalAsset()){
			//大于等于0小于20亿的判断
			if( ckCompareTo(sa.getTotalAsset(),0) == 1 && ckCompareTo(sa.getTotalAsset(),ConstantSA.total_asset_20) == -1 ){
				logger.info("【总资产  运算】大于等于0小于20亿的判断 BigDecimal totalAsset = new BigDecimal() 的初始值为 ："+totalAsset);
				logger.info("【总资产  运算】进入大于等于0小于20亿的判断            权重  0.12 * (参数*5/2) ");
				totalAsset = BD(sa.getTotalAsset())
						.multiply(new BigDecimal(5.0).setScale(2,BigDecimal.ROUND_HALF_UP)
						.divide(new BigDecimal(2.0), 2,BigDecimal.ROUND_HALF_UP))
						.multiply(new BigDecimal(ConstantSA.total_asset_weight)).setScale(2,BigDecimal.ROUND_HALF_UP);
				logger.info("【总资产  运算】大于等于0小于20亿的判断  0.12 * (参数*5/2)   【结果】值为："+totalAsset);
				return totalAsset;
			}
			//大于等于20小于80亿的判断
			else if(( ckCompareTo(sa.getTotalAsset(),ConstantSA.total_asset_20) == 0 || ckCompareTo(sa.getTotalAsset(),ConstantSA.total_asset_20) == 1 ) && ckCompareTo(sa.getTotalAsset(),ConstantSA.total_asset_80) == -1){
				logger.info("【总资产  运算】大于等于20小于80亿的判断 BigDecimal totalAsset = new BigDecimal() 的初始值为 ："+totalAsset);
				logger.info("【总资产  运算】进入大于等于20小于80亿的判断            权重 0.12 *( (参数*5/12) + (125/3) )  ");
				totalAsset = BD(sa.getTotalAsset())
						.multiply(new BigDecimal(5.0).setScale(2,BigDecimal.ROUND_HALF_UP)
						.divide(new BigDecimal(12.0), 2,BigDecimal.ROUND_HALF_UP))
						.add( new BigDecimal(125.0)
						.divide(new BigDecimal(3.0) , 2,BigDecimal.ROUND_HALF_UP) )
						.multiply(new BigDecimal(ConstantSA.total_asset_weight)).setScale(2,BigDecimal.ROUND_HALF_UP);
				logger.info("【总资产  运算】大于等于20小于80亿的判断   权重 0.12 *( (参数*5/12) + (125/3) )  【结果】值为："+totalAsset);
				return totalAsset;
			}
			//大于等于80小于200亿的判断
			else if(( ckCompareTo(sa.getTotalAsset(),ConstantSA.total_asset_80) == 0 || ckCompareTo(sa.getTotalAsset(),ConstantSA.total_asset_80) == 1 ) && ckCompareTo(sa.getTotalAsset(),ConstantSA.total_asset_200) == -1){
				logger.info("【总资产  运算】大于等于80小于200亿的判断 BigDecimal totalAsset = new BigDecimal() 的初始值为 ："+totalAsset);
				logger.info("【总资产  运算】进入大于等于80小于200亿的判断         权重 0.12 *( (参数*5/24) + (175/3) )   ");
				totalAsset = BD(sa.getTotalAsset())
						.multiply(new BigDecimal(5.0).setScale(2,BigDecimal.ROUND_HALF_UP)
						.divide(new BigDecimal(24.0), 2,BigDecimal.ROUND_HALF_UP))
						.add( new BigDecimal(175.0)
						.divide(new BigDecimal(3.0) , 2,BigDecimal.ROUND_HALF_UP) )
						.multiply(new BigDecimal(ConstantSA.total_asset_weight)).setScale(2,BigDecimal.ROUND_HALF_UP);
				logger.info("【总资产  运算】大于等于80小于200亿的判断    权重 0.12 *( (参数*5/24) + (175/3) )   【结果】值为："+totalAsset);
				return totalAsset;
			}
			
			//大于等于200亿的判断
			else if(ckCompareTo(sa.getTotalAsset(),ConstantSA.total_asset_200) == 0 || ckCompareTo(sa.getTotalAsset(),ConstantSA.total_asset_200) == 1){
				logger.info("【总资产  运算】大于等于200亿的判断 BigDecimal totalAsset = new BigDecimal() 的初始值为 ："+totalAsset);
				logger.info("【总资产  运算】进入大于等于200亿的判断            权重 0.12 *100  ");
				totalAsset = new BigDecimal(ConstantSA.total_asset_weight*100).setScale(2,BigDecimal.ROUND_HALF_UP);
				logger.info("【总资产  运算】大于等于200亿的判断     权重 0.12 *100  【结果】值为："+totalAsset);
				return totalAsset;
			}
		}
		logger.info("【总资产 运算】   结束");
		return totalAsset;
	}	
	/**
	 * 营业收入
	 * @param sa
	 * @return
	 */
	public BigDecimal mainIncome(StateInvestmentAsset sa){
		logger.info("【 营业收入  运算】   开始----值为："+sa.getMainIncome());
		BigDecimal mainIncome = new BigDecimal(0);
		if(null!=sa.getMainIncome()){
			
			//大于等于0小于10亿的判断
			if( ckCompareTo(sa.getMainIncome(),0) == 1 && ckCompareTo(sa.getMainIncome(),ConstantSA.main_income_10) == -1 ){
				logger.info("【 营业收入  运算】大于等于0小于10亿的判断      BigDecimal mainIncome = new BigDecimal() 的初始值为 ："+mainIncome);
				logger.info("【 营业收入  运算】进入大于等于0小于10亿的判断   权重  0.06 * (参数*5) ");
				mainIncome =BD(sa.getMainIncome())
						.multiply(new BigDecimal(5.0)).setScale(2,BigDecimal.ROUND_HALF_UP)
						.multiply(new BigDecimal(ConstantSA.main_income_weight)).setScale(2,BigDecimal.ROUND_HALF_UP);
				logger.info("【 营业收入  运算】大于等于10亿的判断   0.06 * (参数*5)  【结果】值为："+mainIncome);
				return mainIncome;
			}
			//大于等于10小于50亿的判断
			else if(( ckCompareTo(sa.getMainIncome(),ConstantSA.main_income_10) == 0 || ckCompareTo(sa.getMainIncome(),ConstantSA.main_income_10) == 1 ) && ckCompareTo(sa.getMainIncome(),ConstantSA.main_income_50) == -1){
				logger.info("【 营业收入  运算】大于等于10小于50亿的判断 BigDecimal mainIncome = new BigDecimal() 的初始值为 ："+mainIncome);
				logger.info("【 营业收入  运算】进入大于等于10小于50亿的判断    权重  0.06 * ((参数*5/8)+(175/4)) ");
				mainIncome =BD(sa.getMainIncome())
						.multiply(new BigDecimal(5.0)).setScale(2,BigDecimal.ROUND_HALF_UP)
						.divide(new BigDecimal(8.0),2,BigDecimal.ROUND_HALF_UP)
						.add(new BigDecimal(175.0)
						.divide(new BigDecimal(4.0),2,BigDecimal.ROUND_HALF_UP ))
						.multiply(new BigDecimal(ConstantSA.main_income_weight)).setScale(2,BigDecimal.ROUND_HALF_UP);
				logger.info("【 营业收入  运算】大于等于10小于50亿的判断    权重  0.06 * ((参数*5/8)+(175/4))  【结果】值为："+mainIncome);
				return mainIncome;
			}
			//大于等于50小于100亿的判断
			else if(( ckCompareTo(sa.getMainIncome(),ConstantSA.main_income_50) == 0 || ckCompareTo(sa.getMainIncome(),ConstantSA.main_income_50) == 1 ) && ckCompareTo(sa.getMainIncome(),ConstantSA.main_income_100) == -1){
				logger.info("【 营业收入  运算】大于等于50小于100亿的判断 BigDecimal mainIncome = new BigDecimal() 的初始值为 ："+mainIncome);
				logger.info("【 营业收入  运算】进入大于等于50小于100亿的判断    权重  0.06 * ((参数*1/2)+50) ");
				mainIncome =BD(sa.getMainIncome())
						.multiply(new BigDecimal(1.0)).setScale(2,BigDecimal.ROUND_HALF_UP)
						.divide(new BigDecimal(2.0),2,BigDecimal.ROUND_HALF_UP)
						.add(new BigDecimal(50.0))
						.multiply(new BigDecimal(ConstantSA.main_income_weight)).setScale(2,BigDecimal.ROUND_HALF_UP);
				logger.info("【 营业收入  运算】大于等于50小于100亿的判断  权重  0.06 * ((参数*1/2)+50)   【结果】值为："+mainIncome);
				return mainIncome;
			}
			
			//大于等于100亿的判断
			else if(ckCompareTo(sa.getMainIncome(),ConstantSA.main_income_100) == 0 || ckCompareTo(sa.getMainIncome(),ConstantSA.main_income_100) == 1){
				logger.info("【 营业收入  运算】大于等于100亿的判断 BigDecimal mainIncome = new BigDecimal() 的初始值为 ："+mainIncome);
				logger.info("【 营业收入  运算】进入大于等于100亿的判断    权重  0.06 * 100 ");
				mainIncome =new BigDecimal(ConstantSA.main_income_weight*100).setScale(2,BigDecimal.ROUND_HALF_UP);
				logger.info("【 营业收入  运算】大于等于100亿的判断   权重  0.06 * 100    【结果】值为："+mainIncome);
				return mainIncome;
			}
		}
		logger.info("【营业收入  运算】   结束");
		return mainIncome;
		
	}	
	
	
	/**
	 * 区域地位
	 * @return
	 */
	public BigDecimal regionalStatus(){
		return new BigDecimal(60*0.1);
		
	}
	/**行政级别
	 * 
	 * @return
	 */
	public BigDecimal admGrade (StateInvestmentAsset sa){
		BigDecimal temp = new BigDecimal(0);
		if("1".equals(sa.getAdmGrade())){
			temp = new BigDecimal(100*0.1);
		}else if("2".equals(sa.getAdmGrade())){
			temp = new BigDecimal(80*0.1);
		}else if("3".equals(sa.getAdmGrade())){
			temp = new BigDecimal(60*0.1);
		}
		return temp;
		
	}
	
	
	/** 政府负债率
	 *    
	 * @param sa
	 * @return
	 */
	 public BigDecimal liabilitiesRatio(StateInvestmentAsset sa){
		 BigDecimal liabilitiesRatio = new BigDecimal(0);
		 logger.info("【 政府负债率  运算】   开始----值为："+sa.getLiabilitiesRatio());
		 if( null!=sa.getLiabilitiesRatio()){
			 logger.info("【 政府负债率  运算】  参数/100=："+sa.getLiabilitiesRatio().divide(new BigDecimal(100.0),4,BigDecimal.ROUND_HALF_UP));
			 BigDecimal ckTemp = new BigDecimal(0);
			 ckTemp = sa.getLiabilitiesRatio().divide(new BigDecimal(100.0),4,BigDecimal.ROUND_HALF_UP);
			 //小于0.2的判断
			 if(  ckCompareTo(ckTemp,0.2) == -1 ){
				 logger.info("【 政府负债率  运算】大于0.2的判断  BigDecimal ckTemp = new BigDecimal() 的初始值为 ："+ckTemp);
				 logger.info("【 政府负债率  运算】进入大于0.2的判断    权重   0.08 * 100 ");
				 liabilitiesRatio = new BigDecimal(ConstantSA.lR_weight*100).setScale(2,BigDecimal.ROUND_HALF_UP);
				 logger.info("【政府负债率  运算】大于0.2的判断   权重   0.08 * 100       【结果】值为："+liabilitiesRatio);
				 return liabilitiesRatio;
			 }
			 //大于等于0.2小于0.8的判断
			 else if(( ckCompareTo(ckTemp,0.2) == 0 || ckCompareTo(ckTemp,0.2) == 1 ) && ckCompareTo(ckTemp,0.8) == -1){
				 logger.info("【 政府负债率  运算】大于等于0.2小于0.8的判断 BigDecimal ckTemp = new BigDecimal() 的初始值为 ："+ckTemp);
				 logger.info("【 政府负债率  运算】进入大于等于0.2小于0.8的判断    权重    0.08 * (-125*参数 + 125)   ");
				 liabilitiesRatio = ckTemp.multiply(new BigDecimal(-125.0))
						 .add(new BigDecimal(125.0))
						 .multiply(new BigDecimal(ConstantSA.lR_weight)).setScale(2,BigDecimal.ROUND_HALF_UP);
				 logger.info("【政府负债率  运算】大于等于0.2小于0.8的判断   权重       0.08 * (-125*参数 + 125)     【结果】值为："+liabilitiesRatio);
				 return liabilitiesRatio;
			 }
			//大于等于0.8小于4的判断
			else if(( ckCompareTo(ckTemp,0.8) == 0 || ckCompareTo(ckTemp,0.8) == 1 ) && ckCompareTo(ckTemp,4) == -1){
				logger.info("【 政府负债率  运算】大于等于0.8小于4的判断  BigDecimal ckTemp = new BigDecimal() 的初始值为 ："+ckTemp);
				logger.info("【 政府负债率  运算】进入大于等于0.8小于4的判断    权重    (参数 *( (负16分之125) + (125/4) ) )   ");
				liabilitiesRatio = ckTemp.multiply(new BigDecimal(-125.0))
						 .divide(new BigDecimal(16.0),2,BigDecimal.ROUND_HALF_UP)
						 .add(new BigDecimal(125)
						 .divide(new BigDecimal(4.0),2,BigDecimal.ROUND_HALF_UP ))
						 .multiply(new BigDecimal(ConstantSA.lR_weight)).setScale(2,BigDecimal.ROUND_HALF_UP);
				logger.info("【政府负债率  运算】大于等于0.8小于4的判断   权重     (参数 *( (负16分之125) + (125/4) ) )   【结果】值为："+liabilitiesRatio);
				 return liabilitiesRatio;		 
			 }
			//大于等于4的判断
			else if(ckCompareTo(ckTemp,4) == 0 || ckCompareTo(ckTemp,4) == 1){
				logger.info("【 政府负债率  运算】大于等于4的判断  BigDecimal ckTemp = new BigDecimal() 的初始值为 ："+ckTemp);
				logger.info("【 政府负债率  运算】进入大于等于4的判断    权重    返回-0");
				logger.info("【政府负债率  运算】大于等于4的判断   权重      返回-0【结果】值为："+liabilitiesRatio);
				 return liabilitiesRatio;
			}
			 
		 }
		 logger.info("【政府负债率  运算】     结束"+liabilitiesRatio);
		 return liabilitiesRatio;
	 }	
	 /**
	  * 净利润   
	  * 	
	  */
	 public BigDecimal	netProfit(StateInvestmentAsset sa){ 
			BigDecimal netProfit = new BigDecimal(0);
			//-1 小于  ,0 等于,1 大于
			logger.info("【净利润     运算】   开始----值为："+sa.getNetProfit());
			 if( null!=sa.getNetProfit()){
				 logger.info("【净利润     运算】  参数/1亿=："+sa.getNetProfit().divide(new BigDecimal(100000000.0),4,BigDecimal.ROUND_HALF_UP));
				 BigDecimal ckTemp = new BigDecimal(0);
				 ckTemp = sa.getNetProfit().divide(new BigDecimal(100000000.0),4,BigDecimal.ROUND_HALF_UP);
				 //小于0.2的判断
				 if(  ckCompareTo(ckTemp,0.2) == -1 ){
					 logger.info("【 净利润     运算】大于0.2的判断  BigDecimal ckTemp = new BigDecimal() 的初始值为 ："+ckTemp);
					 logger.info("【净利润     运算】进入大于0.2的判断    权重  0.03 *(参数*125/10的八次方) ");
					 netProfit = ckTemp.multiply(new BigDecimal(125.0)).setScale(2, BigDecimal.ROUND_HALF_UP)
							 .multiply(new BigDecimal(ConstantSA.net_profit_weight)).setScale(2, BigDecimal.ROUND_HALF_UP);
					 logger.info("【净利润     运算】大于0.2的判断       权重  0.03 *(参数*125/10的八次方)       【结果】值为："+netProfit);
					 return netProfit;
				 }
				 //大于等于0.2小于1的判断
				 else if(( ckCompareTo(ckTemp,0.2) == 0 || ckCompareTo(ckTemp,0.2) == 1 ) && ckCompareTo(ckTemp,1) == -1){
					 logger.info("【 净利润     运算】大于等于0.2小于1的判断  BigDecimal ckTemp = new BigDecimal() 的初始值为 ："+ckTemp);
					 logger.info("【净利润     运算】进入大于等于0.2小于1的判断    权重  0.03 *( 参数*125/4 +(75/4) )");
					 netProfit = ckTemp.multiply(new BigDecimal(125.0)).setScale(2, BigDecimal.ROUND_HALF_UP)
							 .divide(new BigDecimal(4.0),2, BigDecimal.ROUND_HALF_UP)
							 .add(new BigDecimal(75.0)
							 .divide(new BigDecimal(4.0)))
							 .multiply(new BigDecimal(ConstantSA.net_profit_weight)).setScale(2, BigDecimal.ROUND_HALF_UP);
					 logger.info("【净利润     运算】大于等于0.2小于1的判断      权重  0.03*( 参数*125/4 +(75/4) )   【结果】值为："+netProfit);
					 return netProfit;
				 }
				//大于等于1小于2的判断
				else if(( ckCompareTo(ckTemp,1) == 0 || ckCompareTo(ckTemp,1) == 1 ) && ckCompareTo(ckTemp,2) == -1){
					logger.info("【 净利润     运算】大于等于1小于2的判断  BigDecimal ckTemp = new BigDecimal() 的初始值为 ："+ckTemp);
					logger.info("【净利润     运算】进入大于等于1小于2的判断    权重  0.03 *(参数*25 + 25)  ");
					netProfit = ckTemp.multiply(new BigDecimal(25)).setScale(2, BigDecimal.ROUND_HALF_UP)
							 .add(new BigDecimal(25))
							 .multiply(new BigDecimal(ConstantSA.net_profit_weight)).setScale(2, BigDecimal.ROUND_HALF_UP);
					logger.info("【净利润     运算】大于等于1小于2的判断      权重  0.03 *(参数*25 + 25)  【结果】值为："+netProfit);
					return netProfit;
				 }
				//大于等于2小于10的判断
				else if(( ckCompareTo(ckTemp,2) == 0 || ckCompareTo(ckTemp,2) == 1 ) && ckCompareTo(ckTemp,10) == -1){
					logger.info("【 净利润     运算】大于等于2小于10的判断  BigDecimal ckTemp = new BigDecimal() 的初始值为 ："+ckTemp);
					logger.info("【净利润     运算】进入大于等于2小于10的判断    权重  0.03*(参数*25/8+(275/4))  ");
					netProfit = ckTemp.multiply(new BigDecimal(25)).setScale(2, BigDecimal.ROUND_HALF_UP)
							 .divide(new BigDecimal(8.0),2, BigDecimal.ROUND_HALF_UP)
							 .add(new BigDecimal(275.0)
							 .divide(new BigDecimal(4.0),2, BigDecimal.ROUND_HALF_UP))
							 .multiply(new BigDecimal(ConstantSA.net_profit_weight)).setScale(2, BigDecimal.ROUND_HALF_UP);
					logger.info("【净利润     运算】大于等于2小于10的判断      权重  0.03  *(参数*25/8+(275/4))      【结果】值为："+netProfit);
					return netProfit;
				 }
				//大于等于10的判断
				else if(ckCompareTo(ckTemp,10) == 0 || ckCompareTo(ckTemp,10) == 1){
					logger.info("【 净利润     运算】大于等于10的判断  BigDecimal ckTemp = new BigDecimal() 的初始值为 ："+ckTemp);
					logger.info("【净利润     运算】进入大于等于10的判断    权重  0.03 *100 ");
					netProfit = new BigDecimal(ConstantSA.net_profit_weight*100).setScale(2, BigDecimal.ROUND_HALF_UP);
					logger.info("【净利润     运算】大于等于10的判断      权重  0.03   *100 【结果】值为："+netProfit);
					return netProfit;
				}
				 
			 }
			 logger.info("【净利润     运算】     结束"+netProfit);
			return netProfit;
	 }
	 /**
	  * 资产负债率     
	  *
	  */
	 public BigDecimal alRatio(StateInvestmentAsset sa){ 
		 BigDecimal alRatio = new BigDecimal(0);
			//-1 小于  ,0 等于,1 大于
		 if( null!=sa.getAlRatio()){
			 logger.info("【资产负债率     运算】   开始----值为："+sa.getAlRatio());
			 logger.info("【资产负债率     运算】  参数/100=："+sa.getAlRatio().divide(new BigDecimal(100.0),2,BigDecimal.ROUND_HALF_UP));
			 BigDecimal ckTemp = new BigDecimal(0.00);
			 ckTemp = sa.getAlRatio().divide(new BigDecimal(100.0),2,BigDecimal.ROUND_HALF_UP);
			 //大于0小于0.25的判断
			 if(  ckCompareTo(ckTemp,0.25) == -1 ){
				 logger.info("【 资产负债率     运算】大于0小于0.25的判断  BigDecimal ckTemp = new BigDecimal() 的初始值为 ："+ckTemp);
				 logger.info("【资产负债率     运算】进入大于0小于0.25的判断    权重  0.06* 100 ");
				 alRatio = new BigDecimal(ConstantSA.al_ratio_weight*100);
				 logger.info("【资产负债率     运算】大于0小于0.25的判断       权重    0.06* 100   【结果】值为："+alRatio);
				 return alRatio;
			 }
			 //大于等于0.25小于0.55的判断
			 else if(( ckCompareTo(ckTemp,0.25) == 0 || ckCompareTo(ckTemp,0.25) == 1 ) && ckCompareTo(ckTemp,0.55) == -1){
				 logger.info("【 资产负债率     运算】大于等于0.25小于0.55的判断  BigDecimal ckTemp = new BigDecimal() 的初始值为 ："+ckTemp);
				 logger.info("【资产负债率     运算】进入大于等于0.25小于0.55的判断    权重  0.06* (参数 * 负3分之500 + ( 3分之425 ))");
				 alRatio = ckTemp.multiply(new BigDecimal(-500.0)).setScale(2,BigDecimal.ROUND_HALF_UP)
						 .divide(new BigDecimal(3.0),2,BigDecimal.ROUND_HALF_UP)
						 .add(new BigDecimal(425.0)
						 .divide(new BigDecimal(3) ,2,BigDecimal.ROUND_HALF_UP))
						 .multiply(new BigDecimal(ConstantSA.al_ratio_weight)).setScale(2, BigDecimal.ROUND_HALF_UP);
				 logger.info("【资产负债率     运算】大于等于0.25小于0.55的判断       权重    0.06* (参数 * 负3分之500 + ( 3分之425 ))  【结果】值为："+alRatio);
				 return alRatio;
			 }
			//大于等于0.55小于0.65的判断
			else if(( ckCompareTo(ckTemp,0.55) == 0 || ckCompareTo(ckTemp,0.55) == 1 ) && ckCompareTo(ckTemp,0.65) == -1){
				logger.info("【 资产负债率     运算】大于等于0.55小于0.65的判断  BigDecimal ckTemp = new BigDecimal() 的初始值为 ："+ckTemp);
				logger.info("【资产负债率     运算】进入大于等于0.55小于0.65的判断    权重  0.06* (参数*-250 + 187.5)");
				alRatio = ckTemp.multiply(new BigDecimal(-250.0)).setScale(2,BigDecimal.ROUND_HALF_UP)
						 .add(new BigDecimal(187.5))
						 .multiply(new BigDecimal(ConstantSA.al_ratio_weight)).setScale(2, BigDecimal.ROUND_HALF_UP);
				logger.info("【资产负债率     运算】大于等于0.55小于0.65的判断       权重    0.06* (参数*-250 + 187.5)   【结果】值为："+alRatio);
				return alRatio;
			 }
			//大于等于0.65小于0.85的判断
			else if(( ckCompareTo(ckTemp,0.65) == 0 || ckCompareTo(ckTemp,0.65) == 1 ) && ckCompareTo(ckTemp,0.85) == -1){
				logger.info("【 资产负债率     运算】大于等于0.65小于0.85的判断  BigDecimal ckTemp = new BigDecimal() 的初始值为 ："+ckTemp);
				logger.info("【资产负债率     运算】进入大于等于0.65小于0.85的判断    权重  0.06* (参数*-125 + 106.25)   ");
				alRatio = ckTemp.multiply(new BigDecimal(-125.0)).setScale(2,BigDecimal.ROUND_HALF_UP)
						 .add(new BigDecimal(106.25))
						 .multiply(new BigDecimal(ConstantSA.al_ratio_weight)).setScale(2, BigDecimal.ROUND_HALF_UP);
				logger.info("【资产负债率     运算】大于等于0.65小于0.85的判断       权重    0.06*  (参数*-125 + 106.25)   【结果】值为："+alRatio);
				return alRatio;
			 }		 
		 }
		 logger.info("【资产负债率     运算】     结束"+alRatio);
		return alRatio;
	 }		
	/**
	 * 净资产收益率 
	 */
	 public BigDecimal netProfitNetAsset(StateInvestmentAsset sa){ 
			BigDecimal netProfitNetAsset = new BigDecimal(0);
			//-1 小于  ,0 等于,1 大于
			 if( null!=sa.getNetProfit() && null!=sa.getNetAsset()){
				 if(  ckCompareTo(sa.getNetAsset(),0) == 0 ){
					 return netProfitNetAsset;
				 }
				 logger.info("【 净资产收益率      运算】   开始----值为："+sa.getNetProfit().divide(sa.getNetAsset(),5,BigDecimal.ROUND_HALF_UP));
				 logger.info("【 净资产收益率      运算】 净利润/净资产保留5为小数=："+sa.getNetProfit().divide(sa.getNetAsset(),5,BigDecimal.ROUND_HALF_UP));
				 BigDecimal ckTemp = new BigDecimal(0);
				 ckTemp = sa.getNetProfit().divide(sa.getNetAsset(),5,BigDecimal.ROUND_HALF_UP);
				 if(  ckCompareTo(ckTemp,0) == -1 ){
					 return netProfitNetAsset;
				 }
				 //大于0小于0.01的判断
				 if(  ckCompareTo(ckTemp,0.01) == -1 ){
					 logger.info("【  净资产收益率      运算】大于0小于0.01的判断  BigDecimal ckTemp = new BigDecimal() 的初始值为 ："+ckTemp);
					 logger.info("【 净资产收益率      运算】进入大于0小于0.01的判断    权重  0.03 *  (参数* 2500) ");
					 netProfitNetAsset = ckTemp
							 .multiply(new BigDecimal(2500.0)).setScale(2, BigDecimal.ROUND_HALF_UP)
							 .multiply(new BigDecimal(ConstantSA.net_profit_net_asset_weight)).setScale(2, BigDecimal.ROUND_HALF_UP);
					 logger.info("【 净资产收益率      运算】大于0小于0.01的判断       权重    0.03*(参数* 2500)   【结果】值为："+netProfitNetAsset);
					 return netProfitNetAsset;
				 }
				 //大于等于0.01小于0.07的判断
				 else if(( ckCompareTo(ckTemp,0.01) == 0 || ckCompareTo(ckTemp,0.01) == 1 ) && ckCompareTo(ckTemp,0.07) == -1){
					 logger.info("【  净资产收益率      运算】大于等于0.01小于0.07的判断  BigDecimal ckTemp = new BigDecimal() 的初始值为 ："+ckTemp);
					 logger.info("【 净资产收益率      运算】进入大于等于0.01小于0.07的判断    权重  0.03*  (参数*1250+12.5)");
					 netProfitNetAsset =ckTemp
							 .multiply(new BigDecimal(1250.0)).setScale(2, BigDecimal.ROUND_HALF_UP)
							 .add(new BigDecimal(12.5)).setScale(2, BigDecimal.ROUND_HALF_UP)
							 .multiply(new BigDecimal(ConstantSA.net_profit_net_asset_weight)).setScale(2, BigDecimal.ROUND_HALF_UP);
					 logger.info("【 净资产收益率      运算】大于等于0.01小于0.07的判断       权重    0.03* (参数*1250+12.5)   【结果】值为："+netProfitNetAsset);
					 return netProfitNetAsset;
				 }
				//大于等于0.07的判断
				else if(ckCompareTo(ckTemp,0.07) == 0 || ckCompareTo(ckTemp,0.07) == 1){
					 logger.info("【  净资产收益率      运算】大于等于0.07的判断  BigDecimal ckTemp = new BigDecimal() 的初始值为 ："+ckTemp);
					 logger.info("【 净资产收益率      运算】进入大于等于0.07的判断    权重  0.03 * 100 ");
					 netProfitNetAsset =new BigDecimal(ConstantSA.net_profit_net_asset_weight*100).setScale(2, BigDecimal.ROUND_HALF_UP);
					 logger.info("【 净资产收益率      运算】大于等于0.07的判断     权重    0.03 * 100   【结果】值为："+netProfitNetAsset);
					 return netProfitNetAsset;
				}
				 
			 }
			 logger.info("【 净资产收益率      运算】     结束"+netProfitNetAsset);
			return netProfitNetAsset;
	 }		
	/**
	 * 股权抵押率
	 */
	 public BigDecimal pledgeRatio (StateInvestmentAsset sa){ 
			BigDecimal pledgeRatio = new BigDecimal(0);
			//-1 小于  ,0 等于,1 大于

				 logger.info("【股权抵押率     运算】 目前写死   开始----值为："+0.6);
				 BigDecimal ckTemp = new BigDecimal(0.6);
			 

				 logger.info("【 股权抵押率      运算】大于0小于0.01的判断  BigDecimal ckTemp = new BigDecimal() 的初始值为 ："+ckTemp);
				 logger.info("【 股权抵押率      运算】进入大于0小于0.01的判断    权重  0.1 * (参数*-125/2 + (175/2) )  ");
				 pledgeRatio = ckTemp
						 .multiply(new BigDecimal(-125.0)).setScale(2,BigDecimal.ROUND_HALF_UP)
						 .divide(new BigDecimal(2.0),2,BigDecimal.ROUND_HALF_UP)
						 .add(new BigDecimal(175.0)
						 .divide(new BigDecimal(2.0),2,BigDecimal.ROUND_HALF_UP))
						 .multiply(new BigDecimal( ConstantSA.pledge_ratio_weight)).setScale(2,BigDecimal.ROUND_HALF_UP);
				 logger.info("【股权抵押率      运算】大于0小于0.01的判断       权重    0.1*  (参数*-125/2 + (175/2) ) 【结果】值为："+pledgeRatio);
			return pledgeRatio;
	 }	
	/**
	 * 土地质押率 
	 */
	 public BigDecimal scaleLandValue (StateInvestmentAsset sa){ 
			BigDecimal scaleLandValue = new BigDecimal(0);
			//-1 小于  ,0 等于,1 大于
			 logger.info("【土地质押率       运算】   开始----值为：融资规模:"+sa.getScale()+",土地抵押估值:"+sa.getLandValue());
			 if( null!=sa.getScale() && null!=sa.getLandValue()){
				 if(ckCompareTo(sa.getLandValue(),0) == 0){
					 return scaleLandValue;
				 }
				 logger.info("【土地质押率       运算】   开始----值为："+sa.getScale().divide(sa.getLandValue(),5,BigDecimal.ROUND_HALF_UP));
				 logger.info("【土地质押率       运算】 融资规模/土地抵押估值 保留5为小数=："+sa.getScale().divide(sa.getLandValue(),5,BigDecimal.ROUND_HALF_UP));
				 BigDecimal ckTemp = new BigDecimal(0);
				 ckTemp = sa.getScale().divide(sa.getLandValue(),5,BigDecimal.ROUND_HALF_UP);
				 //大于0小于0.3的判断
				 if(  ckCompareTo(ckTemp,0.3) == -1 ){
					 logger.info("【  土地质押率       运算】大于0小于0.3的判断  BigDecimal ckTemp = new BigDecimal() 的初始值为 ："+ckTemp);
					 logger.info("【 土地质押率       运算】进入大于0小于0.3的判断    权重  0.1 * 100");
					 scaleLandValue = new BigDecimal(ConstantSA.scale_land_value_weight * 100);
					 logger.info("【土地质押率       运算】大于0小于0.3的判断        权重  0.1 * 100     【结果】值为："+scaleLandValue);
					 return scaleLandValue;
				 }
				 //大于等于0.3小于0.7的判断
				 else if(( ckCompareTo(ckTemp,0.3) == 0 || ckCompareTo(ckTemp,0.3) == 1 ) && ckCompareTo(ckTemp,0.7) == -1){
					 logger.info("【  土地质押率       运算】大于等于0.3小于0.7的判断  BigDecimal ckTemp = new BigDecimal() 的初始值为 ："+ckTemp);
					 logger.info("【 土地质押率       运算】进入大于等于0.3小于0.7的判断    权重  0.1 * (参数*-125 + (275/2) )");
					 scaleLandValue = ckTemp
							 .multiply(new BigDecimal(-125)).setScale(2,BigDecimal.ROUND_HALF_UP)
							 .add(new BigDecimal(275.0)
							 .divide(new BigDecimal(2.0),2,BigDecimal.ROUND_HALF_UP ))
							 .multiply(new BigDecimal(ConstantSA.scale_land_value_weight)).setScale(2,BigDecimal.ROUND_HALF_UP);
					 logger.info("【土地质押率       运算】大于等于0.3小于0.7的判断        权重  0.1 *  (参数*-125 + (275/2)    【结果】值为："+scaleLandValue);
					 return scaleLandValue;
				 }
				//大于等于0.7小于1的判断
				 else if(( ckCompareTo(ckTemp,0.7) == 0 || ckCompareTo(ckTemp,0.7) == 1 ) && ckCompareTo(ckTemp,1) == -1){
					 logger.info("【  土地质押率       运算】大于等于0.7小于1的判断  BigDecimal ckTemp = new BigDecimal() 的初始值为 ："+ckTemp);
					 logger.info("【 土地质押率       运算】进入大于等于0.7小于1的判断    权重  0.1 * (参数*-250/3 + (325/3) )");
					 scaleLandValue = ckTemp
							 .multiply(new BigDecimal(-250)).setScale(2,BigDecimal.ROUND_HALF_UP)
							 .divide(new BigDecimal(3.0) ,2,BigDecimal.ROUND_HALF_UP)
							 .add(new BigDecimal(325.0)
							 .divide(new BigDecimal(3.0),2,BigDecimal.ROUND_HALF_UP ))
							 .multiply(new BigDecimal(ConstantSA.scale_land_value_weight)).setScale(2,BigDecimal.ROUND_HALF_UP);
					 logger.info("【土地质押率       运算】大于等于0.7小于1的判断        权重  0.1 *  (参数*-250/3 + (325/3))  【结果】值为："+scaleLandValue);
					 return scaleLandValue;
				 }
				//大于等于1小于2的判断
				 else if(( ckCompareTo(ckTemp,1) == 0 || ckCompareTo(ckTemp,1) == 1 ) && ckCompareTo(ckTemp,2) == -1){
					 logger.info("【  土地质押率       运算】大于等于1小于2的判断  BigDecimal ckTemp = new BigDecimal() 的初始值为 ："+ckTemp);
					 logger.info("【 土地质押率       运算】进入大于等于1小于2的判断    权重  0.1 * (参数*-50 + 100)  )");
					 scaleLandValue = ckTemp
							 .multiply(new BigDecimal(-50)).setScale(2,BigDecimal.ROUND_HALF_UP)
							 .add(new BigDecimal(100.0))
							 .multiply(new BigDecimal(ConstantSA.scale_land_value_weight)).setScale(2,BigDecimal.ROUND_HALF_UP);
					 logger.info("【土地质押率       运算】大于等于1小于2的判断        权重  0.1 *  【结果】值为："+scaleLandValue);
					 return scaleLandValue;
				 }
			 }
			 logger.info("【 土地质押率        运算】     结束"+scaleLandValue);
			return scaleLandValue;
	 }	 
	/**
	 * 应收账款质押率 
	 * @param sa
	 * @return
	 */
	 public BigDecimal scaleReceivable(StateInvestmentAsset sa){
			BigDecimal scaleReceivable = new BigDecimal(0);
			//-1 小于  ,0 等于,1 大于
			 logger.info("【应收账款质押率        运算】   开始----值为：融资规模:"+sa.getScale()+",应收账款质押金额 :"+sa.getReceivable());
			 if( null!=sa.getScale() && null!=sa.getReceivable()){
				 if(ckCompareTo(sa.getReceivable(),0) == 0){
					 return scaleReceivable;
				 }
				 logger.info("【应收账款质押率        运算】   开始----值为："+sa.getScale().divide(sa.getReceivable(),5,BigDecimal.ROUND_HALF_UP));
				 logger.info("【应收账款质押率        运算】 融资规模/应收账款质押金额 保留5为小数=："+sa.getScale().divide(sa.getReceivable(),5,BigDecimal.ROUND_HALF_UP));
				 BigDecimal ckTemp = new BigDecimal(0);
				 ckTemp = sa.getScale().divide(sa.getReceivable(),5,BigDecimal.ROUND_HALF_UP);
				 //大于0小于0.4的判断
				 if(  ckCompareTo(ckTemp,0.4) == -1 ){
					 logger.info("【  应收账款质押率       运算】大于0小于0.4的判断  BigDecimal ckTemp = new BigDecimal() 的初始值为 ："+ckTemp);
					 logger.info("【应收账款质押率       运算】进入大于0小于0.4的判断    权重  0.1 * 100");
					 scaleReceivable = new BigDecimal(ConstantSA.scale_receivable_weight * 100);
					 logger.info("【应收账款质押率       运算】大于0小于0.4的判断        权重  0.1 * 100     【结果】值为："+scaleReceivable);
					 return scaleReceivable;
				 }
				 //大于等于0.4小于1的判断
				 else if(( ckCompareTo(ckTemp,0.4) == 0 || ckCompareTo(ckTemp,0.4) == 1 ) && ckCompareTo(ckTemp,1) == -1){
					 logger.info("【  应收账款质押率       运算】大于等于0.4小于1的判断 BigDecimal ckTemp = new BigDecimal() 的初始值为 ："+ckTemp);
					 logger.info("【应收账款质押率       运算】进入大于等于0.4小于1的判断    权重  0.1 * (参数*-125+150)");
					 scaleReceivable =ckTemp
							 .multiply(new BigDecimal(-125.0)).setScale(2,BigDecimal.ROUND_HALF_UP)
							 .add(new BigDecimal(150.0) )
							 .multiply(new BigDecimal(ConstantSA.scale_receivable_weight)).setScale(2,BigDecimal.ROUND_HALF_UP);
					 logger.info("【应收账款质押率       运算】大于等于0.4小于1的判断        权重  0.1 *    (参数*-125+150)   【结果】值为："+scaleReceivable);
					 return scaleReceivable;
				 }
				//大于等于1小于2的判断
				 else if(( ckCompareTo(ckTemp,1) == 0 || ckCompareTo(ckTemp,1) == 1 ) && ckCompareTo(ckTemp,2) == -1){
					 logger.info("【  应收账款质押率       运算】大于等于1小于2的判断  BigDecimal ckTemp = new BigDecimal() 的初始值为 ："+ckTemp);
					 logger.info("【应收账款质押率       运算】进入大于等于1小于2的判断    权重  0.1 * (参数*-50+100)");
					 scaleReceivable =ckTemp
							 .multiply(new BigDecimal(-50.0)).setScale(2,BigDecimal.ROUND_HALF_UP)
							 .add(new BigDecimal(100.0) )
							 .multiply(new BigDecimal(ConstantSA.scale_receivable_weight)).setScale(2,BigDecimal.ROUND_HALF_UP);
					 logger.info("【应收账款质押率       运算】大于等于1小于2的判断        权重  0.1 *  (参数*-50+100)    【结果】值为："+scaleReceivable);
					 return scaleReceivable;
				 }
			 }
			 logger.info("【应收账款质押率        运算】     结束"+scaleReceivable);
			return scaleReceivable;
	 }	 
	
	

	 /**
	  *人大决议
	  */
	 public BigDecimal govDecision(StateInvestmentAsset sa){
		 BigDecimal govDecision = new BigDecimal(0);
		 if(sa.getGovDecision() == 1 ){
			 govDecision = new BigDecimal(100 * 0.05);
		 }
		return govDecision;
		 
	 }
	 /**
	  * 同意融资批复
	  */
	 public BigDecimal govAgreement(StateInvestmentAsset sa){
		 BigDecimal govAgreement = new BigDecimal(0);
		 if(sa.getGovAgreement() == 1 ){
			 govAgreement = new BigDecimal(100 * 0.05);
		 }
		return govAgreement;
		 
	 }
	 /**
	  * 财政安排函
	  */
	 public BigDecimal financeArrangement(StateInvestmentAsset sa){
		 BigDecimal financeArrangement = new BigDecimal(0);
		 if(sa.getFinanceArrangement() == 1 ){
			 financeArrangement = new BigDecimal(100 * 0.1);
		 }
		return financeArrangement;
		 
	 }
	
	
	/**
	 * 判断大小  
	 * @param parameter
	 * @param lv
	 * @return -1 小于  ,0 等于,1 大于
	 */
	private  int ckCompareTo(BigDecimal parameter ,double lv){
		return parameter.compareTo(BigDecimal.valueOf(lv));
	}
	
	/**
	 *  亿单位的 除以10的八次方 1亿
	 * @param parameter
	 * @param lv
	 * @return
	 */
	private   BigDecimal  BD(BigDecimal parameter ){
		return parameter.divide(BigDecimal.valueOf(ConstantSA.bigDecimal),2,BigDecimal.ROUND_HALF_UP);
	}
}
