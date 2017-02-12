package io.github.adraw.framework.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import io.github.adraw.framework.mapper.ArchiveMapper;
import io.github.adraw.framework.mapper.MemberMapper;
import io.github.adraw.framework.mapper.UserMapper;
import io.github.adraw.framework.model.Archive;
import io.github.adraw.framework.model.Member;
import io.github.adraw.framework.model.User;
import io.github.adraw.framework.service.IMemberService;

@Service
public class MemberService implements IMemberService {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private ArchiveMapper archiveMapper;
	
	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public void insert(Member member, User user, String uid) {
		Date now = new Date();
		String password = user.getPassword();
		String username = user.getUserName();
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();  
		credentialsMatcher.setHashAlgorithmName("MD5");  
		credentialsMatcher.setHashIterations(2);  
		credentialsMatcher.setStoredCredentialsHexEncoded(true);
		String salt =  new SecureRandomNumberGenerator().nextBytes().toHex();
		password = new SimpleHash("MD5", username, username+salt,2).toString();
		user.setPassword(password);
		user.setSalt(salt);
		user.setCreateAt(now);
		user.setUpdateAt(now);
		
		
		userMapper.insert(user);
		
		member.setUserId(user.getId());
		member.setCreateAt(now);
		member.setUpdateAt(now);
		memberMapper.insert(member);
		
		Long mid = member.getId();
		
		List<Long> ids = (List<Long>) redisTemplate.opsForValue().get(uid);
		
		if(ids==null){
			return;
		}
		
		for (Long id : ids) {
			Archive archive = new Archive();
			archive.setId(id);
			archive.setBusinessType("member");
			archive.setBusinessId(mid);
			archiveMapper.updateByPrimaryKeySelective(archive);
		}
		
		
		redisTemplate.delete(uid);
	}

	@Override
	public List<Member> list4Public(Member member) {
		return memberMapper.list4Public(member);
	}

	@Override
	public Member byUserId(Long userId) {
		Member record = new Member();
		record.setUserId(userId);
		Member m = memberMapper.selectOne(record);
		Archive domain = new Archive();
		domain.setBusinessType("member");
		domain.setBusinessId(m.getId());
		Archive archive = archiveMapper.selectOne(domain);
		m.setAvatarId(archive.getId());
		return m;
	}
	
	
}
