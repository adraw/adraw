package io.github.adraw.framework.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.adraw.framework.mapper.UserMapper;
import io.github.adraw.framework.model.User;
import io.github.adraw.framework.service.IUserService;

@Service
public class UserService implements IUserService{

	@Autowired
	private UserMapper userMapper;

	@Override
	public List<User> selectAll(){
		return userMapper.selectAll();
	}

	@Override
	public void save(User user) {
		userMapper.insert(user);
	}

	@Override
	public void insertUseGeneratedKeys(User user) {
		userMapper.insertUseGeneratedKeys(user);
	}
	
}
