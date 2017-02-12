package io.github.adraw.framework.mapper;

import org.apache.ibatis.annotations.Mapper;

import io.github.adraw.framework.model.Message;
import io.github.adraw.framework.util.MyMapper;
@Mapper
public interface MessageMapper extends MyMapper<Message> {
}