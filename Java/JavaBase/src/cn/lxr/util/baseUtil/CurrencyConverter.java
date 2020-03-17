package cn.lxr.util.baseUtil;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
/**
 * 注：经性能测试，第二种方法效率更高，在多次循环使用时更甚。具体参数如下(循环1000次)：
 * method 1 执行耗时 : 0.088 秒 壹佰贰拾叁万肆仟伍佰元伍角陆分
 * method 2 执行耗时 : 0.026 秒 壹佰贰拾叁万肆仟伍佰圆陆角陆分
 */
public class CurrencyConverter {
	/*------------------------------------------- 方法一---------------------------------------------*/
	static HashMap<Integer, String> hm = new HashMap<Integer, String>();

	public static String toUpper(String num) {
		// 小数如果没有，自动补个0
		// logger.debug("sa1:" + snum.indexOf("\\."));
		if (num.indexOf("\\.") == -1) {
			num = num + ".00";
		}

		hm.put(0, "零");
		hm.put(1, "壹");
		hm.put(2, "贰");
		hm.put(3, "叁");
		hm.put(4, "肆");
		hm.put(5, "伍");
		hm.put(6, "陆");
		hm.put(7, "柒");
		hm.put(8, "捌");
		hm.put(9, "玖");
		hm.put(10, "拾");
		hm.put(100, "佰");
		hm.put(1000, "仟");
		hm.put(10000, "万");
		String snum = num;
		String intpart = null;
		String decpart = null;
		String dec0 = null;
		String dec1 = null;
		String hasdec = null;

		String[] sa = new String[2];
		sa = snum.split("\\.");

		intpart = sa[0];
		decpart = sa[1];
		String[] sint = intpart.split(""); // 整数部分
		
		switch (sint.length) {
		case 1:
			snum = hm.get(Integer.parseInt(sint[0]));
			break;
		case 2:
			snum = hm.get(Integer.parseInt(sint[0])) + hm.get(10) + hm.get(Integer.parseInt(sint[1]));
			break;
		case 3:
			snum = hm.get(Integer.parseInt(sint[0])) + hm.get(100) + hm.get(Integer.parseInt(sint[1]))
					+ hm.get(10) + hm.get(Integer.parseInt(sint[2]));
			break;
		case 4:
			snum = hm.get(Integer.parseInt(sint[0])) + hm.get(1000) + hm.get(Integer.parseInt(sint[1]))
					+ hm.get(100) + hm.get(Integer.parseInt(sint[2])) + hm.get(10)
					+ hm.get(Integer.parseInt(sint[3]));
			break;
		case 5:
			snum = hm.get(Integer.parseInt(sint[0])) + hm.get(10000) + hm.get(Integer.parseInt(sint[1]))
					+ hm.get(1000) + hm.get(Integer.parseInt(sint[2])) + hm.get(100)
					+ hm.get(Integer.parseInt(sint[3])) + hm.get(10) + hm.get(Integer.parseInt(sint[4]));
			break;
		case 6:
			snum = hm.get(Integer.parseInt(sint[0])) + hm.get(10) + hm.get(Integer.parseInt(sint[1]))
					+ hm.get(10000) + hm.get(Integer.parseInt(sint[2])) + hm.get(1000)
					+ hm.get(Integer.parseInt(sint[3])) + hm.get(100) + hm.get(Integer.parseInt(sint[4]))
					+ hm.get(10) + hm.get(Integer.parseInt(sint[5]));
			break;
		case 7:
			snum = hm.get(Integer.parseInt(sint[0])) + hm.get(100) + hm.get(Integer.parseInt(sint[1]))
					+ hm.get(10) + hm.get(Integer.parseInt(sint[2])) + hm.get(10000)
					+ hm.get(Integer.parseInt(sint[3])) + hm.get(1000) + hm.get(Integer.parseInt(sint[4]))
					+ hm.get(100) + hm.get(Integer.parseInt(sint[5])) + hm.get(10)
					+ hm.get(Integer.parseInt(sint[6]));
			break;
		case 8:
			snum = hm.get(Integer.parseInt(sint[0])) + hm.get(1000) + hm.get(Integer.parseInt(sint[1]))
					+ hm.get(100) + hm.get(Integer.parseInt(sint[2])) + hm.get(10)
					+ hm.get(Integer.parseInt(sint[3])) + hm.get(10000) + hm.get(Integer.parseInt(sint[4]))
					+ hm.get(1000) + hm.get(Integer.parseInt(sint[5])) + hm.get(100)
					+ hm.get(Integer.parseInt(sint[6])) + hm.get(10) + hm.get(Integer.parseInt(sint[7]));
			break;
		case 9:
			snum = hm.get(Integer.parseInt(sint[0])) + "亿" + hm.get(Integer.parseInt(sint[1])) + hm.get(1000)
					+ hm.get(Integer.parseInt(sint[2])) + hm.get(100) + hm.get(Integer.parseInt(sint[3]))
					+ hm.get(10) + hm.get(Integer.parseInt(sint[4])) + hm.get(10000)
					+ hm.get(Integer.parseInt(sint[5])) + hm.get(1000) + hm.get(Integer.parseInt(sint[6]))
					+ hm.get(100) + hm.get(Integer.parseInt(sint[7])) + hm.get(10)
					+ hm.get(Integer.parseInt(sint[8]));
			break;
		case 10:
			snum = hm.get(Integer.parseInt(sint[0])) + hm.get(10) + hm.get(Integer.parseInt(sint[1])) + "亿"
					+ hm.get(Integer.parseInt(sint[2])) + hm.get(1000) + hm.get(Integer.parseInt(sint[3]))
					+ hm.get(100) + hm.get(Integer.parseInt(sint[4])) + hm.get(10)
					+ hm.get(Integer.parseInt(sint[5])) + hm.get(10000) + hm.get(Integer.parseInt(sint[6]))
					+ hm.get(1000) + hm.get(Integer.parseInt(sint[7])) + hm.get(100)
					+ hm.get(Integer.parseInt(sint[8])) + hm.get(10) + hm.get(Integer.parseInt(sint[9]));
			break;
		case 11:
			snum = hm.get(Integer.parseInt(sint[0])) + hm.get(100) + hm.get(Integer.parseInt(sint[1]))
					+ hm.get(10) + hm.get(Integer.parseInt(sint[2])) + "亿"
					+ hm.get(Integer.parseInt(sint[3])) + hm.get(1000) + hm.get(Integer.parseInt(sint[4]))
					+ hm.get(100) + hm.get(Integer.parseInt(sint[5])) + hm.get(10)
					+ hm.get(Integer.parseInt(sint[6])) + hm.get(10000) + hm.get(Integer.parseInt(sint[7]))
					+ hm.get(1000) + hm.get(Integer.parseInt(sint[8])) + hm.get(100)
					+ hm.get(Integer.parseInt(sint[9])) + hm.get(10) + hm.get(Integer.parseInt(sint[10]));
			break;
		case 12:
			snum = hm.get(Integer.parseInt(sint[0])) + hm.get(1000) + hm.get(Integer.parseInt(sint[1]))
					+ hm.get(100) + hm.get(Integer.parseInt(sint[2])) + hm.get(10)
					+ hm.get(Integer.parseInt(sint[3])) + "亿" + hm.get(Integer.parseInt(sint[4]))
					+ hm.get(1000) + hm.get(Integer.parseInt(sint[5])) + hm.get(100)
					+ hm.get(Integer.parseInt(sint[6])) + hm.get(10) + hm.get(Integer.parseInt(sint[7]))
					+ hm.get(10000) + hm.get(Integer.parseInt(sint[8])) + hm.get(1000)
					+ hm.get(Integer.parseInt(sint[9])) + hm.get(100) + hm.get(Integer.parseInt(sint[10]))
					+ hm.get(10) + hm.get(Integer.parseInt(sint[11]));
			break;
		}

		snum += "元";

		snum = snum.replaceAll("零仟", "");
		snum = snum.replaceAll("零佰", "零");
		snum = snum.replaceAll("零拾", "");
		snum = snum.replaceAll("零零亿", "亿");
		snum = snum.replaceAll("零亿", "亿");
		snum = snum.replaceAll("零零万", "万");
		snum = snum.replaceAll("零万", "万");
		snum = snum.replaceAll("亿万", "亿");
		snum = snum.replaceAll("零零元", "元");
		snum = snum.replaceAll("零元", "元");
//		snum = snum.replaceAll("壹拾", "拾");
		if (snum.startsWith("元"))
			snum = "零" + snum;
		
		BigDecimal decimal = new BigDecimal(Double.valueOf("0."+decpart));
		decimal = decimal.round(new MathContext(2));
		decpart = decimal.toString().replaceAll("0.", "");
		while(decpart.length() < 2){
			decpart += "0";
		}
		String[] sdec = decpart.split(""); // 小数部分
		if (sdec[0].equals("0") && sdec[1].equals("0")) {
			hasdec = "整";
			snum += hasdec;
		} else {
			if (sdec[0].equals("0"))
				dec0 = "零";
			else
				dec0 = hm.get(Integer.parseInt(sdec[0])) + "角";
			
			if (sdec[1].equals("0"))
				dec1 = "";
			else
				dec1 = hm.get(Integer.parseInt(sdec[1])) + "分";
			
			snum += dec0 + dec1;
		}			
		
		return snum;

	}
	
	/*------------------------------------------- 方法二---------------------------------------------*/
	 
	/**
	 * 数字转换成大写汉字
	 * @return
	 */
	
	static final String big = "零壹贰叁肆伍陆柒捌玖";     //大写  
    static final String[] units = {"仟佰拾", "角分"};    //单位  
  
      
    /** 
     * 数字转换成大写汉字
     */  
    public static String getDecimalStr(double d){  
        String str = new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP).toString();  
          
        //如果结果是整数，则去掉尾巴  
        if(str.endsWith(".00")){  
            str = str.replace(".00", "");  
        }  
        return str;  
    }  
      
    /** 
     * 金额是double类型的要先转换成字符串 
     * @param money 金额 
     */  
    public static String transform(double money){  
        String moneyStr = getDecimalStr(money);  
        return transform(moneyStr);  
    }  
      
    /** 
     * 金额转换成大字 
     */  
    public static String transform(String moneyStr){  
        String[] parts = moneyStr.split("\\."); //区别整数、小数部分  
        String result = "";  
          
        //处理整数部分  
        int length = parts[0].length(); //整数部分的位数  
        if(length>15){  
            return "金额太大,不能处理整数部分超过15位的金额！";  
        }  
        String intPart = parts[0];  
          
        //填充到16位，因为是分4组，每组4个数字  
        while(intPart.length()<16){  
            intPart = '0' + intPart;  
        }  
        //共分四组,右起四位一组,例如：0001,2003,0030,3400  
        String[] groups = new String[4];   
        for(int i=0; i < groups.length; i++){  
            int start = intPart.length()-(i+1)*4;   //开始位置  
            int end = intPart.length()-i*4;         //结束位置  
            groups[i] = intPart.substring(start, end);  
            groups[i] = transformGroup(groups[i]);  //当前组的四位数字转换成大写  
        }  
          
        //对这四组结果从高位到低位拼接起来，规则：[组4]万[组3]亿[组2]万[组1]圆  
        for(int i=groups.length-1; i>=0; i--){  
            if(i==3){   //第四组：万亿级  
                if(!"零".equals(groups[i])){  
                    result += groups[i] + "万";  
                }  
            }else if(i==2){ //第三组：亿级  
                if(!"零".equals(groups[i])){  
                    result += groups[i] + "亿";  
                }else{  
                    if(result.length()>0){  
                        result += "亿";  
                    }  
                }  
            }else if(i==1){ //第二组：万级  
                if(!"零".equals(groups[i])){  
                    result += groups[i] + "万";  
                }else if(!groups[i].startsWith("零")){  
                    result += groups[i];  
                }   
            }else{  //第一组：千级  
                if(!"零".equals(groups[i]) || result.length()==0){  
                    result += groups[i];  
                }  
                result += "圆";  
            }  
        }  
        if(!"零圆".equals(result) && result.startsWith("零")){  
            result = result.substring(1, result.length()); //最前面的可能出现的“零”去掉  
        }  
  
        //处理小数部分  
        if(parts.length==2){  
            String decimalPart = parts[1];  //小数部分  
            for(int i=0; i < decimalPart.length();i++){  
                int num = Integer.valueOf(decimalPart.charAt(i) + "");  //提取数字，左起  
                result += big.charAt(num) + "" + units[1].charAt(i);    //数字变大写加上单位  
            }  
            result = result.replace("零角", "零"); //去掉"零角"的"角"  
            result = result.replace("零分", "");  //去掉"零分"  
        }else{  
            result += "整";  //没有小数部分，则加上“整”  
        }  
          
        return result;  
    }  
      
    /** 
     * 处理整数部分的组，右起每四位是一组 
     * @param group 四位数字字符串 
     */  
    public static String transformGroup(String group){  
        String result = "";  
        int length = group.length();  
        for(int i=0; i < length; i++){  
            int digit = Integer.valueOf(group.charAt(i)+"");    //单个数字，左起  
            String unit = "";   //单位  
            if(i!=length-1){  
                unit = units[0].charAt(i) + "";   
            }  
            result += big.charAt(digit) + unit; //数字变大写加上单位  
        }  
          
        result = result.replace("零仟", "零");  
        result = result.replace("零佰", "零");  
        result = result.replace("零拾", "零");  
        //result = result.replace("壹拾", "拾");   
        while(result.contains("零零")){  
            result = result.replace("零零", "零"); //如果有“零零”则变成一个“零”  
        }  
          
        if(!"零".equals(result) && result.endsWith("零")){  
            result = result.substring(0, result.length()-1); //最未尾的可能出现的“零”去掉  
        }  
        return result;  
    }  
	
    /** 
     * 数字转换成大写汉字
     */  
    public static String transformToCaps(double d){
    	String s1 = getDecimalStr(d);
    	String s = transform(s1);
    	return s;
    }
}
