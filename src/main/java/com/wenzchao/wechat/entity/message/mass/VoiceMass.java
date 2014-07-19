package com.wenzchao.wechat.entity.message.mass;

/**
 * 群发语音信息
 * 
 * @author Venz
 *
 */
public class VoiceMass extends Mass {

	private Media voice;

	public Media getVoice() {
		return voice;
	}

	public void setVoice(Media voice) {
		this.voice = voice;
	}

}
