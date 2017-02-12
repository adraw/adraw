package io.github.adraw.framework.config;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StreamUtils;

import com.alibaba.fastjson.JSONObject;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

public final class JWTOrFormAuthenticationFilter extends AuthenticatingFilter {

    public static final String USER_ID = "userId";
    public static final String PASSWORD = "password";

    protected static final String AUTHORIZATION_HEADER = "Authorization";

    public JWTOrFormAuthenticationFilter() {
        setLoginUrl(DEFAULT_LOGIN_URL);
    }

    @Override
    public void setLoginUrl(String loginUrl) {
        String previous = getLoginUrl();
        if (previous != null) {
            this.appliedPaths.remove(previous);
        }
        super.setLoginUrl(loginUrl);
        this.appliedPaths.put(getLoginUrl(), null);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        boolean loggedIn = false;

        if (isLoginRequest(request, response) || isLoggedAttempt(request, response)) {
            loggedIn = executeLogin(request, response);
        }

        if (!loggedIn) {
            HttpServletResponse httpResponse = WebUtils.toHttp(response);
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        return loggedIn;
    }


    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws IOException {

        if (isLoginRequest(request, response)) {
            String json = StreamUtils.copyToString(request.getInputStream(),Charset.forName("utf-8"));

            if (json != null && !json.isEmpty()) {
            	JSONObject jo = JSONObject.parseObject(json);
                
                    String username = jo.getString(USER_ID);
                    String password = jo.getString(PASSWORD);
                    return new UsernamePasswordToken(username, password);

            }
        }

        if (isLoggedAttempt(request, response)) {
            String jwtToken = getAuthzHeader(request);
            if (jwtToken != null) {
                return createToken(jwtToken);
            }
        }

        return new UsernamePasswordToken();
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {

        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        return false;
    }

    protected boolean isLoggedAttempt(ServletRequest request, ServletResponse response) {
        String authzHeader = getAuthzHeader(request);
        return authzHeader != null;
    }

    protected String getAuthzHeader(ServletRequest request) {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        return httpRequest.getHeader(AUTHORIZATION_HEADER);
    }

    public JWTAuthenticationToken createToken(String token) {
    	try {
    		 byte[] encodedKey = Base64.decodeBase64("aaaaaaa");
    		    SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        	 Claims claims = Jwts.parser()
        	          .setSigningKey(key)
        	          .parseClaimsJws(token).getBody();
        	 return new JWTAuthenticationToken(claims.getSubject(), token);
    	}catch (SignatureException  e) {
    		throw new AuthenticationException(e);
		}

    }
    
}
