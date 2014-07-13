package com.wenzchao.wechat.entity.message.out;

/**
 * 发送音乐信息
 * 
 * @author Venz
 * 
 */
public class MusicMessage extends BaseMessage {

	private String Title; // 音乐标题
	private String Description; // 音乐描述
	private String MusicUrl; // 音乐链接
	private String HQMusicUrl; // 高质量音乐链接，WIFI环境优先使用该链接播放音乐
	private String ThumbMediaId; // 缩略图的媒体id，通过上传多媒体文件，得到的id

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

	public String getMusicUrl() {
		return MusicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		this.MusicUrl = musicUrl;
	}

	public String getHqMusicUrl() {
		return HQMusicUrl;
	}

	public void setHqMusicUrl(String hqMusicUrl) {
		this.HQMusicUrl = hqMusicUrl;
	}

	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.ThumbMediaId = thumbMediaId;
	}

}
