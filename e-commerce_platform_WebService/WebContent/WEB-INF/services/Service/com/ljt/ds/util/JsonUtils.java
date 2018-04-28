package ljt.ds.util;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ljt.ds.model.Model;
import ljt.ds.model.ModelList;

public class JsonUtils {

	/**
	 * 通过传入一个列表对象,调用指定方法将列表中的数据生成一个JSON规格指定字符串
	 *
	 * @param list
	 *            列表对象
	 * @return String "[{},{}]"
	 */
	public static String listToJson(List<?> list) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (list != null && list.size() > 0) {
			for (Object obj : list) {
				json.append(objectToJson(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}

	/**
	 * 通过传入一个列表对象,调用指定方法将列表中的数据生成一个JSON规格指定字符串
	 *
	 * @param list
	 *            列表对象
	 * @return String "[{},{}]"
	 */
	public static String modelListToJson(ModelList<?> list) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (list != null && list.size() > 0) {
			for (Object obj : list) {
				json.append(objectToJson(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}

	public static String mapListToJson(List<?> list) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (list != null && list.size() > 0) {
			for (Object obj : list) {
				json.append(objectToJson(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}

	public static String modelToJson(Model<String, Object> model) {
		Set<String> keys = model.getAttributes().keySet();
		String key = "";
		String value = "";
		StringBuffer jsonBuffer = new StringBuffer();
		jsonBuffer.append("{");
		for (Iterator<String> it = keys.iterator(); it.hasNext();) {
			key = it.next();
			value = objectToJson2(model.getAttributeValue(key));
			jsonBuffer.append("\"" + key + "\"" + ":" + value);
			if (it.hasNext()) {
				jsonBuffer.append(",");
			}
		}
		jsonBuffer.append("}");
		return jsonBuffer.toString();
	}

	public static String mapToJson(Map<String, Object> model) {
		Set<String> keys = model.keySet();
		String key = "";
		String value = "";
		StringBuffer jsonBuffer = new StringBuffer();
		jsonBuffer.append("{");
		for (Iterator<String> it = keys.iterator(); it.hasNext();) {
			key = it.next();
			value = objectToJson2(model.get(key));
			jsonBuffer.append("\"" + key + "\"" + ":" + value);
			if (it.hasNext()) {
				jsonBuffer.append(",");
			}
		}
		jsonBuffer.append("}");
		return jsonBuffer.toString();
	}

	/**
	 * 传入任意一个 object对象生成一个指定规格的字符串
	 *
	 * @param object
	 *            任意对象
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public static String objectToJson(Object object) {
		StringBuilder json = new StringBuilder();
		if (object == null) {
			json.append("null");
		} else if (object instanceof String) {
			json.append("\"").append(object.toString().trim()).append("\"");
		} else if (object instanceof Double) {
			BigDecimal bigDecimal = new BigDecimal(object.toString());
			json.append(bigDecimal.toString());
		} else if (object instanceof Integer || object instanceof Boolean) {
			json.append(object.toString());
		} else if (object instanceof BigDecimal) {
			json.append(object.toString());
		} else if (object instanceof Model) {
			json.append(modelToJson((Model<String, Object>) object));
		} else if (object instanceof Map) {
			json.append(mapToJson((Map<String, Object>) object));
		} else {
			json.append(beanToJson2(object));
		}
		return json.toString();
	}

	/**
	 * 传入任意一个 object对象生成一个指定规格的字符串
	 *
	 * @param object
	 *            任意对象
	 * @return String
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unchecked")
	public static String objectToJson2(Object object) {
		StringBuilder json = new StringBuilder();
		if (object == null) {
			json.append("null");
		} else if (object instanceof String) {
			json.append("\"")
					.append(UnicodeUtils.toUnicode(object.toString().trim(), false))
					.append("\"");
		} else if (object instanceof Double) {
			BigDecimal bigDecimal = new BigDecimal(object.toString());
			json.append(bigDecimal.toString());
		} else if (object instanceof Integer || object instanceof Boolean || object instanceof Long) {
			json.append(object.toString());
		} else if (object instanceof BigDecimal) {
			json.append(object.toString());
		} else if (object instanceof Model) {
			json.append(modelToJson((Model<String, Object>) object));
		} else if (object instanceof ModelList) {
			json.append(modelListToJson((ModelList<?>) object));
		} else if (object instanceof Map) {
			json.append(mapListToJson((List<?>) object));
		} else if(object instanceof Timestamp){
			json.append("\"").append(object.toString()).append("\"");
		} else if(object instanceof Date){
			json.append("\"").append(object.toString()).append("\"");
		}else {
			json.append(beanToJson2(object));
		}
		return json.toString();
	}

	/**
	 * 传入任意一个 Javabean对象生成一个指定规格的字符串
	 *
	 * @param bean
	 *            bean对象
	 * @return String "{}"
	 */
	public static String beanToJson(Object bean, String... jsonArrKey) {
		StringBuilder json = new StringBuilder();
		json.append("{");
		PropertyDescriptor[] props = null;
		try {
			props = Introspector.getBeanInfo(bean.getClass(), Object.class)
					.getPropertyDescriptors();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (props != null) {
			for (int i = 0; i < props.length; i++) {
				try {
					String name = objectToJson(props[i].getName());
					Object value = props[i].getReadMethod().invoke(bean);
					if (value instanceof List) {
						json.append(name);
						json.append(":");
						if ((List<?>) value != null
								&& ((List<?>) value).size() > 0) {
							json.append("[");
							for (Object obj : ((List<?>) value)) {
								json.append(objectToJson(obj));
								json.append(",");
							}
							json.setCharAt(json.length() - 1, ']');
							json.append(",");
						} else {
							json.append("\"\"");
							json.append(",");
						}
					} else {
						if (jsonArrKey.length > 0) {
							if (!jsonArrKey[0]
									.equals(name.replaceAll("\"", ""))) {
								value = objectToJson(value);
							}
						} else {

							value = objectToJson(value);
						}
						json.append(name);
						json.append(":");
						json.append(value.toString());
						json.append(",");
					}
				} catch (Exception e) {
				}
			}
			json.setCharAt(json.length() - 1, '}');
		} else {
			json.append("}");
		}
		return json.toString();
	}

	/**
	 * 传入任意一个 Javabean对象生成一个指定规格的字符串
	 *
	 * @param bean
	 *            bean对象
	 * @return String "{}"
	 */
	public static String beanToJson2(Object bean) {
		StringBuilder json = new StringBuilder();
		json.append("{");
		PropertyDescriptor[] props = null;
		try {
			props = Introspector.getBeanInfo(bean.getClass(), Object.class)
					.getPropertyDescriptors();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (props != null) {
			for (int i = 0; i < props.length; i++) {
				try {
					String name = objectToJson(props[i].getName());
					Object value = props[i].getReadMethod().invoke(bean);
					if (value instanceof List) {
						json.append(name);
						json.append(":");
						if ((List<?>) value != null
								&& ((List<?>) value).size() > 0) {
							json.append("[");
							for (Object obj : ((List<?>) value)) {
								json.append(objectToJson2(obj));
								json.append(",");
							}
							json.setCharAt(json.length() - 1, ']');
							json.append(",");
						} else {
							json.append("\"\"");
							json.append(",");
						}
					} else {
						value = objectToJson2(value);
						json.append(name);
						json.append(":");
						json.append(value.toString());
						json.append(",");
					}
				} catch (Exception e) {
				}
			}
			json.setCharAt(json.length() - 1, '}');
		} else {
			json.append("}");
		}
		return json.toString();
	}
}
