package com.shimh.oauth;

import java.util.Collection;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;

/**
 * 该类是自定义登录 Token，扩展了 {@code realmClassList} 字段，该字段用来指定处理登录 Realm。
 * <p>用户登录时，该类的对象被传递给 {@link #OAuthModularRealmAuthenticator}，在它的 doAuthenticate() 方法
 * 中获取到 {@code realmClassList}，从而判断使用哪个 Realm 来进行认证和授权。
 * 
 * @author miansen.wang
 * @date 2020-04-17
 */
public class OAuthUsernamePasswordToken extends UsernamePasswordToken {

	private static final long serialVersionUID = 7986401142376038381L;
	
	/**
	 * Realm 的 Class 对象
	 */
	private final Collection<Class<? extends Realm>> realmClassList;

	public OAuthUsernamePasswordToken(String username, String password, Collection<Class<? extends Realm>> realmClassList) {
		super(username, password);
		this.realmClassList = realmClassList;
	}

	public Collection<Class<? extends Realm>> getRealmClassList() {
		return realmClassList;
	}

}
