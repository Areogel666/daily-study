package cn.lxr.util.baseUtil;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapBeanUtil {
	private static Logger logger = LoggerFactory.getLogger(JavaBeanUtil.class);

	/**
	 * 实体类转map (一)
	 * <p>根据getter方法获取,无法得到没有按规则生成setter/getter的字段</p>
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> beanToMap(Object obj) {
		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);
					if (null == value) {
//						map.put(key, "");
						map.put(key.toUpperCase(), "");
					} else {
//						map.put(key, value);
						map.put(key.toUpperCase(), value);
					}
				}
			}

		} catch (

		Exception e) {
			logger.error("convertBean2Map Error {}", e);
		}
		return map;
	}

	/**
	 * 实体类转map (二)
	 * <p> 通过类反射转化,得到所有声明字段</p>
	 */
	public static Map<String, Object> objectToMap(Object obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (obj == null) {
			return map;
		}
		Class<? extends Object> clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		try {
			for (Field field : fields) {
				field.setAccessible(true);
//				map.put(field.getName(), field.get(obj));
				map.put(field.getName().toUpperCase(), field.get(obj));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * map转实体类 (一)
	 * <p>根据setter方法存储,必须存在按规则生成的setter/getter方法</p>
	 */
	public static <T> T mapToBean(Class<T> clazz, Map<String, Object> map) {
		T obj = null;
		if (!map.isEmpty()) {
			try {
				BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
				obj = clazz.newInstance(); // 创建 JavaBean 对象
				// 给 JavaBean 对象的属性赋值
				PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
				for (int i = 0; i < propertyDescriptors.length; i++) {
					PropertyDescriptor descriptor = propertyDescriptors[i];
					String propertyName = descriptor.getName();
					if (map.containsKey(propertyName)) {
						// 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
						Object value = map.get(propertyName);
						if ("".equals(value)) {//map空值对象属性为null
							value = null;
						}
						Object[] args = new Object[1];
						args[0] = value;
						descriptor.getWriteMethod().invoke(obj, args);
					} else if (map.containsKey(propertyName.toUpperCase())) {
						Object value = map.get(propertyName.toUpperCase());
						if ("".equals(value)) {
							value = null;
						}
						Object[] args = new Object[1];
						args[0] = value;
						descriptor.getWriteMethod().invoke(obj, args);
					}
				}
			} catch (IllegalAccessException e) {
				logger.error("convertMapToBean 实例化JavaBean失败 Error{}", e);
			} catch (IntrospectionException e) {
				logger.error("convertMapToBean 分析类属性失败 Error{}", e);
			} catch (IllegalArgumentException e) {
				logger.error("convertMapToBean 映射错误 Error{}", e);
			} catch (InstantiationException e) {
				logger.error("convertMapToBean 实例化 JavaBean 失败 Error{}", e);
			} catch (InvocationTargetException e) {
				logger.error("convertMapToBean字段映射失败 Error{}", e);
			} catch (Exception e) {
				logger.error("convertMapToBean Error{}", e);
			}
		}
		return (T) obj;
	} 

	/**
	 * map转实体类 (二)
	 * <p>通过类反射转化</p>
	 * @param map
	 * @param o 对象
	 * @return
	 * @throws Exception
	 */
	public static Object mapToObject(Map<String, Object> map, Object o) throws Exception {
		if (!map.isEmpty()) {
			for (String k : map.keySet()) {
				Object v = null;
				if (!k.isEmpty()) {
					v = map.get(k);
				}
				Field[] fields = null;
				fields = o.getClass().getDeclaredFields();
				for (Field field : fields) {
					if (field.getName().toUpperCase().equals(k.toUpperCase())) {
						field.setAccessible(true);
						// region--进行类型判断(此处强制转换成类中字段类型，避免出现反射存值类型不匹配)
						String type = field.getType().toString();
						if (type.endsWith("String")) {
							if (v != null) {
								v = v.toString();
							} else {
								v = "";
							}
						}else if (type.endsWith("Date")) {
							v = (Date)v;
						}else if  (type.endsWith("Boolean")) {
							v = Boolean.valueOf(v.toString());
						}else if  (type.endsWith("int")) {
							v = Integer.valueOf(v.toString());
						}else if  (type.endsWith("Long")) {
							v = Long.valueOf(v.toString());
						}
						// endregion
						field.set(o, v);
					}
				}
			}
		}
		return o;
	}

	/**
	 * map转实体类 (三)
	 * <p>通过类反射转化</p>
	 * @param <T>
	 * @param map
	 * @param clazz 加载类
	 * @return
	 */
	public static <T> T mapToObject(Map<String, Object> map, Class<T> clazz) {
		T obj = null;
		if (!map.isEmpty()) {
			try {
				obj = clazz.newInstance();
				
				Field[] fields = obj.getClass().getDeclaredFields();
				for (Field field : fields) {
					int mod = field.getModifiers();
					if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {//static和final属性不赋值
						continue;
					}
					Object object = map.get(field.getName()) == null ? map.get(field.getName().toUpperCase()) : map.get(field.getName());
					field.setAccessible(true);
					field.set(obj, object);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return (T)obj;
	}


}