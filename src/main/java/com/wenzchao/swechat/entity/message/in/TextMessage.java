package com.wenzchao.swechat.entity.message.in;

/**
 * 接收文本消息
 * 
 * @author Venz
 * 
 */
public class TextMessage extends BaseMessage {

	private String content;// 消息内容

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
