package com.shimh.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.alibaba.fastjson.annotation.JSONField;

import com.shimh.common.entity.BaseEntity;

/**
 * 敏感词
 * 
 * @author miansen.wang
 * @date 2020-04-19
 */
@Entity
@Table(name = "sensitive_word")
public class SensitiveWord extends BaseEntity<Integer> {

	private static final long serialVersionUID = 6195346727647953192L;

	@Column(name = "word", length = 64)
	private String word;

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "create_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "SensitiveWord [word=" + word + ", createDate=" + createDate + "]";
	}

}
