package com.wenzchao.swechat.entity.message.out;

/**
 * 发送文本消息
 * 
 * @author Venz
 * 
 */
public class TextMessage extends BaseMessage {

	private String Content; // 消息内容

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		this.Content = content;
	}

}
