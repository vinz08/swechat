package com.wenzchao.swechat.web.main;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wenzchao.swechat.service.MainService;
import com.wenzchao.swechat.util.SignUtil;

/**
 * 核心控制器，负责验证、接收推送信息
 * 
 * @author Venz
 * 
 */
@Controller
public class MainController {

	@Autowired
	private MainService mainService;
	
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
	@RequestMapping(method = RequestMethod.GET, value = "/main")
	public String get(@RequestParam String signature, @RequestParam String timestamp, @RequestParam String nonce, @RequestParam String echostr) {
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			return echostr;
		}
		return null;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/main", produces = "text/html;charset=UTF-8")
	public String post(HttpServletRequest request) {
		return mainService.processRequest(request);
	}
}
