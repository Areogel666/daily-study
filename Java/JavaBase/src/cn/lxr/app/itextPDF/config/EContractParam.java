package cn.lxr.app.itextPDF.config;

import cn.lxr.app.itextPDF.entity.DocumentSetting;

public class EContractParam {
	/*=========================文件设置===========================*/
	public static final DocumentSetting PDF_SETTING;
	
	/*=========================路径设置===========================*/
//	public static final String E_CONTRACT_PATH = SkyEye.getRealPath("WEB-INF/res/eContract/");
//	public static final String FONT_SONGTI_PATH = SkyEye.getRealPath("WEB-INF/res/simsun.ttc,1");
	
	/*=========================合同设置===========================*/
	public static final float WATERMARK_OPACITY = 0.08f;//透明度
	public static final float HEADER_SCALEPERSENT = 1.2f;//缩放比例%
	public static final float HEADER_LINE_SCALEPERSENT = 34;//缩放比例%
	public static final int HEADER_FONT_SIZE = 10;//字体大小
	public static final float WATERMARK_SCALEPERSENT = 22;//缩放比例%
	
	static {
		PDF_SETTING = new DocumentSetting("公司", "电子合同", "平台", true);
	}
}
