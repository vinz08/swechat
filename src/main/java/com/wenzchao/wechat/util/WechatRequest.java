package com.wenzchao.wechat.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 公众平台API
 * 
 * @author Venz
 *
 */
public enum WechatRequest {
	
	GET_ACCESSTOKEN("https://api.weixin.qq.com/cgi-bin/token"),

    SEND_CUSTOM_MESSAGE("https://api.weixin.qq.com/cgi-bin/message/custom/send"),
    SEND_MASS_MESSAGE_BY_GROUPID("https://api.weixin.qq.com/cgi-bin/message/mass/sendall"),
    SEND_MASS_MESSAGE_BY_OPENID("https://api.weixin.qq.com/cgi-bin/message/mass/send"),
    DELETE_MASS_MESSAGE("https://api.weixin.qq.com//cgi-bin/message/mass/delete"),

    UPLOAD_MEDIA("http://file.api.weixin.qq.com/cgi-bin/media/upload"),
    GET_MEDIA("http://file.api.weixin.qq.com/cgi-bin/media/get"),
    UPLOAD_NEWS_MEDIA("https://api.weixin.qq.com/cgi-bin/media/uploadnews"),

    CREATE_MENU("https://api.weixin.qq.com/cgi-bin/menu/create"),
    GET_MENU("https://api.weixin.qq.com/cgi-bin/menu/get"),
    DELETE_MENU("https://api.weixin.qq.com/cgi-bin/menu/delete"),

    GET_GROUPS("https://api.weixin.qq.com/cgi-bin/groups/get"),
    CREATE_GROUP("https://api.weixin.qq.com/cgi-bin/groups/create"),
    GET_GROUP_BY_OPENID("https://api.weixin.qq.com/cgi-bin/groups/getid"),
    UPDATE_GROUP("https://api.weixin.qq.com/cgi-bin/groups/update"),
    MOVE_MEMBER_GROUP("https://api.weixin.qq.com/cgi-bin/groups/members/update"),

    GET_USER_INFO("https://api.weixin.qq.com/cgi-bin/user/info"),
    GET_USERS("https://api.weixin.qq.com/cgi-bin/user/get"),

    OAUTH2_ACCESSTOKEN("https://api.weixin.qq.com/sns/oauth2/access_token"),
    REFRESH_OAUTH2_ACCESSTOKEN("https://api.weixin.qq.com/sns/oauth2/refresh_token"),
    GET_USER_INFO_BY_OAUTH2_ACCESS_TOKEN("https://api.weixin.qq.com/sns/userinfo"),
    
    CREATE_QRCODE("https://api.weixin.qq.com/cgi-bin/qrcode/create"),
    SHOW_QRCODE("https://mp.weixin.qq.com/cgi-bin/showqrcode");
	
	private String url;

    private WechatRequest(String url) {
        this.url = url;
    }

    public String getName() {
        return StringUtils.lowerCase(StringUtils.replace(name(), "_", " "));
    }

    public String getUrl() {
        return url;
    }
}
