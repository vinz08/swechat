package com.wenzchao.wechat.util;

/**
 * 事件推送类型
 * 
 * @author Venz
 *
 */
public enum EventPushType {

	SUBSCRIBE("subscribe"), // 事件类型：subscribe(订阅)
	UNSUBSCRIBE("unsubscribe"), // 事件类型：unsubscribe(取消订阅)
	LOCATION("LOCATION"), // 事件类型：LOCATION(上报地理位置事件)
	SCAN("SCAN"), // 事件类型：SCAN(二维码扫描事件)
	CLICK("CLICK"), // 事件类型：CLICK(自定义菜单点击事件)
	VIEW("VIEW"), // 事件类型：VIEW(自定义链接菜单访问事件)
	MASSSENDJOBFINISH("MASSSENDJOBFINISH"); // 事件类型：事件推送群发结果

	private String type;

	private EventPushType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
