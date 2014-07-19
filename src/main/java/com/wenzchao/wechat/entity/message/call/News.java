package com.wenzchao.wechat.entity.message.call;

import java.util.List;

/**
 * 图文消息
 * 
 * @author Venz
 * 
 */
public class News {

	private List<Article> articles;

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

}
