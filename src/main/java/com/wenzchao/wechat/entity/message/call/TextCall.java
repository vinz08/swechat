package com.wenzchao.wechat.entity.message.call;

import com.wenzchao.wechat.entity.message.mass.Text;

/**
 * 文本客服消息
 * 
 * @author Venz
 * 
 */
public class TextCall extends Call {

	private Text text;

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

}
