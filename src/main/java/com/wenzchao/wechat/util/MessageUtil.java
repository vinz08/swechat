package com.wenzchao.wechat.util;

import java.io.InputStream;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.wenzchao.wechat.entity.message.out.Article;
import com.wenzchao.wechat.entity.message.out.MusicMessage;
import com.wenzchao.wechat.entity.message.out.NewsMessage;
import com.wenzchao.wechat.entity.message.out.TextMessage;
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
		for (Element e : elementList) {
			map.put(e.getName(), e.getText());
		}

		// 释放资源
		inputStream.close();
		inputStream = null;

		return map;
	}
	
	public Object xml2Object(HttpServletRequest request) throws Exception {
		// 从request中取得输入流
		InputStream inputStream = request.getInputStream();
		return xstream.fromXML(inputStream);
	}

	public static void main(String[] args) {
		TextMessage message = new TextMessage();
		message.setContent("sdf");
		message.setCreateTime(new Date().getTime());
		message.setMsgType("text");
		message.setOpenId("dfg");
		message.setOriginalId("fdghh");
		xstream.alias("xml", message.getClass());
		String xml = xstream.toXML(message);
		System.out.println(xml);
		TextMessage textMessage = (TextMessage) xstream.fromXML(xml);
		System.out.println(textMessage.getOpenId());
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
		@Override
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 对所有xml节点的转换都增加CDATA标记
				boolean cdata = true;
				
				@Override
				public void startNode(String name, @SuppressWarnings("rawtypes") Class clazz) {
					super.startNode(name, clazz);
				}
				
				@Override
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
	
	static {
		xstream.autodetectAnnotations(true);
	}
}