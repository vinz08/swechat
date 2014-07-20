package com.wenzchao.wechat.util;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * 请求工具类
 * 
 * @author Venz
 *
 */
public class HttpUtil {

	private static Logger log = LoggerFactory.getLogger(HttpUtil.class);

	public static final ResponseHandler<String> UTF8_CONTENT_HANDLER = new ResponseHandler<String>() {
		@Override
		public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
			final StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() >= 300) {
				throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
			}

			final HttpEntity entity = response.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity, "UTF-8");
			}

			return StringUtils.EMPTY;
		}
	};

	private static URI buildURI(String requestUrl, NameValuePair params[]) {
		try {
			return new URIBuilder(requestUrl).setParameters(Arrays.asList(params)).build();
		} catch (Exception e) {
			log.error("build URI failed:\n url={} \n params={}", requestUrl, params);
			return null;
		}
	}

	public static JSONObject getRequest(WechatRequest request, NameValuePair params[]) {
		JSONObject jsonObject = null;
		String requestUrl = request.getUrl();
        String requestName = request.getName();
		URI uri = buildURI(requestUrl, params);
		try {
			String responseBody = Request.Get(uri).execute().handleResponse(HttpUtil.UTF8_CONTENT_HANDLER);
			jsonObject = JSONObject.parseObject(responseBody);
		} catch (Exception e) {
			log.error("GetRequest {} failed:\n url={} \n params={}", requestName, uri, params);
		}
		return jsonObject;
	}
	
	public static JSONObject postRequest(WechatRequest request, NameValuePair params[], String jsonBody) {
		JSONObject jsonObject = null;
		String requestUrl = request.getUrl();
        String requestName = request.getName();
		URI uri = buildURI(requestUrl, params);
		try {
			String responseBody = Request.Post(uri).bodyString(jsonBody, ContentType.create("text/html", Consts.UTF_8))
					.execute().handleResponse(HttpUtil.UTF8_CONTENT_HANDLER);
			jsonObject = JSONObject.parseObject(responseBody);
		} catch (Exception e) {
			log.error("PostRequest {} failed:\n url={} \n body={}", requestName, uri, jsonBody);
		}
		return jsonObject;
	}
	
	public static JSONObject postRequest(WechatRequest request, NameValuePair params[], HttpEntity httpEntity) {
		JSONObject jsonObject = null;
		String requestUrl = request.getUrl();
        String requestName = request.getName();
		URI uri = buildURI(requestUrl, params);
		try {
			String responseBody = Request.Post(uri).body(httpEntity).execute().handleResponse(HttpUtil.UTF8_CONTENT_HANDLER);
			jsonObject = JSONObject.parseObject(responseBody);
		} catch (Exception e) {
			log.error("PostRequest {} failed:\n url={}", requestName, uri);
		}
		return jsonObject;
	}
}
