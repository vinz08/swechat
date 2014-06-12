package com.wenzchao.swechat.web.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 核心控制器，负责验证、接收推送信息
 * 
 * @author Venz
 * 
 */
@Controller
public class MainController {

	@RequestMapping
	public String get() {
		return "";
	}
}
