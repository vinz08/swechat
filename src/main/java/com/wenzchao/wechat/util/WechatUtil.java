package com.wenzchao.wechat.util;

import java.io.File;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;

/**
 * 公众平台通用接口工具类
 * 
 * @author Venz
 */
public class WechatUtil {
	
	/**
	 * 获取AccessToken
	 * 
	 * @param appId
	 * @param appSecret
	 * @return
	 */
	public static JSONObject getAccessToken(String appId, String appSecret) {
		NameValuePair params[] = new NameValuePair[] {
			new BasicNameValuePair("grant_type", "client_credential"),
			new BasicNameValuePair("appid", appId),
			new BasicNameValuePair("secret", appSecret)
		};
		return HttpUtil.getRequest(WechatRequest.GET_ACCESSTOKEN, params);
	}

	/**
	 * 获取关注用户
	 * 
	 * @param accessToken
	 * @param nextOpenId
	 * @return
	 */
	public static JSONObject getUserList(String accessToken, String nextOpenId) {
		NameValuePair params[] = new NameValuePair[] {
			new BasicNameValuePair("access_token", accessToken),
			new BasicNameValuePair("next_openid", nextOpenId)
		};
		return HttpUtil.getRequest(WechatRequest.GET_USERS, params);
	}

	/**
	 * 获取用户信息
	 * 
	 * @param accessToken
	 * @param openId
	 * @return
	 */
	public static JSONObject getUserInfo(String accessToken, String openId) {
		NameValuePair params[] = new NameValuePair[] {
			new BasicNameValuePair("access_token", accessToken),
			new BasicNameValuePair("openid", openId)
		};
		return HttpUtil.getRequest(WechatRequest.GET_USER_INFO, params);
	}
	
	/**
	 * 发送客服信息
	 * 
	 * @param accessToken
	 * @param jsonMessage
	 * @return
	 */
	public static JSONObject sendCustomMessage(String accessToken, String jsonMessage) {
		NameValuePair params[] = new NameValuePair[] {
			new BasicNameValuePair("access_token", accessToken)
		};
		return HttpUtil.postRequest(WechatRequest.SEND_CUSTOM_MESSAGE, params, jsonMessage);
	}

	/**
	 * 群发信息 by openid
	 * 
	 * @param accessToken
	 * @param jsonMessage
	 * @return
	 */
	public static JSONObject sendMassMessageByOpenId(String accessToken, String jsonMessage){
		NameValuePair params[] = new NameValuePair[] {
			new BasicNameValuePair("access_token", accessToken)
		};
		return HttpUtil.postRequest(WechatRequest.SEND_MASS_MESSAGE_BY_OPENID, params, jsonMessage);
	}
	
	/**
	 * 群发信息 by groupid
	 * 
	 * @param accessToken
	 * @param jsonMessage
	 * @return
	 */
	public static JSONObject sendMassMessageByGroupId(String accessToken, String jsonMessage){
		NameValuePair params[] = new NameValuePair[] {
			new BasicNameValuePair("access_token", accessToken)
		};
		return HttpUtil.postRequest(WechatRequest.SEND_MASS_MESSAGE_BY_GROUPID, params, jsonMessage);
	}
	
	/**
	 * 创建菜单
	 * 
	 * @param accessToken
	 * @param jsonMenu
	 * @return
	 */
	public static JSONObject createMenu(String accessToken, String jsonMenu) {
		NameValuePair params[] = new NameValuePair[] {
			new BasicNameValuePair("access_token", accessToken)
		};
		return HttpUtil.postRequest(WechatRequest.CREATE_MENU, params, jsonMenu);
	}
	
	/**
	 * 获取菜单
	 * 
	 * @param accessToken
	 * @return
	 */
	public static JSONObject getMenu(String accessToken) {
		NameValuePair params[] = new NameValuePair[] {
			new BasicNameValuePair("access_token", accessToken)
		};
		return HttpUtil.getRequest(WechatRequest.GET_MENU, params);
	}
	
	/**
	 * 删除菜单
	 * 
	 * @param accessToken
	 * @return
	 */
	public static JSONObject deleteMenu(String accessToken) {
		NameValuePair params[] = new NameValuePair[] {
			new BasicNameValuePair("access_token", accessToken)
		};
		return HttpUtil.getRequest(WechatRequest.DELETE_MENU, params);
	}
	
	/**
	 * 上传图文信息
	 * 
	 * @param accessToken
	 * @param jsonNews
	 * @return
	 */
	public static JSONObject uploadNews(String accessToken, String jsonNews) {
		NameValuePair params[] = new NameValuePair[] {
			new BasicNameValuePair("access_token", accessToken)
		};
		return HttpUtil.postRequest(WechatRequest.UPLOAD_NEWS_MEDIA, params, jsonNews);
	}
	
	/**
	 * 上传文件
	 * 
	 * @param accessToken
	 * @param type
	 * @param filePath
	 * @return
	 */
	public static JSONObject uploadMedia(String accessToken, String type, String filePath) {
		
		NameValuePair params[] = new NameValuePair[] {
			new BasicNameValuePair("access_token", accessToken),
			new BasicNameValuePair("type", type)
		};
		File file = new File(filePath);
		HttpEntity httpEntity = MultipartEntityBuilder.create().addBinaryBody("body", file).build();
		return HttpUtil.postRequest(WechatRequest.UPLOAD_MEDIA, params, httpEntity);
	}
	
	/**
	 * 获取OAuth2.0AccessToken
	 * 
	 * @param appId
	 * @param appSecret
	 * @param code
	 * @return
	 */
	public static JSONObject oAuth2AccessToken(String appId, String appSecret, String code) {
		NameValuePair params[] = new NameValuePair[] {
			new BasicNameValuePair("appid", appId),
			new BasicNameValuePair("secret", appSecret),
			new BasicNameValuePair("code", code),
			new BasicNameValuePair("grant_type", "authorization_code")
		};
		return HttpUtil.getRequest(WechatRequest.OAUTH2_ACCESSTOKEN, params);
	}
	
	/**
	 * 刷新OAuth2.0AccessToken
	 * 
	 * @param appId
	 * @param refreshToken
	 * @return
	 */
	public static JSONObject refreshOAuth2AccessToken(String appId, String refreshToken) {
		NameValuePair params[] = new NameValuePair[] {
			new BasicNameValuePair("appid", appId),
			new BasicNameValuePair("refresh_token", refreshToken),
			new BasicNameValuePair("grant_type", "refresh_token")
		};
		return HttpUtil.getRequest(WechatRequest.REFRESH_OAUTH2_ACCESSTOKEN, params);
	}

	/**
	 * 根据OAuth2.0AccessToken获取用户信息
	 * 
	 * @param accessToken
	 * @param openId
	 * @return
	 */
	public static JSONObject getUserInfoByOAuthAccessToken(String accessToken, String openId) {
		NameValuePair params[] = new NameValuePair[] {
			new BasicNameValuePair("access_token", accessToken),
			new BasicNameValuePair("openid", openId),
			new BasicNameValuePair("lang", "zh_CN")
		};
		return HttpUtil.getRequest(WechatRequest.GET_USER_INFO_BY_OAUTH2_ACCESS_TOKEN, params);
	}

}