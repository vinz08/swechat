package com.wenzchao.swechat.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.protocol.Protocol;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class AutoSetUtil {

	private static final String URL = "http://vip8.sentree.com.cn/wechat/main";
	
	private static final String TOKEN = "sentree";
	
	static {
		Protocol myhttps = new Protocol("https", new MyProtocolSocketFactory(), 443);
		Protocol.registerProtocol("https", myhttps);
	}
	
	public static JSONObject autoSetAccount(String username, String passwd) throws Exception{
		// 创造httpclient实例
		HttpClient client = new HttpClient();
		client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY); // 设置cookie管理策略
		client.getParams().setParameter("http.protocol.single-cookie-header", true);
 
		Header ua = new Header("User-Agent", "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.172 Safari/537.22");
		
		PostMethod postMethod = new PostMethod();
		//模拟浏览器
		postMethod.setRequestHeader(ua);
		postMethod.addRequestHeader("Referer", "https://mp.weixin.qq.com/");
		//登录请求提交地址
		postMethod.setURI(new URI("https://mp.weixin.qq.com/cgi-bin/login?lang=zh_CN", true, "utf-8"));
 
		//构造请求参数
		NameValuePair[] params = new NameValuePair[] {
			new NameValuePair("username", username),
			new NameValuePair("pwd", passwd),
			//new NameValuePair("pwd", DigestUtils.md5Hex(passwd.getBytes())),
			new NameValuePair("f", "json"),
			new NameValuePair("imagecode", "")
		};
		postMethod.setQueryString(params);
		client.executeMethod(postMethod);
		JSONObject jsonObject = JSONObject.parseObject(postMethod.getResponseBodyAsString());
		int ret = jsonObject.getJSONObject("base_resp").getIntValue("ret");
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", ret);
		if(ret == 0){
			
			// 账户信息
			Map<String, String> accountInfo = new HashMap<String, String>();
			
			// 获取cookie
			Cookie[] cookies = client.getState().getCookies();
			String cookieStr = "";
			for (int i = 0; i < cookies.length; i++) {
				cookieStr += cookies[i].getName() + "=" + cookies[i].getValue();
				if (i != cookies.length -1) {
					cookieStr += ";";
				}
			}
			Header cookie = new Header("Cookie", cookieStr);
			// 获取token
			String token = jsonObject.getString("redirect_url").split("&")[2].split("=")[1];
			
			// 获取基本信息
			getBaseInfo(client, cookie, accountInfo, token);
			
			// 关闭编辑模式
			jsonObject = closeEditMod(client, cookie, token);
			ret = jsonObject.getJSONObject("base_resp").getIntValue("ret");
			if (ret == 0) {
				// 设置URL和TOKEN
				jsonObject = setURLandTOKEN(client, cookie, token);
				ret = jsonObject.getIntValue("ret");
				if (ret == 0) {
					// 打开开发模式
					jsonObject = openDevMod(client, cookie, token);
					ret = jsonObject.getJSONObject("base_resp").getIntValue("ret");
					if (ret == 0) {
						// 获取开发者信息
						getDevInfo(client, cookie, accountInfo, token);
					}
				}
			}
			
			result.put("accountInfo", JSON.toJSONString(accountInfo));
			
		} else {
			String errorMsg = "未知错误！";
			switch (Integer.valueOf(ret)) {
				case -1:
					errorMsg = "系统错误，请稍候再试";
					break;
				case -2:
					errorMsg = "帐号或密码错误";
					break;
				case -23:
					errorMsg = "您输入的帐号或者密码不正确，请重新输入";
					break;
				case -21:
					errorMsg = "不存在该帐户";
					break;
				case -7:
					errorMsg = "您目前处于访问受限状态";
					break;
				case -8:
					errorMsg = "请输入图中的验证码";
					break;
				case -27:
					errorMsg = "您输入的验证码不正确，请重新输入";
					break;
				case -26:
					errorMsg = "该公众会议号已经过期，无法再登录使用";
					break;
				case -25:
					errorMsg = "海外帐号";
					break;
				default:
					errorMsg = "未知的返回";
					break;
			}
			result.put("errorMsg", errorMsg);
		}
		return JSONObject.parseObject(JSON.toJSONString(result));
		
	}

	/**
	 * 获取开发者信息
	 * 
	 * @param client
	 * @param cookie
	 * @param accountInfo
	 * @param token
	 * @throws NullPointerException
	 * @throws HttpException
	 * @throws IOException
	 */
	private static void getDevInfo(HttpClient client, Header cookie, Map<String, String> accountInfo, String token) throws NullPointerException, HttpException, IOException{
		GetMethod getMethod = new GetMethod("https://mp.weixin.qq.com/cgi-bin/settingpage?t=setting/index&action=index&lang=zh_CN&token=" + token);
		getMethod.setRequestHeader(cookie);
		client.executeMethod(getMethod);
		String responseStr = getMethod.getResponseBodyAsString().toLowerCase();
		
		// 转换html
		Document doc = Jsoup.parse(responseStr);
		Elements div = doc.select("div.meta_content");
		
		for (int i = 0; i < div.size(); i++) {
			switch (i) {
			case 0:
				accountInfo.put("name", div.get(i).text().replace("已认证", "").trim());
				break;
			case 1:
				accountInfo.put("headImg", "https://mp.weixin.qq.com" + div.get(i).select("img").attr("src").trim());				
				break;
			case 3:
				accountInfo.put("baseId", div.get(i).select("span").html().trim());
				break;
			case 4:
				accountInfo.put("weixin", div.get(i).select("span").html().trim());
				break;
			case 5:
				accountInfo.put("type", div.get(i).html().trim());
				break;
			case 6:
				accountInfo.put("auth", div.get(i).html().trim());
				break;
			default:
				break;
			}
		}
	}
	
	/**
	 * 获取基本信息
	 * 
	 * @param client
	 * @param cookie
	 * @param accountInfo
	 * @param token
	 * @throws NullPointerException
	 * @throws HttpException
	 * @throws IOException
	 */
	private static void getBaseInfo(HttpClient client, Header cookie, Map<String, String> accountInfo, String token) throws NullPointerException, HttpException, IOException{
		GetMethod getMethod = new GetMethod("https://mp.weixin.qq.com/advanced/advanced?action=dev&t=advanced/dev&lang=zh_CN&token=" + token);
		getMethod.setRequestHeader(cookie);
		client.executeMethod(getMethod);
		String responseStr = getMethod.getResponseBodyAsString().toLowerCase();
		
		// 转换html
		Document doc = Jsoup.parse(responseStr);
		Elements div = doc.select("div.frm_vertical_pt");
		for (int i = 0; i < div.size(); i++) {
			switch (i) {
			case 0:
				accountInfo.put("appId", div.get(i).html().trim());
				break;
			case 1:
				accountInfo.put("appSecret", div.get(i).text().replace("重置", "").trim());				
				break;
			case 2:
				accountInfo.put("URL", div.get(i).html().trim());
				break;
			case 3:
				accountInfo.put("Token", div.get(i).html().trim());
				break;
			default:
				break;
			}
		}
	}
	
	/**
	 * 关闭编辑模式
	 * 
	 * @param client
	 * @param cookie
	 * @param token
	 * @return
	 * @throws IOException
	 */
	private static JSONObject closeEditMod(HttpClient client, Header cookie, String token) throws IOException {
 
		String url = "https://mp.weixin.qq.com/misc/skeyform?form=advancedswitchform&lang=zh_CN";
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		postMethod.setRequestHeader("Referer", "https://mp.weixin.qq.com/advanced/advanced?action=edit&t=advanced/edit&token=" + token + "&lang=zh_CN");
		postMethod.setRequestHeader(cookie);
 
		postMethod.setRequestBody(new NameValuePair[] {
			new NameValuePair("flag", "0"),
			new NameValuePair("token", token),
			new NameValuePair("type", "1")
		});
		client.executeMethod(postMethod);
		return JSONObject.parseObject(postMethod.getResponseBodyAsString());
	}
 
	/**
	 * 打开开发模式
	 * 
	 * @param client
	 * @param cookie
	 * @param token
	 * @return
	 * @throws IOException
	 */
	private static JSONObject openDevMod(HttpClient client, Header cookie, String token) throws IOException {
		String url = "https://mp.weixin.qq.com/misc/skeyform?form=advancedswitchform&lang=zh_CN";
 
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		postMethod.setRequestHeader("Referer", "https://mp.weixin.qq.com/advanced/advanced?action=dev&t=advanced/dev&lang=zh_CN&token=" + token);
		postMethod.setRequestHeader(cookie);
 
		postMethod.setRequestBody(new NameValuePair[] {
			new NameValuePair("flag", "1"),
			new NameValuePair("token", token),
			new NameValuePair("type", "2")
		});
		client.executeMethod(postMethod);
		return JSONObject.parseObject(postMethod.getResponseBodyAsString());
	}
 
	/**
	 * 设置url和token
	 * 
	 * @param client
	 * @param cookie
	 * @param token
	 * @return
	 * @throws IOException
	 */
	private static JSONObject setURLandTOKEN(HttpClient client, Header cookie, String token) throws IOException {
		String url = "https://mp.weixin.qq.com/advanced/callbackprofile?t=ajax-response&lang=zh_CN&token=" + token;
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader(cookie);
		postMethod.setRequestBody(new NameValuePair[] {
			new NameValuePair("url", URL),
			new NameValuePair("callback_token", TOKEN)
		});
		client.executeMethod(postMethod);
		return JSONObject.parseObject(postMethod.getResponseBodyAsString());
	}
}

