package com.wenzchao.wechat.entity.message.mass;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 文本
 * 
 * @author Venz
 *
 */
@XStreamAlias("text")
public class Text {

	@XStreamAlias("content")
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
