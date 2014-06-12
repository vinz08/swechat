package com.wenzchao.swechat.entity.message.out;

/**
 * 发送消息基类
 * 
 * @author Venz
 * 
 */
public class BaseMessage {

	private String toUserName; // 接收方帐号（一个OpenID）
	private String fromUserName; // 开发者微信号
	private long createTime; // 消息创建时间 （整型）
	private String msgType; // 接收消息类型 text/image/voice/video/music/news

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

}
