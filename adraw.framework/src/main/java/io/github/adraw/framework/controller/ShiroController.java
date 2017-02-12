package io.github.adraw.framework.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.github.adraw.framework.base.ResponseResult;
import io.github.adraw.framework.base.RestResultGenerator;
import io.github.adraw.framework.mapper.UserMapper;
import io.github.adraw.framework.model.Member;
import io.github.adraw.framework.model.User;
import io.github.adraw.framework.service.IMemberService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class ShiroController {
	
	private static final Logger logger = LoggerFactory.getLogger(ShiroController.class);
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private IMemberService memberService;
	
	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;

//	@RequestMapping(value="/login",method=RequestMethod.GET)
//	public String loginForm(Model model){
//		model.addAttribute("user", new User());
//		return "login";
//	}
		
	@RequestMapping (value = "getCode")
	public ResponseResult<Map<String,String>> getCode(String mobile){
	    Map<String,String> map = new HashMap<String,String>();
	    String rn = RandomStringUtils.randomNumeric(4);
	    String cid = UUID.randomUUID().toString();
	    map.put("rn", rn);
	    map.put("cid", cid);
		return RestResultGenerator.genResult(map,"操作成功!");
	}
	
	@RequestMapping (value = "regist", method = RequestMethod.POST)
	public ResponseResult<Map<String,String>> regist(Member member,User user,@RequestParam(value="uid") String uid){
		memberService.insert(member, user,uid);
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256 ;
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		byte[] encodedKey = Base64.decodeBase64("aaaaaaa");
		SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
		JwtBuilder builder = Jwts.builder()
				.setIssuedAt(now)
				.setSubject(user.getUserName())
				.signWith(signatureAlgorithm, key);
		long expMillis = nowMillis + 100000000;
		Date exp = new Date(expMillis);
		builder.setExpiration(exp);
		String token =  builder.compact();
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("token", token);
		return RestResultGenerator.genResult(map,"操作成功!");
	}
	
	@RequestMapping (value = "login", method = RequestMethod.POST)
	public ResponseResult<Map<String,String>> login(){
		User u = (User) SecurityUtils.getSubject().getPrincipal();
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256 ;
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		byte[] encodedKey = Base64.decodeBase64("aaaaaaa");
		SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
		JwtBuilder builder = Jwts.builder()
				.setIssuedAt(now)
				.setSubject(u.getUserName())
				.signWith(signatureAlgorithm, key);
		long expMillis = nowMillis + 100000000;
		Date exp = new Date(expMillis);
		builder.setExpiration(exp);
		String token =  builder.compact();
		Map<String,String> map = new HashMap<String,String>();
		map.put("token", token);
		return RestResultGenerator.genResult(map,"登录成功!");
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)  
    public String logout(RedirectAttributes redirectAttributes ){ 
        SecurityUtils.getSubject().logout();  
        redirectAttributes.addFlashAttribute("message", "您已安全退出");  
        return "redirect:/login";
    } 
	
	@RequestMapping("/403")
	public String unauthorizedRole(){
		logger.info("------没有权限-------");
		return "403";
	}
	
}
