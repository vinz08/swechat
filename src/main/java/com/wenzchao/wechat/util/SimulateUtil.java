package com.wenzchao.wechat.util;

import java.util.Arrays;

import javax.net.ssl.SSLContext;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSONObject;

/**
 * 模拟登陆并设置开发模式工具类
 * 
 * @author Venz
 *
 */
public class SimulateUtil {

	private static final String URL = "http://vip8.sentree.com.cn/wechat/main";
	private static final String TOKEN = "sentree";
	
	public static void main(String[] args) throws Exception {
		JSONObject jsonObject = simulateRobot("wenzchao@gmail.com", "Wenz10512");
		System.out.println(jsonObject.toJSONString());
	}
	
	public static JSONObject simulateRobot(String username, String passwd) throws Exception{
		JSONObject result = new JSONObject();
		//构造请求参数
		NameValuePair params[] = new NameValuePair[] {
			new BasicNameValuePair("username", username),
			//new BasicNameValuePair("pwd", passwd),
			new BasicNameValuePair("pwd", DigestUtils.md5Hex(passwd.getBytes())),
			new BasicNameValuePair("f", "json"),
			new BasicNameValuePair("imagecode", "")
		};
		
        SSLContext sslcontext = SSLContexts.custom()
                .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                .build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		HttpPost httpPost = new HttpPost("https://mp.weixin.qq.com/cgi-bin/login?lang=zh_CN");
		httpPost.addHeader("Referer", "https://mp.weixin.qq.com/");
		httpPost.setEntity(new UrlEncodedFormEntity(Arrays.asList(params), Consts.UTF_8));
		CloseableHttpResponse response = httpClient.execute(httpPost);
		
		JSONObject jsonObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
		int ret = jsonObject.getJSONObject("base_resp").getIntValue("ret");
		result.put("code", ret);
		if(ret == 0){
			// 获取token
			String token = jsonObject.getString("redirect_url").split("&")[2].split("=")[1];
			Header headers[] = response.getAllHeaders();
			String cookieStr = "";
			for (int i = 0; i < headers.length; i++) {
				if(headers[i].getName().equals("Set-Cookie")){
					cookieStr += headers[i].getValue().split(";")[0];
					if (i != headers.length -1) {
						cookieStr += ";";
					}
				}
			}
			Header cookie = new BasicHeader("Cookie", cookieStr);
			// 获取基本信息
			jsonObject = getBaseInfo(httpClient, cookie, token);
			result.putAll(jsonObject);
			// 关闭编辑模式
			jsonObject = closeEditMod(httpClient, cookie, token);
			ret = jsonObject.getJSONObject("base_resp").getIntValue("ret");
			if (ret == 0) {
				// 设置URL和TOKEN
				jsonObject = setURLandTOKEN(httpClient, cookie, token);
				ret = jsonObject.getIntValue("ret");
				if (ret == 0) {
					// 打开开发模式
					jsonObject = openDevMod(httpClient, cookie, token);
					ret = jsonObject.getJSONObject("base_resp").getIntValue("ret");
					if (ret == 0) {
						// 获取开发者信息
						jsonObject = getDevInfo(httpClient, cookie, token);
						result.putAll(jsonObject);
					}
				}
			}
		} else {
			String errorMsg = "未知错误";
			switch (ret) {
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
		return result;
		
	}

	/**
	 * 获取基本信息
	 * 
	 * @param client
	 * @param cookie
	 * @param accountInfo
	 * @param token
	 * @throws Exception
	 */
	private static JSONObject getBaseInfo(CloseableHttpClient httpClient, Header cookie, String token) throws Exception{
		HttpGet httpGet = new HttpGet("https://mp.weixin.qq.com/advanced/advanced?action=dev&t=advanced/dev&lang=zh_CN&token=" + token);
		httpGet.addHeader(cookie);
		httpGet.addHeader("Referer", "https://mp.weixin.qq.com/");
		CloseableHttpResponse response = httpClient.execute(httpGet);
		String responseStr = EntityUtils.toString(response.getEntity());
		
		JSONObject jsonObject = new JSONObject();
		
		// 转换html
		Document doc = Jsoup.parse(responseStr);
		Elements div = doc.select("div.frm_vertical_pt");
		for (int i = 0; i < div.size(); i++) {
			switch (i) {
			case 0:
				jsonObject.put("appId", div.get(i).html().trim());
				break;
			case 1:
				jsonObject.put("appSecret", div.get(i).text().replace("重置", "").trim());				
				break;
			case 2:
				jsonObject.put("URL", div.get(i).html().trim());
				break;
			case 3:
				jsonObject.put("Token", div.get(i).html().trim());
				break;
			default:
				break;
			}
		}
		return jsonObject;
	}
	
	/**
	 * 获取开发者信息
	 * 
	 * @param client
	 * @param cookie
	 * @param accountInfo
	 * @param token
	 * @throws Exception 
	 */
	private static JSONObject getDevInfo(CloseableHttpClient httpClient, Header cookie, String token) throws Exception {
		HttpGet httpGet = new HttpGet("https://mp.weixin.qq.com/cgi-bin/settingpage?t=setting/index&action=index&lang=zh_CN&token=" + token);
		httpGet.addHeader(cookie);
		httpGet.addHeader("Referer", "https://mp.weixin.qq.com/");
		CloseableHttpResponse response = httpClient.execute(httpGet);
		String responseStr = EntityUtils.toString(response.getEntity());
		
		JSONObject jsonObject = new JSONObject();
		
		// 转换html
		Document doc = Jsoup.parse(responseStr);
		Elements div = doc.select("div.meta_content");
		for (int i = 0; i < div.size(); i++) {
			switch (i) {
			case 0:
				jsonObject.put("name", div.get(i).text().replace("已认证", "").trim());
				break;
			case 1:
				jsonObject.put("headImg", "https://mp.weixin.qq.com" + div.get(i).select("img").attr("src").trim());				
				break;
			case 3:
				jsonObject.put("originalId", div.get(i).select("span").html().trim());
				break;
			case 4:
				jsonObject.put("weixin", div.get(i).select("span").html().trim());
				break;
			case 5:
				jsonObject.put("type", div.get(i).html().trim());
				break;
			case 6:
				jsonObject.put("auth", div.get(i).html().trim());
				break;
			default:
				break;
			}
		}
		return jsonObject;
	}
	
	/**
	 * 关闭编辑模式
	 * 
	 * @param client
	 * @param cookie
	 * @param token
	 * @return
	 * @throws Exception
	 */
	private static JSONObject closeEditMod(CloseableHttpClient httpClient, Header cookie, String token) throws Exception {
		String url = "https://mp.weixin.qq.com/misc/skeyform?form=advancedswitchform&lang=zh_CN";
		//postMethod.setRequestHeader("Referer", "https://mp.weixin.qq.com/advanced/advanced?action=edit&t=advanced/edit&token=" + token + "&lang=zh_CN");
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader(cookie);
		httpPost.addHeader("Referer", "https://mp.weixin.qq.com/");
		httpPost.setEntity(new UrlEncodedFormEntity(Arrays.asList(new NameValuePair[] {
			new BasicNameValuePair("flag", "0"),
			new BasicNameValuePair("token", token),
			new BasicNameValuePair("type", "1")
		})));
		CloseableHttpResponse response = httpClient.execute(httpPost);
		return JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
	}
 
	/**
	 * 打开开发模式
	 * 
	 * @param client
	 * @param cookie
	 * @param token
	 * @return
	 * @throws Exception
	 */
	private static JSONObject openDevMod(CloseableHttpClient httpClient, Header cookie, String token) throws Exception {
		String url = "https://mp.weixin.qq.com/misc/skeyform?form=advancedswitchform&lang=zh_CN";
		//postMethod.setRequestHeader("Referer", "https://mp.weixin.qq.com/advanced/advanced?action=dev&t=advanced/dev&lang=zh_CN&token=" + token);
 
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader(cookie);
		httpPost.addHeader("Referer", "https://mp.weixin.qq.com/");
		httpPost.setEntity(new UrlEncodedFormEntity(Arrays.asList(new NameValuePair[] {
			new BasicNameValuePair("flag", "1"),
			new BasicNameValuePair("token", token),
			new BasicNameValuePair("type", "2")
		})));
		CloseableHttpResponse response = httpClient.execute(httpPost);
		return JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
	}
 
	/**
	 * 设置url和token
	 * 
	 * @param client
	 * @param cookie
	 * @param token
	 * @return
	 * @throws Exception
	 */
	private static JSONObject setURLandTOKEN(CloseableHttpClient httpClient, Header cookie, String token) throws Exception {
		String url = "https://mp.weixin.qq.com/advanced/callbackprofile?t=ajax-response&lang=zh_CN&token=" + token;
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader(cookie);
		httpPost.addHeader("Referer", "https://mp.weixin.qq.com/");
		httpPost.setEntity(new UrlEncodedFormEntity(Arrays.asList(new NameValuePair[] {
			new BasicNameValuePair("url", URL),
			new BasicNameValuePair("callback_token", TOKEN)
		})));
		CloseableHttpResponse response = httpClient.execute(httpPost);
		return JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
	}
}