package io.github.adraw.framework.test;

import java.util.concurrent.TimeUnit;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import io.github.adraw.framework.mapper.StudentMapper;
import io.github.adraw.framework.mapper.UserMapper;
import io.github.adraw.framework.model.User;
import io.github.adraw.framework.service.impl.UserService;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
public class UserTest {
	
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private StudentMapper studentMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;  
	
	@Test
	public void testRedis(){
		redisTemplate.opsForValue().set("aaa", "111",3,TimeUnit.MINUTES);
		
		
		
		String a = redisTemplate.opsForValue().get("aaa");
		System.out.println(a);
	}
	
	@Test
	public void testUserByUserName(){
		
//		User u = userMapper.findByUserName("admin");
//		
//		List<Role> roles = u.getRoles();
//		
//		for (Role role : roles) {
//			String roleName = role.getName();
//			System.out.println(roleName);
//			logger.info(roleName);
//		}
		
		 HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();  
		 credentialsMatcher.setHashAlgorithmName("MD5");  
		 credentialsMatcher.setHashIterations(2);  
		 credentialsMatcher.setStoredCredentialsHexEncoded(true);  
		
		 
//		 String str = "hello";  
//		 String salt = "123";  
//		 //内部使用MessageDigest  
//		 String simpleHash = new SimpleHash("MD5", str, "123",2).toString();
//		
//		 User user = new User();
//		 user.setUserName("aaa");
//		 user.setPassword(simpleHash);
//		 SimpleAccount account = new SimpleAccount(user, user.getPassword(),ByteSource.Util.bytes("123"), "test");
//		 
//		 
//		 UsernamePasswordToken upToken = new UsernamePasswordToken("aaa", "hello");
//		 
//		 System.out.println(credentialsMatcher.doCredentialsMatch(upToken, account));
		 
//		String salt =  new SecureRandomNumberGenerator().nextBytes().toHex();
//		System.out.println(salt);
//		System.out.println(new SimpleHash("MD5", "admin", "admin"+salt,2).toString());
		 
		 
		User user = new User();
		
		user.setMobilePhone("123");
		user.setUserName("aaa");
		user.setPassword("aaa");
		 
		
		userService.save(user);
		
		System.out.println(user.getId());
		
    }
	
	

}
