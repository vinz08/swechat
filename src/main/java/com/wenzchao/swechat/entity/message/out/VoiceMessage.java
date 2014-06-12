package com.wenzchao.swechat.entity.message.out;

/**
 * 发送语音信息
 * 
 * @author Venz
 * 
 */
public class VoiceMessage extends BaseMessage {

	private String mediaId; // 通过上传多媒体文件，得到的id

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

}
