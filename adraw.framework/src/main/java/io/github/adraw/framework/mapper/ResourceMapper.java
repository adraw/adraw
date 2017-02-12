package io.github.adraw.framework.mapper;

import org.apache.ibatis.annotations.Mapper;

import io.github.adraw.framework.model.Resource;
import io.github.adraw.framework.util.MyMapper;
@Mapper
public interface ResourceMapper extends MyMapper<Resource> {
}