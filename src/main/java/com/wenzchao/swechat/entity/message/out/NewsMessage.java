package com.wenzchao.swechat.entity.message.out;

import java.util.List;

/**
 * 发送图文消息
 * 
 * @author Venz
 * 
 */
public class NewsMessage extends BaseMessage {

	private String ArticleCount; // 图文消息个数，限制为10条以内
	private List<Article> Articles; // 多条图文消息信息，默认第一个item为大图,注意，如果图文数超过10，则将会无响应

	public String getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(String articleCount) {
		this.ArticleCount = articleCount;
	}

	public List<Article> getArticles() {
		return Articles;
	}

	public void setArticles(List<Article> articles) {
		this.Articles = articles;
	}

}
