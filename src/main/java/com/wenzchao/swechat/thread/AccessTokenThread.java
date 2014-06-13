package com.wenzchao.swechat.thread;

import org.apache.log4j.Logger;

import com.wenzchao.swechat.entity.base.AccessToken;
import com.wenzchao.swechat.util.WeixinUtil;

/**
 * 定时获取微信access_token的线程
 * 
 * @author Venz
 * 
 */
public class AccessTokenThread implements Runnable {
	private static Logger log = Logger.getLogger(AccessTokenThread.class);
	// 第三方用户唯一凭证
	public static String appid = "wxf7fe1d00188e7307";
	// 第三方用户唯一凭证密钥
	public static String appsecret = "d541e2d012aed341b111ec904342c7ee";

	public static AccessToken accessToken = null;

	public void run() {
		while (true) {
			try {
				accessToken = WeixinUtil.getAccessToken(appid, appsecret);
				if (null != accessToken) {
					log.info("获取access_token成功，有效时长" + accessToken.getExpiresIn() + "秒 token:" + accessToken.getAccessToken());
					// 休眠7000秒
					Thread.sleep((accessToken.getExpiresIn() - 200) * 1000);
				} else {
					// 如果access_token为null，60秒后再获取
					Thread.sleep(60 * 1000);
				}
			} catch (InterruptedException e) {
				try {
					Thread.sleep(60 * 1000);
				} catch (InterruptedException e1) {
					log.error(e1);
				}
				log.error(e);
			}
		}
	}
}
