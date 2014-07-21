package com.wenzchao.wechat.entity.message.call;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wenzchao.wechat.entity.message.mass.Text;

/**
 * 文本客服消息
 * 
 * @author Venz
 * 
 */
@XStreamAlias("xml")
public class TextCall extends Call {

	private Text text;

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

}
