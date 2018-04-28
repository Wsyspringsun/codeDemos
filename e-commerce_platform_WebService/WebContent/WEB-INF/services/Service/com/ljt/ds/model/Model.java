package ljt.ds.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Model<K, V> extends HashMap<String, Object> implements
		Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 表名
	 */
	private String table = null;

	/**
	 * 名称
	 */
	private String name = null;

	/**
	 * 属性一覧
	 */
	private Map<String, Object> attributes = new LinkedHashMap<String, Object>();

	/**
	 * 构造函数.<br>
	 */
	public Model() {
	}

	/**
	 * 构造函数.<br>
	 */
	public Model(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	/**
	 * 构造函数.<br>
	 */
	public Model(int initialCapacity) {
		super(initialCapacity);
	}

	/**
	 * 构造函数.<br>
	 * 
	 * @param name
	 *            模块名
	 */
	public Model(String name) {

		// Model<String, Object> define =
		// ModelConfig.getInstance().getModel(name);
		ModelConfig config = new ModelConfig();
		Model<String, Object> define = config.getModel(name);

		this.name = define.getName();

		this.table = define.getTable();

		this.attributes = define.getAttributes();
	}

	/**
	 * 构造函数.<br>
	 * 
	 * @param model
	 *            模块
	 */
	public Model(Model<String, Object> model) {

		this.name = model.getName();

		this.table = model.getTable();

		Set<String> keySet = model.getAttributes().keySet();

		Iterator<String> itr = keySet.iterator();

		String key = null;

		while (itr.hasNext()) {

			key = (String) itr.next();

			try {
				addAttribute(key, model.getAttributeValue(key));

			} catch (java.lang.IllegalArgumentException e) {
				/* key存在的场合 */
				addAttribute(key, null);
			} catch (java.lang.RuntimeException runex) {
				/* key不存在的场合 */
				addAttribute(key, null);
			}
		}
	}

	/**
	 * 构造函数（从模块生成）.<br>
	 * 
	 * @param name
	 *            模块名
	 * @param model
	 *            模块
	 */
	public Model(String name, Model<String, Object> model) {

		Model<String, Object> define = ModelConfig.getInstance().getModel(name);

		this.name = define.getName();

		this.table = define.getTable();

		Set<String> keySet = define.getAttributes().keySet();

		Iterator<String> itr = keySet.iterator();

		String key = null;

		/* String value = null; */

		while (itr.hasNext()) {

			key = (String) itr.next();

			try {
				addAttribute(key, model.getAttributeValue(key));

			} catch (java.lang.IllegalArgumentException e) {
				/* key存在的场合 */
				addAttribute(key, null);
			} catch (java.lang.RuntimeException runex) {
				/* key不存在的场合 */
				addAttribute(key, null);
			}
		}
	}

	/**
	 * 取得模块名.<br>
	 * 
	 * @return 模块名
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设定模块名.<br>
	 * 
	 * @param name
	 *            模块名
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 取得表名.<br>
	 * 
	 * @return 表名
	 */
	public String getTable() {
		return table;
	}

	/**
	 * 设定表名.<br>
	 * 
	 * @param table
	 *            表名
	 */
	public void setTable(String table) {
		this.table = table;
	}

	/**
	 * 追加属性.<br>
	 * 
	 * @param attribute
	 *            属性
	 */
	public void addAttribute(Attribute attribute) {
		attributes.put(attribute.getName(), attribute);
	}

	/**
	 * 追加属性.<br>
	 * 
	 * @param name
	 *            属性名
	 * @param value
	 *            値
	 */
	public void addAttribute(String name, Object value) {
		Attribute attribute = new Attribute(name, value);

		addAttribute(attribute);
	}

	/**
	 * 取得属性一览.<br>
	 * 
	 * @return 属性一覧
	 */
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	/**
	 * 返回属性的配列.<br>
	 * 
	 * @return 属性的配列
	 * @throws Exception
	 *             例外
	 */
	public Object[] toArrayAttributes() {

		Attribute attribute = null;

		Set<String> keySet = attributes.keySet();

		List<Object> objects = new ArrayList<Object>();

		Iterator<String> itr = keySet.iterator();

		for (; itr.hasNext();) {
			attribute = (Attribute) attributes.get(itr.next());

			if (attribute.getValue() != null) {
				objects.add(attribute.getValue());
			}
		}
		return objects.toArray();

	}

	/**
	 * 取得属性.<br>
	 * 
	 * @param name
	 *            属性
	 * @return 属性
	 */
	public Attribute getAttribute(String name) {
		return (Attribute) attributes.get(name);
	}

	/**
	 * 取得属性的值.<br>
	 * 
	 * @param name
	 *            名称
	 * @return 値
	 */
	public Object getAttributeValue(String name) {

		Attribute attribute = (Attribute) attributes.get(name);

		return attribute.getValue();
	}

	/**
	 * 取得属性字符类型的值.<br>
	 * 
	 * @param name
	 *            名称
	 * @return 値
	 */
	public String getStringValue(String name) {

		Attribute attribute = (Attribute) attributes.get(name);

		if (attribute == null) {
			return "";
		}

		return String.valueOf(attribute.getValue());
	}

	/**
	 * 取得属性整数型的值.<br>
	 * 
	 * @param name
	 *            名称
	 * @return 値
	 */
	public int getIntValue(String name) {

		Attribute attribute = (Attribute) attributes.get(name);

		if (attribute == null) {
			return 0;
		}

		/* return ((Integer) attribute.getValue()).intValue(); */
		return Integer.valueOf(String.valueOf(attribute.getValue()));
	}

	/**
	 * 取得属性Long型的值.<br>
	 * 
	 * @param name
	 *            名称
	 * @return 値
	 */
	public long getLongValue(String name) {

		Attribute attribute = (Attribute) attributes.get(name);

		if (attribute == null) {
			return 0;
		}

		return Long.valueOf(String.valueOf(attribute.getValue()));
	}

	/**
	 * 取得属性Double型的值.<br>
	 * 
	 * @param name
	 *            名称
	 * @return 値
	 */
	public double getDoubleValue(String name) {

		Attribute attribute = (Attribute) attributes.get(name);

		if (attribute == null) {
			return 0;
		}

		return Double.valueOf(String.valueOf(attribute.getValue()));
	}

	/**
	 * 从属性中删除.<br>
	 * 
	 */
	@Override
	public void clear() {
		attributes.clear();
	}

	/**
	 * 返回是否含有指定Key的属性值.<br>
	 */
	@Override
	public boolean containsKey(Object name) {
		return attributes.containsKey(name);
	}

	@Override
	public boolean containsValue(Object value) {

		Attribute attribute = null;

		Set<String> keySet = attributes.keySet();

		/* List objects = new ArrayList(); */

		Iterator<String> itr = keySet.iterator();

		for (; itr.hasNext();) {
			attribute = (Attribute) attributes.get(itr.next());

			if (value.equals(attribute.getValue())) {
				return true;
			}
		}

		return false;
	}

	public Set<?> modeEntrySet() {
		return attributes.entrySet();
	}

	@Override
	public Object get(Object name) {
		Attribute attribute = (Attribute) attributes.get(name);

		if (attribute == null) {
			return null;
		}
		return attribute.getValue();
	}

	@Override
	public boolean isEmpty() {
		return attributes.isEmpty();
	}

	@Override
	public Set<String> keySet() {
		return attributes.keySet();
	}

	@Override
	public Object remove(Object name) {
		return attributes.remove(name);
	}

	@Override
	public int size() {
		return attributes.size();
	}

	@Override
	public Collection<Object> values() {
		return attributes.values();
	}

	private String[] sortKeys;
	private String[] sortOrder;

	public Model(String[] sortKeys, String[] sortOrder) {
		this.sortKeys = sortKeys;
		this.sortOrder = sortOrder;
	}

	public String[] getSortKeys() {
		return sortKeys;
	}

	public void setSortKeys(String[] key) {
		this.sortKeys = key;
	}

	public String[] getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String[] order) {
		this.sortOrder = order;
	}

	public Object getValueByName(String name) {

		Object value = null;

		value = getAttributeValue(name);

		if (value == null) {
			return "";
		} else {
			return value.toString();
		}
	}
}