package com.wenzchao.wechat.entity.message.call;

import com.wenzchao.wechat.entity.message.mass.Video;

/**
 * 视频客服信息
 * 
 * @author Venz
 * 
 */
public class VideoCall extends Call {

	private Video video;

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

}
