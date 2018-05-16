package com.jinhui.controller.statisticsFile;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jinhui.constant.ConstantEntity;
import com.jinhui.model.Products;
import com.jinhui.model.StatisticsFile;
import com.jinhui.service.statisticsFile.StatisticsFileService;
import com.jinhui.util.UpLoadUtil;

/**
 * 资产数据统计文件上传
 * @time 2017-4-10 下午2:10:26  
 * @author wsc
 * @desc
 *
 */
@Controller
@RequestMapping("/statisticsFile")
public class StatisticsFileController {
	private static Logger logger = LoggerFactory.getLogger(StatisticsFileController.class);

	@Resource
	StatisticsFileService statisticsFileService;
	/**
	 * 文件开头地址
	 */
	@Value("${uploadPath}")
	private  String uploadPath;
	
	/**
	 * 后台    资产数据统计文件上传
	 * @return
	 */
	@RequestMapping("/statisticsFileUpload")
	@ResponseBody
	public Map<String, String> statisticsFileUpload(MultipartFile imgcontent,StatisticsFile statisticsFile){
		Map<String, String> map = new HashMap<String, String>();		
		try {
				if(imgcontent.getSize() == 0){
					map.put("code","1");
					map.put("message","请选择需要上传的文件");
					return map;
				}
				if( !ckFileType(imgcontent)){
					map.put("code","1");
					map.put("message","请选择正确格式,doc,docx,ppt,pptx,xls,xlsx,pdf,bmp,jpg,jpeg,png,gif");
					return map;
				}
				UpLoadUtil u = new UpLoadUtil();
				
				map = u.upLoadMap(imgcontent,  ConstantEntity.FILE_FILE, uploadPath);
				logger.info("上传文件【upcode】："+map.get("upcode"));
				if("0".equals(map.get("upcode"))){
					StatisticsFile statisticsFiles = new StatisticsFile();
					statisticsFiles.setFilePath(map.get("file_path"));
					statisticsFiles.setFileName(map.get("file_name"));
					statisticsFiles.setFileType(map.get("file_type"));
					statisticsFiles.setAssetId(statisticsFile.getAssetId());
					try {
						statisticsFileService.insertSelective(statisticsFiles);
						map.put("file_id", String.valueOf(statisticsFiles.getId()));
					} catch (Exception e) {
						map.put("code","1");
						map.put("message","请选择需要上传的文件");
						return map;
					}
					logger.info("后台    上传文件fid:"+ statisticsFiles.getId());	
				}else{
					map.put("code","1");
					map.put("message","上传文件失败");
					return map;
				}

			
		} catch (Exception e) {
			map.put("code","1");
			map.put("message","请选择需要上传的文件");
			return map;
		}
		map.put("code","0");
		map.put("message","上传成功");
		return map;
	}
	
	/**
	 * 查询资产数据统计文件
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/queryStatisticsFile",method=RequestMethod.GET)
	@ResponseBody
	public String queryStatisticsFile(@RequestParam("assetId") int assetId){
		logger.info("输入：  "+ "assetId="+assetId);
		StatisticsFile statisticsFile = statisticsFileService.selectByAssetId(assetId);
		logger.info("输出：   "+JSON.toJSONString(statisticsFile));
		return JSON.toJSONString(statisticsFile,SerializerFeature.WriteMapNullValue);
	}
	
	private boolean ckFileType(MultipartFile imgcontent){	
		String fileType = imgcontent.getOriginalFilename();
		String nameType = fileType.substring(fileType.indexOf(".")+1,fileType.length());
		logger.info("ckFileType 上传后缀为：  "+ nameType);
		if( !"gif".equals( nameType.toLowerCase() ) && !"png".equals( nameType.toLowerCase() ) && 
			!"jpeg".equals( nameType.toLowerCase() ) && !"jpg".equals( nameType.toLowerCase() ) && 
			!"bmp".equals( nameType.toLowerCase() ) && !"pdf".equals( nameType.toLowerCase() ) && 
			!"xlsx".equals( nameType.toLowerCase() ) && !"xls".equals( nameType.toLowerCase() ) && 
			!"pptx".equals( nameType.toLowerCase() ) && !"ppt".equals( nameType.toLowerCase() ) && 
			!"docx".equals( nameType.toLowerCase() ) && !"".equals( nameType.toLowerCase() ) && 
			!"".equals( nameType.toLowerCase() ) && !"doc".equals( nameType.toLowerCase() ) ){
			return false;
		}
		return true;		
	}
	
	
	
}
