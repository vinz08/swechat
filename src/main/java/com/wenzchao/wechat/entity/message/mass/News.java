package com.wenzchao.wechat.entity.message.mass;

import java.util.List;

/**
 * 图文信息
 * 
 * @author Venz
 *
 */
public class News {

	List<Article> articles;

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

}
