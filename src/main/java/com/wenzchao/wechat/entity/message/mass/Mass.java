package com.wenzchao.wechat.entity.message.mass;

import java.util.List;

/**
 * 群发信息基类
 * 
 * @author Venz
 *
 */
public class Mass {

	private String msgtype;
	private Filter filter;
	List<String> touser;

	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public List<String> getTouser() {
		return touser;
	}

	public void setTouser(List<String> touser) {
		this.touser = touser;
	}

}
