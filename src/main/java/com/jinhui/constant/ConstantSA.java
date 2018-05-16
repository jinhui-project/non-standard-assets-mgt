package com.jinhui.constant;

import java.io.Serializable;


public class ConstantSA implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1172815190822986001L;
	/**
	 * 财政总收入      权重    0.15
	 */
	public final static Double gov_total_income_weight = 0.15;
	/**
	 * 一般预算收      权重  0.2
	 */
	public final static Double general_income_weight = 0.2;
	/**
	 *GDP      权重  0.07
	 */
	public final static Double gdp_weight = 0.07;
	
	/**
	 *政府负债率      权重  0.08
	 */
	public final static Double liabilities_ratio_weight = 0.08;
	
	/**
	 *行政级别      权重  0.1
	 */
	public final static Double  adm_grade_weight = 0.1;
	
	/**
	 * 区域地位   权重  0.1
	 */
	public final static Double  regional_status_weight = 0.1;
	
	/**
	 * 总资产   权重  0.12
	 */
	public final static Double  total_asset_weight = 0.12;
	/**
	 * 营业收入  权重  0.06
	 */
	public final static Double  main_income_weight = 0.06;
	
	/**
	 * 净利润  权重  0.03
	 */
	public final static Double  net_profit_weight = 0.03;
	
	/**
	 * 资产负债率  权重 0.06
	 */
	public final static Double  al_ratio_weight = 0.06;
	
	
	/**
	 * 净资产收益率  权重  0.03
	 */
	public final static Double  net_profit_net_asset_weight = 0.03;
	
	/**
	 * 股权抵押率  权重 0.1
	 */
	public final static Double  pledge_ratio_weight = 0.1;
	
	/**
	 * 土地质押率  权重 0.1
	 */
	public final static Double  scale_land_value_weight = 0.1;
	
	/**
	 *  应收账款质押率  权重  0.1
	 */
	public final static Double  scale_receivable_weight = 0.1;
	
	/**
	 *政府负债率 权重  0.8
	 */
	public final static double lR_weight = 0.08;

	/**
	 *  亿单位的 除以10的八次方 1亿
	 */
	public final static Double bigDecimal = 100000000.00;
	
	
	/**
	 *财政总收入  亿单位用于判断 10亿
	 */
	public final static double lv1 = 1000000000.00;
	/**
	 *财政总收入  亿单位用于判断 40亿
	 */
	public final static double lv4 = 4000000000.00;
	/**
	 *财政总收入  亿单位用于判断 80亿
	 */
	public final static double lv8 = 8000000000.00;
	/**
	 *财政总收入  亿单位用于判断 300亿
	 */
	public final static double lv30 = 30000000000.00;
	
	/**
	 *一般预算收入  亿单位用于判断 5亿
	 */
	public final static double hundred_million_5 = 500000000.00;
	/**
	 *一般预算收入  亿单位用于判断 20亿
	 */
	public final static double hundred_million_20 = 2000000000.00;
	/**
	 *一般预算收入  亿单位用于判断 40亿
	 */
	public final static double hundred_million_40 = 4000000000.00;
	/**
	 *一般预算收入  亿单位用于判断 100亿
	 */
	public final static double hundred_million_100 = 10000000000.00;
	
	/**
	 *GDP  亿单位用于判断 50亿
	 */
	public final static double hundred_million_50 = 5000000000.00;
	/**
	 *GDP 亿单位用于判断 200亿
	 */
	public final static double hundred_million_200 = 20000000000.00;
	/**
	 *GDP  亿单位用于判断 800亿
	 */
	public final static double hundred_million_800 = 80000000000.00;
	/**
	 *GDP  亿单位用于判断 5000亿
	 */
	public final static double hundred_million_5000 = 500000000000.00;
	
	/**
	 * 总资产  亿单位用于判断 20亿
	 */
	public final static double total_asset_20 = 2000000000.00;
	/**
	 * 总资产  亿单位用于判断 80亿
	 */
	public final static double total_asset_80 = 8000000000.00;
	/**
	 * 总资产  亿单位用于判断 200亿
	 */
	public final static double total_asset_200 = 20000000000.00;
	
	/**
	 *营业收入  亿单位用于判断 10亿
	 */
	public final static double main_income_10 = 1000000000.00;
	/**
	 *营业收入  亿单位用于判断 50亿
	 */
	public final static double main_income_50 = 5000000000.00;
	/**
	 *营业收入  亿单位用于判断 100亿
	 */
	public final static double main_income_100 = 10000000000.00;
		

}
