package io.github.adraw.framework.service;

import java.util.List;

import io.github.adraw.framework.model.User;

public interface IUserService {
	List<User> selectAll();
	void save(User user);
	void insertUseGeneratedKeys(User user);
}
