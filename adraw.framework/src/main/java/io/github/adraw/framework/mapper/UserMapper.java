package io.github.adraw.framework.mapper;

import org.apache.ibatis.annotations.Mapper;

import io.github.adraw.framework.model.User;
import io.github.adraw.framework.util.MyMapper;
@Mapper
public interface UserMapper extends MyMapper<User> {
	User findByUserName(String userName);
}