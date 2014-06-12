package com.wenzchao.swechat.entity.message.out;

import java.util.List;

/**
 * 发送图文消息
 * 
 * @author Venz
 * 
 */
public class NewsMessage extends BaseMessage {

	private String articleCount; // 图文消息个数，限制为10条以内
	private List<Article> articles; // 多条图文消息信息，默认第一个item为大图,注意，如果图文数超过10，则将会无响应

	public String getArticleCount() {
		return articleCount;
	}

	public void setArticleCount(String articleCount) {
		this.articleCount = articleCount;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

}
