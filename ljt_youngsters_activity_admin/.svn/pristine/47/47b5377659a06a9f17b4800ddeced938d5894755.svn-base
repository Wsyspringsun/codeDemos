package com.ljt.ds.admin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ljt.ds.admin.dao.TestDao;
import com.ljt.ds.admin.service.TestService;

/**
 * 测试service实现类
 * @author lwk
 */
@Service
public class TestServiceImpl implements TestService {

	@Autowired
	private TestDao dao;
	
	@Override
	public List<Map<String, Object>> findAll(){
		return dao.findAll();
	}

}
