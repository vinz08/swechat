package com.wenzchao.wechat.entity.message.in;

/**
 * 键值事件信息
 * 
 * @author Venz
 * 
 */
public class KeyEvent extends EventMessage {

	private String EventKey; // 键值/URL/二维码扫描 未关注情况下qrscene_为前缀，后面为二维码的参数值；已关注情况下是一个32位无符号整数，即创建二维码时的二维码scene_id

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}

}
