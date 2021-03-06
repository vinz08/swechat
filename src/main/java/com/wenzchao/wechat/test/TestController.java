package com.wenzchao.wechat.test;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.druid.pool.DruidDataSource;
import com.wenzchao.core.dao.BaseDao;
import com.wenzchao.wechat.entity.message.out.TextMessage;
import com.wenzchao.wechat.util.MessageType;
import com.wenzchao.wechat.util.MessageUtil;

@Controller
@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
public class TestController {

	@Autowired
	private BaseDao baseDao;
	
	@RequestMapping("/test")
	public String test() {
		Object id = 55;
		int result = 0;
		baseDao.update("insert into test values(?)", Arrays.asList(id));
		id = null;
		result = baseDao.query4Integer("select count(id) from test", null);
		baseDao.update("insert into test values(?)", Arrays.asList(id));
		
		return "";
	}
	
	public static void main(String[] args) {
		TextMessage message = new TextMessage();
		message.setContent("sdf");
		message.setCreateTime(new Date().getTime());
		message.setMsgType("text");
		message.setOpenId("dfg");
		message.setOriginalId("fdghh");
		
		System.out.println(MessageUtil.textMessage2Xml(message));
		
	}
}
