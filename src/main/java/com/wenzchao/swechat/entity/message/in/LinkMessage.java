package com.wenzchao.swechat.entity.message.in;

/**
 * 接收链接消息
 * 
 * @author Venz
 * 
 */
public class LinkMessage extends BaseMessage {

	private String title; // 消息标题
	private String description; // 消息描述
	private String url; // 消息链接

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
