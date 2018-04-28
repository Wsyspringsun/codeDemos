/*
 * Attribute.java
 *
 *2004/11/20 yamashina(DHC) 新規作成
 *
 * Copyright(C) Sony Solutions Corporation,All Rights Reserved.
 * Sony Global Solutions CONFIDENTIAL.
 *
 */
package ljt.ds.model;

import java.io.Serializable;

public class Attribute implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 桁数
	 */
	private int size;

	/**
	 * 値
	 */
	private Object value;

	/**
	 * 构造函数
	 */
	public Attribute() {
	}

	/**
	 * 构造函数.<br>
	 * 
	 * @param name
	 *            名称
	 * @param value
	 *            値
	 */
	public Attribute(String name, Object value) {

		this.name = name;

		if (value instanceof String) {
			// this.value =
			// StringUtils.ignoreString(ConvUtil.convToString(value));
			this.value = String.valueOf(value);
		} else {
			this.value = value;
		}

	}

	/**
	 * 取得名称.<br>
	 * 
	 * @return 名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设定名称.<br>
	 * 
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 取得size.<br>
	 * 
	 * @return size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * 设定size.<br>
	 * 
	 * @param size
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * 取得值.<br>
	 * 
	 * @return 值
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * 设定值.<br>
	 * 
	 * @param value
	 *            值
	 */
	public void setValue(Object value) {
		this.value = String.valueOf(value);
	}
}
