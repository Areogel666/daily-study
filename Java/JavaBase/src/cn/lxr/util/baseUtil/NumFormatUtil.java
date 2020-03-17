package cn.lxr.util.baseUtil;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * 数字格式 转换工具类
 * @author Diego Areogel
 */
public class NumFormatUtil {

	/**
	 * 
	* 将 10000.5 转换成10,000 .50格式  (四舍五入保留两位小数)
	* @param double num
	* @return String
	 */
	public static String numFormatWesternModel(double num){
		String sign = "";
		//判断正负
		if(num < 0 ){
			sign = "-";
		}
		String numStr = new String(Math.abs(num)+"");
		int index = 0;
		//判断是否含有"E"
		if(numStr.indexOf("E") != -1){//存在科学计数法
			index = Integer.parseInt(numStr.substring(numStr.indexOf("E")+1))+3;//保留两位小数
		}else{
			index = numStr.indexOf(".")+2;
		}
 		MathContext context = new MathContext(index, RoundingMode.HALF_UP);
		BigDecimal bigDecimal = new BigDecimal(Math.abs(num), context);
		//System.out.println(bigDecimal+"//"+new MathContext(index).toString());
		StringBuffer sBuffer = new StringBuffer(bigDecimal+"");
		StringBuffer resultBuffer = new StringBuffer();
		String decimal = "";
		//判断是否是小数
		int indexOf = sBuffer.indexOf(".");
		if(indexOf != -1){
			//保存小数部分
			decimal = sBuffer.substring(sBuffer.indexOf("."));
			if(decimal.length() == 2){
				decimal += "0"; 
			}
			sBuffer = new StringBuffer(sBuffer.substring(0, sBuffer.indexOf(".")));
		}else{
			decimal = ".00";
		}
		//每三位添加一个","  
		for (int i = sBuffer.length(); i > 0 ; i-=3) {
			String str = "";
			if(i-3>0){
				str = sBuffer.substring(i-3, i);
			}else{
				str = sBuffer.substring(0, i);
			}
			StringBuffer buffer = new StringBuffer(str);
			//最后一组不加","
			if(i != sBuffer.length()){
				buffer = buffer.append(",");
			}
			//通过两次倒转得到结果
			buffer = buffer.reverse();
			resultBuffer = resultBuffer.append(buffer);
		}
		resultBuffer = resultBuffer.append(sign).reverse().append(decimal);
		return resultBuffer.toString();
	}
}
