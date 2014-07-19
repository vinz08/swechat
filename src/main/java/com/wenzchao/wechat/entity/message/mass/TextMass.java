package com.wenzchao.wechat.entity.message.mass;

/**
 * 群发文本信息
 * 
 * @author Venz
 * 
 */
public class TextMass extends Mass {

	private Text text;

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

}
