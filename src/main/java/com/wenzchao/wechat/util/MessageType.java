package com.wenzchao.wechat.util;

/**
 * 微信信息类型
 * 
 * @author Venz
 * 
 */
public enum MessageType {

	TEXT("text"), // 消息类型：文本
	IMAGE("image"), // 消息类型：图片
	VOICE("voice"), // 消息类型：声音
	VIDEO("video"), // 消息类型：视频
	MUSIC("music"), // 消息类型：音乐
	NEWS("news"), // 消息类型：图文
	LOCATION("location"), // 消息类型：地理位置
	LINK("link"), // 消息类型：链接
	EVENT("event"); // 消息类型：事件推送

	private String type;

	private MessageType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
