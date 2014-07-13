package com.wenzchao.wechat.entity.message.out;

/**
 * 发送图片信息
 * 
 * @author Venz
 * 
 */
public class ImageMessage extends BaseMessage {

	private String MediaId; // 通过上传多媒体文件，得到的id。

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		this.MediaId = mediaId;
	}

}
