package cn.lxr.util.xmlParser;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;

public class XmlParser {

	/**
	 * 创建文件
	 * @throws DocumentException 
	 */
	public static Document parseXmlToDocument(String xmlString) throws DocumentException {
		return DocumentHelper.parseText(xmlString.trim());
	}
	
	/**
	 * 获取根目录
	 */
	public static Element parseRootElement(Document doc) {
		return doc.getRootElement();
	}
	
	/**
	 * 将element转换为对象
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws DocumentException 
	 */
	public static void parseObject(Element element, Object obj) throws IllegalArgumentException, IllegalAccessException {
		Class<? extends Object> cla = obj.getClass();
		Field[] fields = cla.getDeclaredFields();//所有声明属性
		Field elementField = null;//类级节点属性
		try {
			elementField = cla.getDeclaredField("OWN_ELEMENT_NAME");//类级节点属性
			elementField.setAccessible(true);
		} catch (NoSuchFieldException e1) {
			System.out.println("=====================未配置OWN_ELEMENT_NAME字段，默认不解析类级节点=======================");
		} catch (SecurityException e1) {
			e1.printStackTrace();
		}
		//如果没有类级节点名，则直接从上级element中解析子节点;如果有，则先解析类级节点名
		if (elementField != null && !"".equals(elementField.get(obj).toString())) {
			element = element.element(elementField.get(obj).toString());
		}
		for (Field field : fields) {
			String fieldName = field.getName();
			String type = field.getType().toString();
			field.setAccessible(true);
			if (type.endsWith("java.lang.String")) {//报文节点字段
				if ("OWN_ELEMENT_NAME".equals(fieldName)) {//跳过类级节点属性
					continue;
				}
				field.set(obj, StringUtil.nullToString(element.elementText(fieldName)));
				
			} else if (type.endsWith("Req")){//报文类对象（子节点使用）
				if (field.get(obj) != null) {//字段值不为空时 添加element元素
					Element childElement = element.element(fieldName);
					//子节点中添加子对象
					parseObject(childElement, field.get(obj));
				}
			}
		}
	}
	
	public static void main(String[] args) {
		String xml = "<?xml version=\"1.0\" encoding=\"GB18030\" standalone=\"yes\"?>\r\n" + 
				"<ap><Cmp><DbProv>666</DbProv><DbAccNo>1234</DbAccNo></Cmp><Cme><SerialNo>000</SerialNo></Cme></ap>\r\n" + 
				"";//XMLString
		ABCCmpReq req = new ABCCmpReq();
		try {
			Document doc = ABCXmlParser.parseXmlToDocument(xml);
			Element root = ABCXmlParser.parseRootElement(doc);
			ABCXmlParser.parseObject(root, req);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		System.out.println(req.toString());
	}

}
