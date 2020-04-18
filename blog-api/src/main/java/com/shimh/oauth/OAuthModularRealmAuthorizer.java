package com.shimh.oauth;

import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;

import com.shimh.entity.AdminUser;

/**
 * @author miansen.wang
 * @date 2020-04-18
 */
public class OAuthModularRealmAuthorizer extends ModularRealmAuthorizer {

	@Override
	public boolean hasRole(PrincipalCollection principals, String roleIdentifier) {
		assertRealmsConfigured();
		Object principal = principals.getPrimaryPrincipal();
		if (principal instanceof AdminUser) {
			for (Realm realm : getRealms()) {
				if (realm instanceof OAuthAdminRealm) {
					if (((Authorizer) realm).hasRole(principals, roleIdentifier)) {
						return true;
					}
				}
			}
		}
		return super.hasRole(principals, roleIdentifier);
	}

}
