package com.wenzchao.wechat.entity.message.call;

/**
 * 客服消息基类
 * 
 * @author Venz
 * 
 */
public class Call {

	private String touser;
	private String msgtype;

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

}
