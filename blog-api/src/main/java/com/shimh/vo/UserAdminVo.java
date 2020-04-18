package com.shimh.vo;

/**
 * @author miansen.wang
 * @date 2020-04-18
 */
public class UserAdminVo {

	private String account;
	
	private String nickname;
	
	private String sort;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	@Override
	public String toString() {
		return "UserAdminVo [account=" + account + ", nickname=" + nickname + ", sort=" + sort + "]";
	}
	
}
