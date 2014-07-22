package com.wenzchao.wechat.entity.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 发送文本消息
 * 
 * @author Venz
 * 
 */
public class TextMessage extends BaseMessage {
	
	@XStreamAlias("Content")
	private String content; // 消息内容

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
