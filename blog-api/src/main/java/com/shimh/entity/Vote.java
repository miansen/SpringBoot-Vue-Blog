package com.shimh.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.annotation.JSONField;

import com.shimh.common.entity.BaseEntity;


@Entity
@Table(name = "me_vote")
public class Vote extends BaseEntity<Integer> {

	// ALTER table sys_user add COLUMN integral INT(11) DEFAULT 0 COMMENT '积分';
	private static final long serialVersionUID = 5261804795163527143L;

	@NotNull
	@Column(name = "article_id")
	private Integer articleId;

	@Column(name = "user_id")
	private Integer userId;

	@NotNull
	@Column(name = "vote_value")
	private Integer voteValue;

	@JSONField(format = "yyyy.MM.dd HH:mm")
	@Column(name = "create_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@JSONField(format = "yyyy.MM.dd HH:mm")
	@Column(name = "update_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getVoteValue() {
		return voteValue;
	}

	public void setVoteValue(Integer voteValue) {
		this.voteValue = voteValue;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return "Vote [articleId=" + articleId + ", userId=" + userId + ", voteValue=" + voteValue + ", createDate="
				+ createDate + ", updateDate=" + updateDate + "]";
	}

}
