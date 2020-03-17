package cn.lxr.util.xmlParser;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;

public class XmlGenerator {

	/**
	 * 创建文件
	 */
	public static Document generateDocument() {
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding(CCBDefaultCommonParams.getDefaultEncoding());
		return doc;
	}
	
	/**
	 * 创建根目录
	 */
	public static Element generateRootElement(Document doc) {
		return doc.addElement("ap");
	}
	
	/**
	 * 在element元素下添加内容
	 * 将对象转换为element元素
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static void addElementFromObj(Element element, Object obj) throws IllegalArgumentException, IllegalAccessException {
		Class<? extends Object> cla = obj.getClass();
		Field[] fields = cla.getDeclaredFields();//所有声明属性
		Field elementField = null;//类级节点属性
		try {
			elementField = cla.getDeclaredField("OWN_ELEMENT_NAME");//类级节点属性
			elementField.setAccessible(true);
		} catch (NoSuchFieldException e1) {
			System.out.println("=====================未配置OWN_ELEMENT_NAME字段，默认不添加类级节点=======================");
//			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		}
		//如果没有类级节点名，则直接从上级element中插入子节点;如果有，则先插入类级节点名
		if (elementField != null && !"".equals(elementField.get(obj).toString())) {
			element = element.addElement(elementField.get(obj).toString());
		}
		for (Field field : fields) {
			String fieldName = field.getName();
			String fieldValue = "";
			String type = field.getType().toString();
			field.setAccessible(true);
			if (type.endsWith("java.lang.String")) {//报文节点字段
				fieldValue = StringUtil.nullToString(field.get(obj));
				if ("OWN_ELEMENT_NAME".equals(fieldName)) {//跳过类级节点属性
					continue;
				} else if (!"".equals(fieldValue)) {//字段值不为空时 添加element元素
					element.addElement(fieldName).setText(fieldValue);
				}
			} else if (type.endsWith("Req")){//报文类对象（子节点使用）
				if (field.get(obj) != null) {//字段值不为空时 添加element元素
					Element childElement = element.addElement(fieldName);
					//子节点中添加子对象
					addElementFromObj(childElement, field.get(obj));
				}
			}
		}
	}

	public static void main(String[] args) {
		String xml = "";// 提交包XMLString
		
		ABCCmpReq req = new ABCCmpReq();
		req.setDbAccNo("1234");
		req.setDbProv("666");
		ABCCmeReq req2 = new ABCCmeReq();
		req2.setSerialNo("000");
//		req.setAbccmereq(req2);
		
		Document doc = ABCXmlGenerator.generateDocument();
		Element root = ABCXmlGenerator.generateRootElement(doc);
		try {
			ABCXmlGenerator.addElementFromObj(root, req);
			ABCXmlGenerator.addElementFromObj(root, req2);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		xml = doc.asXML();
		System.out.println(xml);
	}

}
