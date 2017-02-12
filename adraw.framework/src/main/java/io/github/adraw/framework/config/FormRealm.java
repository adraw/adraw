package io.github.adraw.framework.config;

import java.util.List;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import io.github.adraw.framework.mapper.UserMapper;
import io.github.adraw.framework.model.Role;
import io.github.adraw.framework.model.User;


public class FormRealm extends AuthorizingRealm {

	
	@Autowired
	private UserMapper userMapper;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token != null && token instanceof UsernamePasswordToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        User user = userMapper.findByUserName(upToken.getUsername());
        if (user != null) {
            SimpleAccount account = new SimpleAccount(user, user.getPassword(),ByteSource.Util.bytes(user.getUserName()+user.getSalt()), getName());
            List<Role> roles=user.getRoles();
    		for (Role role : roles) {
    			account.addRole(role.getCode());
    		}
            return account;
        }
        return null;
    }
    
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = (User) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo sai = new SimpleAuthorizationInfo();
        List<Role> roles=user.getRoles();
		for (Role role : roles) {
			sai.addRole(role.getCode());
		}
        return sai;
    }
}
