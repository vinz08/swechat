package com.wenzchao.swechat.web.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wenzchao.swechat.util.SignUtil;

/**
 * 核心控制器，负责验证、接收推送信息
 * 
 * @author Venz
 * 
 */
@Controller
public class MainController {

	/**
	 * 确认请求来自微信服务器
	 * 
	 * @param signature 微信加密签名
	 * @param timestamp 时间戳
	 * @param nonce 随机数
	 * @param echostr 随机字符串
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public String get(String signature, String timestamp, String nonce, String echostr) {
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			return echostr;
		}
		return null;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String post() {

		return "";
	}
}
