package com.wenzchao.core.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;

/**
 * DAO基类
 * 
 * @author Venz
 * 
 */
@Repository
public class BaseDao extends JdbcDaoSupport {

	private static Logger log = Logger.getLogger(BaseDao.class);

	@Autowired
	private DataSourceTransactionManager transactionManager;

	@Resource(name = "dataSource")
	public void setSuperDataSource(DataSource dataSource) {
		super.setDataSource(transactionManager.getDataSource());
	}

	public int update(String sql, List<Object> params) {
		Object args[] = null;
		int result = 0;
		try {
			if (null == params) {
				result = this.getJdbcTemplate().update(sql);
			} else {
				args = params.toArray();
				result = this.getJdbcTemplate().update(sql, args);
			}
			log.info("执行更新SQL：" + sql);
			log.info("参数：" + args);
			log.info("执行结果：" + result + "条");
		} catch (Exception e) {
			log.error("执行更新SQL时发生错误：" + sql, e);
			log.error("参数：" + args);
		}
		return result;
	}

	public List<Map<String, Object>> query4List(String sql, List<Object> params) {
		Object args[] = null;
		List<Map<String, Object>> mapList = null;
		try {
			if (null == params) {
				mapList = this.getJdbcTemplate().queryForList(sql);
			} else {
				args = params.toArray();
				mapList = this.getJdbcTemplate().queryForList(sql, args);
			}
			log.info("执行查询SQL：" + sql);
			log.info("参数：" + args);
			log.info("执行结果：" + mapList.size() + "条");
		} catch (Exception e) {
			log.error("执行查询SQL时发生错误：" + sql, e);
			log.error("参数：" + args);
		}
		return mapList;
	}

	public Map<String, Object> query4Map(String sql, List<Object> params) {
		Object args[] = null;
		Map<String, Object> map = null;
		try {
			if (null == params) {
				map = this.getJdbcTemplate().queryForMap(sql);
			} else {
				args = params.toArray();
				map = this.getJdbcTemplate().queryForMap(sql, args);
			}
			log.info("执行查询SQL：" + sql);
			log.info("参数：" + args);
			log.info("执行结果：" + (map.isEmpty() ? 0 : 1) + "条");
		} catch (Exception e) {
			log.error("执行查询SQL时发生错误：" + sql, e);
			log.error("参数：" + args);
		}
		return map;
	}

	public void call(String storedProcedure, List<Object> params) {
		Object tempArgs[] = null;
		if (null != params) {
			tempArgs = params.toArray();
		}
		final String STORED_PROCEDURE_NAME = storedProcedure;
		final Object ARGS[] = tempArgs;
		try {
			this.getJdbcTemplate().execute(new CallableStatementCreator() {
				public CallableStatement createCallableStatement(Connection conn) throws SQLException {
					StringBuffer sqlBuffer = new StringBuffer("{").append(STORED_PROCEDURE_NAME).append("(");
					int index = 1;
					if(null != ARGS) {
						index = ARGS.length;
						for (int i = 0; i < index; i++) {
							if(i == ARGS.length - 1) {
								sqlBuffer.append("?");
							} else {
								sqlBuffer.append("?,");
							}
						}
					}
					
					sqlBuffer.append(")}");
					CallableStatement statement = conn.prepareCall(sqlBuffer.toString());
					if(null != ARGS) {
						for (int i = 0; i < index; i++) {
							statement.setObject((i + 1), ARGS[i]);
						}
					}
					log.info("执行存储过程：" + STORED_PROCEDURE_NAME);
					log.info("参数：" + ARGS);
					return statement;
				}
			}, new CallableStatementCallback<Object>() {
				public Object doInCallableStatement(CallableStatement statement) throws SQLException, DataAccessException {
					
					log.info("执行存储过程成功：" + STORED_PROCEDURE_NAME);
					return null;
				}
	
			});
		} catch (Exception e) {
			log.error("执行存储过程时发生错误：" + STORED_PROCEDURE_NAME, e);
			log.error("参数：" + ARGS);
		}
	}

}
