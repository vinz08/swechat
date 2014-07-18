package com.wenzchao.wechat.entity.message.in;

/**
 * 事件消息
 * 
 * @author Venz
 * 
 */
public class EventMessage extends BaseMessage {

	private String Event; // 事件类型

	public String getEvent() {
		return Event;
	}

	public void setEvent(String event) {
		Event = event;
	}

}
