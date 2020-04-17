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
 * @author miansen.wang
 * @date 2020-04-17
 */
@Entity
@Table(name = "admin_user")
public class AdminUser extends BaseEntity<Integer> {

	private static final long serialVersionUID = 5277768473511026882L;

	@Column(name = "username", length = 10)
	private String username;

	@Column(name = "password", length = 64)
	private String password;

	@Column(name = "avatar", length = 250)
	private String avatar;

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "create_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "update_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
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
		return "AdminUser {username=" + username + ", password=" + password + ", avatar=" + avatar + ", createDate="
				+ createDate + ", updateDate=" + updateDate + "}";
	}

}
