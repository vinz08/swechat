package com.wenzchao.wechat.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.wenzchao.core.util.DataUtil;
import com.wenzchao.wechat.entity.message.in.BaseMessage;
import com.wenzchao.wechat.entity.message.in.EventMessage;
import com.wenzchao.wechat.entity.message.in.ImageMessage;
import com.wenzchao.wechat.entity.message.in.KeyEvent;
import com.wenzchao.wechat.entity.message.in.LinkMessage;
import com.wenzchao.wechat.entity.message.in.LocationEvent;
import com.wenzchao.wechat.entity.message.in.LocationMessage;
import com.wenzchao.wechat.entity.message.in.QRCodeEvent;
import com.wenzchao.wechat.entity.message.in.TextMessage;
import com.wenzchao.wechat.entity.message.in.VoiceMessage;
import com.wenzchao.wechat.util.MessageUtil;

@Service
public class MainService {

	private static Logger log = LoggerFactory.getLogger(MainService.class);

	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public String processRequest(HttpServletRequest request) {
		String respMessage = null;
		try {

			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			
			String openId = requestMap.get("FromUserName"); // 发送方帐号(OpenID)
			String originalId = requestMap.get("ToUserName"); // 公众帐号(原始ID)
			String msgType = requestMap.get("MsgType"); // 消息类型

			switch (msgType) {
			case MessageUtil.IN_MESSAGE_TYPE_TEXT:
				TextMessage textMessage = (TextMessage) DataUtil.strMap2Object(requestMap, TextMessage.class);
				
				break;
			case MessageUtil.IN_MESSAGE_TYPE_IMAGE:
				ImageMessage imageMessage = (ImageMessage) DataUtil.strMap2Object(requestMap, ImageMessage.class);
				
				break;
			case MessageUtil.IN_MESSAGE_TYPE_LOCATION:
				LocationMessage locationMessage = (LocationMessage) DataUtil.strMap2Object(requestMap, LocationMessage.class);	
				
				break;
			case MessageUtil.IN_MESSAGE_TYPE_LINK:
				LinkMessage linkMessage = (LinkMessage) DataUtil.strMap2Object(requestMap, LinkMessage.class);
				
				break;
			case MessageUtil.IN_MESSAGE_TYPE_VOICE:
				VoiceMessage voiceMessage = (VoiceMessage) DataUtil.strMap2Object(requestMap, VoiceMessage.class);
				
				break;
			case MessageUtil.IN_MESSAGE_TYPE_EVENT:
				String eventType = requestMap.get("Event"); // 事件类型
				switch (eventType) {
				case MessageUtil.EVENT_TYPE_UNSUBSCRIBE:
					
					break;
				case MessageUtil.EVENT_TYPE_SCAN:
					QRCodeEvent qrCodeEvent = (QRCodeEvent) DataUtil.strMap2Object(requestMap, QRCodeEvent.class);
					
					break;
				case MessageUtil.EVENT_TYPE_LOCATION:
					LocationEvent locationEvent = (LocationEvent) DataUtil.strMap2Object(requestMap, LocationEvent.class);
					
					break;
				default:
					KeyEvent keyEvent = (KeyEvent) DataUtil.strMap2Object(requestMap, KeyEvent.class);
					switch (eventType) {
					case MessageUtil.EVENT_TYPE_SUBSCRIBE:
						if (keyEvent.getEventKey().startsWith("qrscene_")) {
							QRCodeEvent codeEvent = (QRCodeEvent) DataUtil.strMap2Object(requestMap, QRCodeEvent.class);
							
						} else {
							
						}
						break;
					case MessageUtil.EVENT_TYPE_CLICK:
						
						break;
					case MessageUtil.EVENT_TYPE_VIEW:
						break;
					}
					break;
				}
				break;
			default:
				break;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return respMessage;
	}
}
