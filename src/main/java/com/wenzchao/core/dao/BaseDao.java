package com.wenzchao.core.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * DAO基类
 * 
 * @author Venz
 * 
 */
@Repository
public class BaseDao {

	private static Logger log = LoggerFactory.getLogger(BaseDao.class);

	@Autowired
	private DataSourceTransactionManager transactionManager;

	@Autowired
	private DruidDataSource dataSource;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public int updateReturnKey(final String sql, List<Object> params) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		Object tempArgs[] = null;
		int result = 0;
		if (null != params) {
			tempArgs = params.toArray();
		}
		final Object ARGS[] = tempArgs;
		try {
	        jdbcTemplate.update(new PreparedStatementCreator() {
	        	public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
	                PreparedStatement ps = conn.prepareStatement(sql, new String[] { "id" });
	                for (int i = 0; i < ARGS.length; i++) {
						ps.setObject((i + 1), ARGS[i]);
					}
	                return ps;
	            }
			}, keyHolder);
	        result = keyHolder.getKey().intValue();
	        log.info("执行更新SQL：" + sql);
			log.info("参数：" + objectArr2Str(ARGS));
			log.info("返回ID：" + result);
		} catch (Exception e) {
			log.error("执行更新SQL时发生错误：" + sql, e);
			log.error("参数：" + objectArr2Str(ARGS));
			throw new RuntimeException();
		}
		return result;
	}
	
	public int update(String sql, List<Object> params) {
		Object args[] = null;
		int result = 0;
		try {
			if (null == params) {
				result = jdbcTemplate.update(sql);
			} else {
				args = params.toArray();
				result = jdbcTemplate.update(sql, args);
			}
			log.info("执行更新SQL：" + sql);
			log.info("参数：" + objectArr2Str(args));
			log.info("执行结果：" + result + "条");
		} catch (Exception e) {
			log.error("执行更新SQL时发生错误：" + sql, e);
			log.error("参数：" + objectArr2Str(args));
			throw new RuntimeException();
		}
		return result;
	}

	public int[] batchUpdate(String sql, List<List<Object>> paramsList) {
		List<Object[]> argsList = new ArrayList<Object[]>();
		int result[] = null;
		try {
			for (List<Object> params : paramsList) {
				Object args[] = params.toArray();
				argsList.add(args);
			}
			result = jdbcTemplate.batchUpdate(sql, argsList);
			log.info("批量执行更新SQL：" + sql);
			log.info("参数：" + objectArr2Str(argsList.toArray()));
			log.info("执行结果：" + result + "条");
		} catch (Exception e) {
			log.error("批量执行更新SQL时发生错误：" + sql, e);
			log.error("参数：" + objectArr2Str(argsList.toArray()));
			throw new RuntimeException();
		}
		return result;
	}
	
	public int[] batchUpdate(List<String> sqlList) {
		int result[] = null;
		String sqlArr[] = null;
		try {
			sqlArr = sqlList.toArray(new String[sqlList.size()]);
			result = jdbcTemplate.batchUpdate(sqlArr);
			log.info("批量执行更新SQL：" + sqlArr);
			log.info("执行结果：" + result + "条");
		} catch (Exception e) {
			log.error("批量执行更新SQL时发生错误：" + sqlArr, e);
			throw new RuntimeException();
		}
		return result;
	}
	
	public List<Map<String, Object>> query4List(String sql, List<Object> params) {
		Object args[] = null;
		List<Map<String, Object>> mapList = null;
		try {
			if (null == params) {
				mapList = jdbcTemplate.queryForList(sql);
			} else {
				args = params.toArray();
				mapList = jdbcTemplate.queryForList(sql, args);
			}
			log.info("执行查询SQL：" + sql);
			log.info("参数：" + objectArr2Str(args));
			log.info("执行结果：" + mapList.size() + "条");
		} catch (Exception e) {
			log.error("执行查询SQL时发生错误：" + sql, e);
			log.error("参数：" + objectArr2Str(args));
			throw new RuntimeException();
		}
		return mapList;
	}

	public int query4Integer(String sql, List<Object> params) {
		Object args[] = null;
		List<Integer> intList = null;
		int result = -1;
		try {
			if (null == params) {
				intList = jdbcTemplate.queryForList(sql, Integer.class);
			} else {
				args = params.toArray();
				intList = jdbcTemplate.queryForList(sql, args, Integer.class);
			}
			if (!intList.isEmpty() && intList.size() > 0) {
				result = intList.get(0);
			}
			log.info("执行查询SQL：" + sql);
			log.info("参数：" + objectArr2Str(args));
			log.info("执行结果：" + (intList == null ? 0 : intList.size()) + "条");
		} catch (Exception e) {
			log.error("执行查询SQL时发生错误：" + sql, e);
			log.error("参数：" + objectArr2Str(args));
			throw new RuntimeException();
		}
		return result;
	}
	
	public String query4String(String sql, List<Object> params) {
		Object args[] = null;
		List<String> strList = null;
		String result = null;
		try {
			if (null == params) {
				strList = jdbcTemplate.queryForList(sql, String.class);
			} else {
				args = params.toArray();
				strList = jdbcTemplate.queryForList(sql, args, String.class);
			}
			if (!strList.isEmpty() && strList.size() > 0) {
				result = strList.get(0);
			}
			log.info("执行查询SQL：" + sql);
			log.info("参数：" + objectArr2Str(args));
			log.info("执行结果：" + (strList == null ? 0 : strList.size()) + "条");
		} catch (Exception e) {
			log.error("执行查询SQL时发生错误：" + sql, e);
			log.error("参数：" + objectArr2Str(args));
			throw new RuntimeException();
		}
		return result;
	}
	
	public Map<String, Object> query4Map(String sql, List<Object> params) {
		Object args[] = null;
		List<Map<String, Object>> mapList = null;
		Map<String, Object> map = null;
		try {
			if (null == params) {
				mapList = jdbcTemplate.queryForList(sql);
			} else {
				args = params.toArray();
				mapList = jdbcTemplate.queryForList(sql, args);
			}
			if (!mapList.isEmpty() && mapList.size() > 0) {
				map = mapList.get(0);
			}
			log.info("执行查询SQL：" + sql);
			log.info("参数：" + objectArr2Str(args));
			log.info("执行结果：" + mapList.size() + "条");
		} catch (Exception e) {
			log.error("执行查询SQL时发生错误：" + sql, e);
			log.error("参数：" + objectArr2Str(args));
			throw new RuntimeException();
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
			jdbcTemplate.execute(new CallableStatementCreator() {
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
					log.info("参数：" + objectArr2Str(ARGS));
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
			log.error("参数：" + objectArr2Str(ARGS));
			throw new RuntimeException();
		}
	}

	private static String objectArr2Str(Object objArr[]) {
		StringBuffer strBuffer = new StringBuffer("[");
		if (null != objArr) {
			for (int i = 0; i < objArr.length; i++) {
				strBuffer.append(null == objArr[i] ? null : objArr[i].toString());
				if (i != objArr.length - 1) {
					strBuffer.append(", ");
				}
			}
		}
		strBuffer.append("]");
		return strBuffer.toString();
	}
	
}
