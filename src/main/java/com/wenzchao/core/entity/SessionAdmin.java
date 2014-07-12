package com.wenzchao.core.entity;

import java.io.Serializable;
import java.util.List;

import com.wenzchao.swechat.entity.base.AccessToken;

/**
 * SESSION管理员
 * 
 * @author Venz
 * 
 */
public class SessionAdmin implements Serializable {

	private static final long serialVersionUID = 1678092981097484160L;
	private AccessToken accessToken; // 管理微信公众号的凭证
	private String currBaseId; // 当前管理的公众账号原始ID
	private int currType;

	public AccessToken getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(AccessToken accessToken) {
		this.accessToken = accessToken;
	}

	public String getCurrBaseId() {
		return currBaseId;
	}

	public void setCurrBaseId(String currBaseId) {
		this.currBaseId = currBaseId;
	}

	public int getCurrType() {
		return currType;
	}

	public void setCurrType(int currType) {
		this.currType = currType;
	}

}
