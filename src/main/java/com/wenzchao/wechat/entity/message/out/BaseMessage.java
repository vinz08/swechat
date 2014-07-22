package com.wenzchao.wechat.entity.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 发送消息基类
 * 
 * @author Venz
 * 
 */
public class BaseMessage {

	@XStreamAlias("ToUserName")
	private String openId; // 接收方帐号（一个OpenID）
	@XStreamAlias("FromUserName")
	private String originalId; // 开发者微信号（原始ID）
	@XStreamAlias("CreateTime")
	private long createTime; // 消息创建时间 （整型）
	@XStreamAlias("MsgType")
	private String msgType; // 接收消息类型 text/image/voice/video/music/news

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getOriginalId() {
		return originalId;
	}

	public void setOriginalId(String originalId) {
		this.originalId = originalId;
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
