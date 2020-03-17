package cn.lxr.util.baseUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * 
 * @ClassName: StringUtils 
 * @Description: (字符串处理工具类) 
 *
 */
public class StringUtils {
	
  /**
   * 比较字符串
   */
	 public static boolean equals(String str1,String str2){
		 if(nullToString(str1).equals(nullToString(str2))){
			 return true;
		 }
		 return false;
	 }
	 
   /**
   * 比较字对象字符串
   */
	 public static boolean equals(Object str1,Object str2){
		 if(nullToString(str1).equals(nullToString(str2))){
			 return true;
		 }
		 return false;
	 }
	
   /**
   * 转换decimal
   */
	 public static double parseDecimal(String numberStr,int scale) throws Exception
	   {
		   NumberFormat  nf = NumberFormat.getNumberInstance();
		   nf.setRoundingMode(RoundingMode.HALF_UP);
		   nf.setMaximumFractionDigits(scale);
		   return new Double(decimal(new Double(numberStr).doubleValue(),2)).doubleValue();
	   }
	 
	 /**
	  * 
	  * @Title: noScientificNotation 
	  * @Description: (忽略金融计数法) 
	  * @param number
	  * @param scale
	  * @return String 
	  * @throws 
	  */
	 public static String noScientificNotation(double number,int scale){
		 NumberFormat  nf = NumberFormat.getNumberInstance();
		 nf.setMaximumFractionDigits(scale);
	     nf.setRoundingMode(RoundingMode.HALF_UP);
	     nf.setGroupingUsed(false);
		 return nf.format(number).replaceAll(",","");
	 }

	 /**
   * 转换decimal
   */
	 public static String decimal(double number,int scale)
	   {
		   NumberFormat  nf = NumberFormat.getNumberInstance();
		   nf.setMaximumFractionDigits(scale);
		   nf.setRoundingMode(RoundingMode.HALF_UP);
		   return nf.format(number).replaceAll(",","");
	   }
	 
  /**
   * 去除空格+null转换''
   */
	public static String nullToString(Object str){
		if(str == null)
			return "";
			return str.toString().trim();
	}
  
  /**
   * 去除空格+null转换other
   */
	public static String nullToOther(Object str,Object other){
		if(isBlank(str))
			return nullToString(other);
			return nullToString(str);
	}
  
  
  /**
   * 非空值转换
   */
	public static String notBankToOther(Object str,Object other){
		if(isNotBlank(str))
			return nullToString(other);
			return nullToString(str);
	}
	
  
  /**
   * 是否为空
   */
	public static boolean isNull(Object o){
		if(o == null){
			return true;
		}
		return false;
	}
  
  /**
   * 是否为非空
   */
	public static boolean isNotNull(Object o){
		return !isNull(o);
	}
	
   /**
   * 是否为空值
   */
	public static boolean isBlank(Object o){
		if(o == null || "".equals(o.toString().trim())||"undefined".equals(o.toString().trim())){
			return true;
		}
		return false;
	}
  
   /**
   * 是否为非空值
   */
	public static boolean isNotBlank(Object o){
		return !isBlank(o);
	}
  
   /**
   * 保留两位小数(四舍五入)
   */
	public static String formatNumberDoubleTwo(String str) {
		try {
			String temp_num = str;
			if ((temp_num == null) || (temp_num.equals(""))) {
				temp_num = "0.00";
			} else {
				java.text.DecimalFormat ft = new java.text.DecimalFormat(
						"###0.00");
				//java.text.DecimalFormat ft =  new java.text.DecimalFormat(style); 
				BigDecimal bd = new BigDecimal(temp_num);
				temp_num = ft.format(bd);

			}
			return temp_num;
		} catch (Exception e) {
		}
		return "";
	}
}
