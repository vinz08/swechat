package com.wenzchao.swechat.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;
import com.wenzchao.swechat.thread.AccessTokenThread;

/**
 * 系统初始化监听
 * 
 * @author Venz
 *
 */
public class SystemInitListener implements ServletContextListener {

	private static Logger log = Logger.getLogger(SystemInitListener.class);
	
	public void contextInitialized(ServletContextEvent sce) {
		log.info("weixin api appid:" + AccessTokenThread.appid);
        log.info("weixin api appsecret:" + AccessTokenThread.appsecret);

        // 未配置appid、appsecret时给出提示
        if ("".equals(AccessTokenThread.appid) || "".equals(AccessTokenThread.appsecret))
        {
            log.error("appid and appsecret configuration error, please check carefully.");
        }
        else
        {
            // 启动定时获取access_token的线程
            new Thread(new AccessTokenThread()).start();
        }
	}

	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
