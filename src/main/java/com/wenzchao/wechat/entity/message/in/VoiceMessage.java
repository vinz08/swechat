package com.wenzchao.wechat.entity.message.in;

/**
 * 接收语音消息
 * 
 * @author Venz
 * 
 */
public class VoiceMessage extends BaseMessage {

	private String MediaId; // 语音消息媒体id，可以调用多媒体文件下载接口拉取数据。
	private String Format; // 语音格式，如amr，speex等
	private String Recognition; // 语言识别结果

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getFormat() {
		return Format;
	}

	public void setFormat(String format) {
		Format = format;
	}

	public String getRecognition() {
		return Recognition;
	}

	public void setRecognition(String recognition) {
		Recognition = recognition;
	}

}
