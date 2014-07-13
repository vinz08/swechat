package com.wenzchao.wechat.entity.message.out;

/**
 * 发送消息基类
 * 
 * @author Venz
 * 
 */
public class BaseMessage {

	private String ToUserName; // 接收方帐号（一个OpenID）
	private String FromUserName; // 开发者微信号
	private long CreateTime; // 消息创建时间 （整型）
	private String MsgType; // 接收消息类型 text/image/voice/video/music/news

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		this.ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.FromUserName = fromUserName;
	}

	public long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(long createTime) {
		this.CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		this.MsgType = msgType;
	}

}
