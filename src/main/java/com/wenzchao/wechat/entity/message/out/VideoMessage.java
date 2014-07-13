package com.wenzchao.wechat.entity.message.out;

/**
 * 发送视频消息
 * 
 * @author Venz
 * 
 */
public class VideoMessage extends BaseMessage {

	private String MediaId; // 通过上传多媒体文件，得到的id
	private String Title; // 视频消息的标题
	private String Description; // 视频消息的描述

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		this.MediaId = mediaId;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		this.Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		this.Description = description;
	}

}
