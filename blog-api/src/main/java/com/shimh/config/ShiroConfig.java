package com.shimh.config;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.shimh.common.cache.RedisManager;
import com.shimh.oauth.OAuthAdminRealm;
import com.shimh.oauth.OAuthModularRealmAuthenticator;
import com.shimh.oauth.OAuthModularRealmAuthorizer;
import com.shimh.oauth.OAuthRealm;
import com.shimh.oauth.OAuthSessionDAO;
import com.shimh.oauth.OAuthSessionManager;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();  
       /* filterChainDefinitionMap.put("/", "anon");  

        filterChainDefinitionMap.put("/static/**", "anon"); 
        filterChainDefinitionMap.put("/blogFile/**", "anon");
        filterChainDefinitionMap.put("/login", "anon"); 
        filterChainDefinitionMap.put("/register", "anon"); 
        filterChainDefinitionMap.put("/**", "authc"); */

        //filterChainDefinitionMap.put("/**/create", "authc");
        //filterChainDefinitionMap.put("/**/update", "authc");
        //filterChainDefinitionMap.put("/**/delete", "authc");
        //filterChainDefinitionMap.put("/upload", "authc");
        //filterChainDefinitionMap.put("/users/currentUser", "authc");

        filterChainDefinitionMap.put("/**", "anon");

        //返回json数据，由前端跳转
        shiroFilterFactoryBean.setLoginUrl("/handleLogin");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }

    @Bean
    public OAuthRealm oAuthRealm() {
        OAuthRealm myShiroRealm = new OAuthRealm();
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }
    
    @Bean
    public OAuthAdminRealm aAuthAdminRealm(HashedCredentialsMatcher hashedCredentialsMatcher) {
    	OAuthAdminRealm oAuthAdminRealm = new OAuthAdminRealm();
    	oAuthAdminRealm.setCredentialsMatcher(hashedCredentialsMatcher);
    	return oAuthAdminRealm;
    }


    @Bean
    public SecurityManager securityManager(OAuthRealm oAuthRealm, OAuthAdminRealm aAuthAdminRealm, 
    		SessionManager sessionManager, OAuthModularRealmAuthenticator authenticator, OAuthModularRealmAuthorizer authorizer) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // securityManager.setRealm(oAuthRealm);
        
        /*List<Realm> realms = new ArrayList<>();
		realms.add(oAuthRealm);
		realms.add(aAuthAdminRealm);
		securityManager.setRealms(realms);*/
		
        securityManager.setSessionManager(sessionManager);
        
        // 使用自定义的认证器
        securityManager.setAuthenticator(authenticator);
        
        // 使用自定义的授权器
        securityManager.setAuthorizer(authorizer);
        
        // 自定义缓存实现 使用redis  
        //securityManager.setCacheManager(cacheManager());  
        return securityManager;
    }

    @Bean
    public SessionManager sessionManager(OAuthSessionDAO authSessionDAO) {
        OAuthSessionManager oAuthSessionManager = new OAuthSessionManager();
        oAuthSessionManager.setSessionDAO(authSessionDAO);
        return oAuthSessionManager;
    }


    @Bean
    public OAuthSessionDAO authSessionDAO(RedisManager redisManager) {
        OAuthSessionDAO authSessionDAO = new OAuthSessionDAO();
        authSessionDAO.setRedisManager(redisManager);
        return authSessionDAO;
    }


    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    
    /**
     * 自定义认证器，不仅可以实现多 Realm 认证，还可以指定由哪个 Realm 来认证。 
     * @return
     */
	@Bean
	public OAuthModularRealmAuthenticator oAuthModularRealmAuthenticator(OAuthRealm oAuthRealm, OAuthAdminRealm aAuthAdminRealm) {
		OAuthModularRealmAuthenticator authenticator = new OAuthModularRealmAuthenticator();
		// 使用自定义认证器的话要配置 Realms
		List<Realm> realms = new ArrayList<>();
		realms.add(oAuthRealm);
		realms.add(aAuthAdminRealm);
		authenticator.setRealms(realms);
		return authenticator;
	}
	
	/**
	 * 自定义授权器
	 * 
	 * @param oAuthRealm
	 * @param aAuthAdminRealm
	 * @return
	 */
	@Bean
	public OAuthModularRealmAuthorizer oAuthModularRealmAuthorizer(OAuthRealm oAuthRealm, OAuthAdminRealm aAuthAdminRealm) {
		OAuthModularRealmAuthorizer authorizer = new OAuthModularRealmAuthorizer();
		// 使用自定义授权器的话要配置 Realms
		List<Realm> realms = new ArrayList<>();
		realms.add(oAuthRealm);
		realms.add(aAuthAdminRealm);
		authorizer.setRealms(realms);
		return authorizer;
	}

}
