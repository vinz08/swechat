package com.wenzchao.swechat.util;

import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.wenzchao.swechat.entity.message.out.Article;
import com.wenzchao.swechat.entity.message.out.MusicMessage;
import com.wenzchao.swechat.entity.message.out.NewsMessage;
import com.wenzchao.swechat.entity.message.out.TextMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 消息工具类
 * 
 * @author Venz
 *
 */
public class MessageUtil {

	public static final String OUT_MESSAGE_TYPE_TEXT = "text"; // 发送消息类型：文本

	public static final String OUT_MESSAGE_TYPE_IMAGE = "image"; // 发送消息类型：图片
	
	public static final String OUT_MESSAGE_TYPE_VOICE = "voice"; // 发送消息类型：声音
	
	public static final String OUT_MESSAGE_TYPE_VIDEO = "video"; // 发送消息类型：视频
	
	public static final String OUT_MESSAGE_TYPE_MUSIC = "music"; // 发送消息类型：音乐

	public static final String OUT_MESSAGE_TYPE_NEWS = "news"; // 发送消息类型：图文

	public static final String IN_MESSAGE_TYPE_TEXT = "text"; // 接收消息类型：文本

	public static final String IN_MESSAGE_TYPE_IMAGE = "image"; // 接收消息类型：图片

	public static final String IN_MESSAGE_TYPE_VOICE = "voice"; // 接收消息类型：音频
	
	public static final String IN_MESSAGE_TYPE_VIDEO = "video"; // 接收消息类型：视频

	public static final String IN_MESSAGE_TYPE_LOCATION = "location"; // 接收消息类型：地理位置
	
	public static final String IN_MESSAGE_TYPE_LINK = "link"; // 接收消息类型：链接

	public static final String IN_MESSAGE_TYPE_EVENT = "event"; // 接收消息类型：推送

	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe"; // 事件类型：subscribe(订阅)

	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe"; // 事件类型：unsubscribe(取消订阅)

	public static final String EVENT_TYPE_CLICK = "CLICK"; // 事件类型：CLICK(自定义菜单点击事件)
	
	public static final String EVENT_TYPE_VIEW = "VIEW"; // 事件类型：VIEW(自定义链接菜单访问事件)

	/**
	 * 解析微信发来的请求（XML）
	 * 
	 * @param INuest
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();

		// 从request中取得输入流
		InputStream inputStream = request.getInputStream();
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();

		// 遍历所有子节点
		for (Element e : elementList)
			map.put(e.getName(), e.getText());

		// 释放资源
		inputStream.close();
		inputStream = null;

		return map;
	}

	/**
	 * 文本消息对象转换成xml
	 * 
	 * @param textMessage
	 * @return xml
	 */
	public static String textMessage2Xml(TextMessage textMessage) {
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	/**
	 * 音乐消息对象转换成xml
	 * 
	 * @param musicMessage
	 * @return xml
	 */
	public static String musicMessage2Xml(MusicMessage musicMessage) {
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}

	/**
	 * 图文消息对象转换成xml
	 * 
	 * @param newsMessage
	 * @return xml
	 */
	public static String newsMessage2Xml(NewsMessage newsMessage) {
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(newsMessage);
	}

	/**
	 * 扩展xstream，使其支持CDATA块
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 对所有xml节点的转换都增加CDATA标记
				boolean cdata = true;

				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});
}