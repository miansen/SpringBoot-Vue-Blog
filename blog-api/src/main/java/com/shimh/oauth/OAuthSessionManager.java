package com.shimh.oauth;

import java.io.Serializable;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import com.shimh.common.util.StringUtils;

/**
 * 自定义会话管理器，从请求头获取 Oauth-Token 参数，并将其对应的值设置为 sessionId。
 * <p>之所以自定义会话管理器，原因是：传统 web 项目中，shiro 从 cookie 中读取 sessionId 以此来维持会话。
 * 在前后端分离的项目中，我们可以在 ajax 的请求头中传递 sessionId，因此需要重写 shiro 获取 sessionId 的方式。</p>
 * @author shimh
 * <p>
 * 2018年1月23日
 */
public class OAuthSessionManager extends DefaultWebSessionManager {

    public static final String OAUTH_TOKEN = "Oauth-Token";

    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    public OAuthSessionManager() {
        super();
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        // 从请求头中获取 Oauth-Token 参数
        String id = httpRequest.getHeader(OAUTH_TOKEN);
        
        //如果请求头没有携带 Oauth-Token 参数，则按照父类的方式在 cookie 进行获取
        if (StringUtils.isEmpty(id)) {
        	return super.getSessionId(httpRequest, response);
        }

        //如果请求头中携带了 Oauth-Token 参数，则将它设置为 sessionId
        request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
        request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
        request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
        return id;
    }
}

