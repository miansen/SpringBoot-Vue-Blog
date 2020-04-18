package com.shimh.vo;

/**
 * @author miansen.wang
 * @date 2020-04-18
 */
public class ArticleAdminVo {

	private String title;
	
	private String author;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "AdminArticleVO [title=" + title + ", author=" + author + "]";
	}
	
}
