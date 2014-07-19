package com.wenzchao.wechat.entity.message.call;

import com.wenzchao.wechat.entity.message.mass.Media;

/**
 * 语音客服消息
 * 
 * @author Venz
 * 
 */
public class VoiceCall extends Call {

	private Media voice;

	public Media getVoice() {
		return voice;
	}

	public void setVoice(Media voice) {
		this.voice = voice;
	}

}
