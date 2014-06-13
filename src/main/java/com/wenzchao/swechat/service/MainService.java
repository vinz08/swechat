package com.wenzchao.swechat.service;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.wenzchao.swechat.entity.message.out.TextMessage;
import com.wenzchao.swechat.util.MessageUtil;

@Service
public class MainService {

	private static Logger log = Logger.getLogger(MainService.class);

	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public String processRequest(HttpServletRequest request) {
		String respMessage = null;
		try {
			// 默认返回的文本消息内容
			String respContent = "感谢您的关注！";

			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);

			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");

			log.info("user " + fromUserName + " send request.");

			// 回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.OUT_MESSAGE_TYPE_TEXT);

			if (msgType.equals(MessageUtil.IN_MESSAGE_TYPE_TEXT)) {
				
			} else if (msgType.equals(MessageUtil.IN_MESSAGE_TYPE_IMAGE)) {
				
			} else if (msgType.equals(MessageUtil.IN_MESSAGE_TYPE_LOCATION)) {
				
			} else if (msgType.equals(MessageUtil.IN_MESSAGE_TYPE_LINK)) {
				
			} else if (msgType.equals(MessageUtil.IN_MESSAGE_TYPE_VOICE)) {
				
			} else if (msgType.equals(MessageUtil.IN_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					respContent = "谢谢您的关注！";
				} else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) { // 取消订阅
					// 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				} else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) { // 自定义菜单点击事件
					// 事件KEY值，与创建自定义菜单时指定的KEY值对应
					String eventKey = requestMap.get("EventKey");

					if (eventKey.equals("02")) {
						respContent = "即将开放，敬请期待！";
					}
				} else if (eventType.equals(MessageUtil.EVENT_TYPE_VIEW)) { // 自定义链接菜单访问事件

				}
			}

			textMessage.setContent(respContent);
			respMessage = MessageUtil.textMessageToXml(textMessage);
			log.info(respMessage);
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return respMessage;
	}
}
