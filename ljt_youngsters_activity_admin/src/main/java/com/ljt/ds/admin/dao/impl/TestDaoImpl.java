package com.ljt.ds.admin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ljt.ds.admin.dao.TestDao;

/**
 * 测试dao层实现类
 * @author lwk
 *
 */
@Repository
public class TestDaoImpl implements TestDao {

	@Autowired
	private JdbcTemplate jdbcTemplate; //springJdbc模板
	
	@Override
	public List<Map<String, Object>> findAll(){
		List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from news");
		return list;
	}

}
