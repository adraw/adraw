package io.github.adraw.framework.config;

import org.apache.shiro.authc.AuthenticationToken;

public class JWTAuthenticationToken implements AuthenticationToken {

	private static final long serialVersionUID = 4353400013129927517L;
	private Object userId;
    private String token;

    public JWTAuthenticationToken(Object userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return getUserId();
    }

    @Override
    public Object getCredentials() {
        return getToken();
    }

    public Object getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
