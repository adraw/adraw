package io.github.adraw.framework.config;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.github.adraw.framework.mapper.UserMapper;
import io.github.adraw.framework.model.Role;
import io.github.adraw.framework.model.User;

public class MyShiroRealm extends AuthorizingRealm {

	private static final Logger logger = LoggerFactory
			.getLogger(MyShiroRealm.class);

	@Autowired
	private UserMapper userMapper;

	/**
	 * 权限认证，为当前登录的Subject授予角色和权限
	 * 
	 * @see 经测试：本例中该方法的调用时机为需授权资源被访问时
	 * @see 经测试：并且每次访问需授权资源时都会执行该方法中的逻辑，这表明本例中默认并未启用AuthorizationCache
	 * @see 经测试
	 *      ：如果连续访问同一个URL（比如刷新），该方法不会被重复调用，Shiro有一个时间间隔（使用的ehcache，时间在ehcache-
	 *      shiro.xml中配置），超过这个时间间隔再刷新页面，该方法会被执行
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		logger.info("##################执行Shiro权限认证##################");
		//获取当前登录输入的用户名，等价于(String) principalCollection.fromRealm(getName()).iterator().next();
        String loginName = (String)super.getAvailablePrincipal(principals); 
		//到数据库查是否有此对象
		User user=userMapper.findByUserName(loginName);// 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
		
		if(user == null){
			return null;
		}
		
		//权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
		SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
		//用户的角色对应的所有权限，如果只使用角色定义访问权限，下面的四行可以不要
		List<Role> roles=user.getRoles();
		for (Role role : roles) {
			info.addRole(role.getName());
		}
		return info;
	}

	/**
	 * 登录认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)throws AuthenticationException {

		//UsernamePasswordToken对象用来存放提交的登录信息
		UsernamePasswordToken token=(UsernamePasswordToken) authenticationToken;
				
		logger.info("验证当前Subject时获取到token"); 
				
		//查出是否有此用户
		User user=userMapper.findByUserName(token.getUsername());
		
		if(user == null){
			return null;
		}
		// 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
		return new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), getName());
	}
}
