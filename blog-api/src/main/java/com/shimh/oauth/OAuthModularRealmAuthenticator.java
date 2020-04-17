package com.shimh.oauth;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;

/**
 * 这个类是自定义认证器，不仅可以实现多 Realm 认证，还可以指定由哪个 Realm 来认证。
 * <p>请注意：要想此类发挥 "指定 Realm" 的功能，前提是用户登录时， {@code subject.login(token)} 方法传入的是 {@link #OAuthUsernamePasswordToken} 对象。
 * 
 * @author miansen.wang
 * @date 2020-04-17
 */
public class OAuthModularRealmAuthenticator extends ModularRealmAuthenticator {

	@Override
	protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		// 先校验 Realms 是否为空
		assertRealmsConfigured();
		
		Collection<Realm> realms = getRealms();
		
		// 只有一个 Realm
		if (realms.size() == 1) {
			return doSingleRealmAuthentication(realms.iterator().next(), authenticationToken);
		}
		
		OAuthUsernamePasswordToken oAuthUsernamePasswordToken = null;
		Collection<Realm> assignRealms = new ArrayList<>();
		if (authenticationToken instanceof OAuthUsernamePasswordToken) {
			oAuthUsernamePasswordToken = (OAuthUsernamePasswordToken) authenticationToken;
		}
		// 看看有木有特别指定哪个 Realm
		if (oAuthUsernamePasswordToken != null) {
			for (Realm realm : realms) {
				Collection<Class<? extends AuthorizingRealm>> realmClassList = oAuthUsernamePasswordToken.getRealmClassList();
				for (Class<? extends AuthorizingRealm> realmClass : realmClassList) {
					if (realmClass.isInstance(realm)) {
						assignRealms.add(realm);
					}
				}
			}
		}
		// 如果指定了 Realm，那么就让指定的去处理
		if (!assignRealms.isEmpty()) {
			return doMultiRealmAuthentication(assignRealms, authenticationToken);
		}
		return doMultiRealmAuthentication(realms, authenticationToken);
	}

}
