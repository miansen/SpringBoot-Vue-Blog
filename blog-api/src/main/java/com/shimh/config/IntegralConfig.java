package com.shimh.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 积分配置
 */
@Configuration
@ConfigurationProperties(prefix = "integral")
public class IntegralConfig {

	/**
	 * 创建文章奖励的积分
	 */
	private Integer createArticle;

	/**
	 * 创建评论奖励的积分
	 */
	private Integer createComment;

	/**
	 * 删除文章扣除的积分
	 */
	private Integer deleteArticle;

	/**
	 * 删除评论扣除的积分
	 */
	private Integer deleteComment;

	public Integer getCreateArticle() {
		return createArticle;
	}

	public void setCreateArticle(Integer createArticle) {
		this.createArticle = createArticle;
	}

	public Integer getCreateComment() {
		return createComment;
	}

	public void setCreateComment(Integer createComment) {
		this.createComment = createComment;
	}

	public Integer getDeleteArticle() {
		return deleteArticle;
	}

	public void setDeleteArticle(Integer deleteArticle) {
		this.deleteArticle = deleteArticle;
	}

	public Integer getDeleteComment() {
		return deleteComment;
	}

	public void setDeleteComment(Integer deleteComment) {
		this.deleteComment = deleteComment;
	}

	@Override
	public String toString() {
		return "IntegralConfig [createArticle=" + createArticle + ", createComment=" + createComment
				+ ", deleteArticle=" + deleteArticle + ", deleteComment=" + deleteComment + "]";
	}

}
