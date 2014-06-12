package com.wenzchao.swechat.entity.message.out;

/**
 * 发送视频消息
 * 
 * @author Venz
 * 
 */
public class VideoMessage extends BaseMessage {

	private String mediaId; // 通过上传多媒体文件，得到的id
	private String title; // 视频消息的标题
	private String description; // 视频消息的描述

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

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

}
