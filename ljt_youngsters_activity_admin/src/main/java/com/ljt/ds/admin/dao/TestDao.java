package com.ljt.ds.admin.dao;

import java.util.List;
import java.util.Map;
/**
 * 测试dao接口类
 * @author lwk
 * @date 2018-4-26
 */
public interface TestDao {
	/**
	 * 查询所有
	 */
	public List<Map<String, Object>> findAll();
}
