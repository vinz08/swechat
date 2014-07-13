package com.wenzchao.wechat.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.wenzchao.wechat.entity.base.AccessToken;
import com.wenzchao.wechat.entity.base.OAuth;
import com.wenzchao.wechat.entity.menu.Menu;
import com.wenzchao.wechat.entity.user.UserInfo;

/**
 * 公众平台通用接口工具类
 * 
 * @author Venz
 */
public class WechatUtil {
	private static Logger log = Logger.getLogger(WechatUtil.class);

	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.parseObject(buffer.toString());
		} catch (ConnectException ce) {
			log.error("Weixin server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:", e);
		}
		return jsonObject;
	}

	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	/**
	 * 获取access_token
	 * 
	 * @param appid 凭证
	 * @param appsecret 密钥
	 * @return
	 */
	public static AccessToken getAccessToken(String appid, String appsecret) {
		AccessToken accessToken = null;

		String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setAccessToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getIntValue("expires_in"));
			} catch (JSONException e) {
				accessToken = null;
				// 获取token失败
				log.error("获取token失败 errcode:" + jsonObject.getIntValue("errcode") + " errmsg:" + jsonObject.getString("errmsg"));
			}
		}
		return accessToken;
	}

	// 菜单创建（POST） 限100（次/天）
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	/**
	 * 创建菜单
	 * 
	 * @param menu 菜单实例
	 * @param accessToken 有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	public static int createMenu(Menu menu, String accessToken) {
		int result = 0;

		// 拼装创建菜单的url
		String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
		// 将菜单对象转换成json字符串
		String jsonMenu = JSONObject.toJSONString(menu);
		// 调用接口创建菜单
		JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);

		if (null != jsonObject) {
			if (0 != jsonObject.getIntValue("errcode")) {
				result = jsonObject.getIntValue("errcode");
				log.error("创建菜单失败 errcode:" + jsonObject.getIntValue("errcode") + " errmsg:" + jsonObject.getString("errmsg"));
			}
		}

		return result;
	}
	
	// 菜单查询
	public static String menu_query_url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	
	/**
	 * 菜单查询
	 * 
	 * @param accessToken
	 * @return
	 */
	public static Menu queryMenu(String accessToken) {
		Menu menu = null;

		// 拼装查询菜单的url
		String url = menu_query_url.replace("ACCESS_TOKEN", accessToken);
		// 调用接口查询菜单
		JSONObject jsonObject = httpRequest(url, "GET", null);

		if (null != jsonObject) {
			try {
				menu = new Menu();
				jsonObject.get("menu");
			} catch (JSONException e) {
				menu = null;
				log.error("查询菜单失败 errcode:" + jsonObject.getIntValue("errcode") + " errmsg:" + jsonObject.getString("errmsg"));
			}
		}
		
		return menu;
	}
	
	// 菜单删除
	public static String menu_delete_url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	
	/**
	 * 删除菜单
	 * 
	 * @param accessToken
	 * @return
	 */
	public static int deleteMenu(String accessToken) {
		int result = 0;

		// 拼装删除菜单的url
		String url = menu_delete_url.replace("ACCESS_TOKEN", accessToken);
		// 调用接口删除菜单
		JSONObject jsonObject = httpRequest(url, "GET", null);

		if (null != jsonObject) {
			if (0 != jsonObject.getIntValue("errcode")) {
				result = jsonObject.getIntValue("errcode");
				log.error("删除菜单失败 errcode:" + jsonObject.getIntValue("errcode") + " errmsg:" + jsonObject.getString("errmsg"));
			}
		}

		return result;
	}
	
	// 通过code换取网页授权access_token
	public static String code_access_token_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=APPSECRET&code=CODE&grant_type=authorization_code";

	/**
	 * OAuth认证
	 * 
	 * @param appid 凭证
	 * @param appsecret 密钥
	 * @param code
	 * @return
	 */
	public static OAuth getOAuthAccessToken(String appid, String appsecret, String code) {
		OAuth oAuth = null;

		// 拼装url
		String url = code_access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret).replace("CODE", code);
		// 调用接口
		JSONObject jsonObject = httpRequest(url, "POST", null);

		if (null != jsonObject) {
			try {
				oAuth = new OAuth();
				oAuth.setAccessToken(jsonObject.getString("access_token"));
				oAuth.setExpiresIn(jsonObject.getIntValue("expires_in"));
				oAuth.setRefreshToken(jsonObject.getString("refresh_token"));
				oAuth.setOpenid(jsonObject.getString("openid"));
				oAuth.setScope(jsonObject.getString("scope"));
			} catch (JSONException e) {
				oAuth = null;
				// OAuth认证失败
				log.error("OAuth认证失败 errcode:" + jsonObject.getIntValue("errcode") + " errmsg:" + jsonObject.getString("errmsg"));
			}
		}
		return oAuth;
	}

	// 刷新access_token（如果需要）
	public static String refresh_access_token = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";

	/**
	 * 刷新OAuth认证
	 * 
	 * @param appid
	 * @param refreshToken
	 * @return
	 */
	public static OAuth refreshOAuthAccessToken(String appid, String refreshToken) {
		OAuth oAuth = null;

		// 拼装url
		String url = code_access_token_url.replace("APPID", appid).replace("REFRESH_TOKEN", refreshToken);
		// 调用接口
		JSONObject jsonObject = httpRequest(url, "POST", null);
		if (null != jsonObject) {
			try {
				oAuth = new OAuth();
				oAuth.setAccessToken(jsonObject.getString("access_token"));
				oAuth.setExpiresIn(jsonObject.getIntValue("expires_in"));
				oAuth.setRefreshToken(jsonObject.getString("refresh_token"));
				oAuth.setOpenid(jsonObject.getString("openid"));
				oAuth.setScope(jsonObject.getString("scope"));
			} catch (JSONException e) {
				oAuth = null;
				// OAuth认证刷新失败
				log.error("刷新OAuth认证失败 errcode:" + jsonObject.getIntValue("errcode") + " errmsg:" + jsonObject.getString("errmsg"));
			}
		}
		return oAuth;
	}

	// 全局access_token拉取用户信息
	public static String get_userinfo_normal = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
	
	/**
	 * 拉取用户信息
	 * 
	 * @param accessToken
	 * @param openId
	 * @return
	 */
	public static UserInfo getUserInfoNormal(String accessToken, String openId) {
		UserInfo userInfo = null;
		// 拼装url
		String url = get_userinfo_normal.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		// 调用接口
		JSONObject jsonObject = httpRequest(url, "GET", null);
		if (null != jsonObject) {
			try {
				userInfo = new UserInfo();
				userInfo.setOpenid(jsonObject.getString("openid"));
				userInfo.setNickname(jsonObject.getString("nickname"));
				userInfo.setCity(jsonObject.getString("city"));
				userInfo.setProvince(jsonObject.getString("province"));
				userInfo.setCountry(jsonObject.getString("country"));
				userInfo.setSex(jsonObject.getIntValue("sex"));
				userInfo.setHeadimgurl(jsonObject.getString("headimgurl"));
			} catch (JSONException e) {
				userInfo = null;
				// 拉取用户信息失败
				log.error("拉取用户信息失败 errcode:" + jsonObject.getIntValue("errcode") + " errmsg:" + jsonObject.getString("errmsg"));
			}
		}
		return userInfo;
	}
	
	// 拉取用户信息
	public static String get_userinfo = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	/**
	 * 拉取用户信息
	 * 
	 * @param accessToken
	 * @param openId
	 * @return
	 */
	public static UserInfo getUserInfo(String accessToken, String openId) {
		UserInfo userInfo = null;
		// 拼装url
		String url = get_userinfo.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		// 调用接口
		JSONObject jsonObject = httpRequest(url, "GET", null);
		if (null != jsonObject) {
			try {
				userInfo = new UserInfo();
				userInfo.setOpenid(jsonObject.getString("openid"));
				userInfo.setNickname(jsonObject.getString("nickname"));
				userInfo.setCity(jsonObject.getString("city"));
				userInfo.setProvince(jsonObject.getString("province"));
				userInfo.setCountry(jsonObject.getString("country"));
				userInfo.setSex(jsonObject.getIntValue("sex"));
				userInfo.setHeadimgurl(jsonObject.getString("headimgurl"));
			} catch (JSONException e) {
				userInfo = null;
				// 拉取用户信息失败
				log.error("拉取用户信息失败 errcode:" + jsonObject.getIntValue("errcode") + " errmsg:" + jsonObject.getString("errmsg"));
			}
		}
		return userInfo;
	}

}