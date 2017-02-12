package io.github.adraw.framework.mapper;

import org.apache.ibatis.annotations.Mapper;

import io.github.adraw.framework.model.Dict;
import io.github.adraw.framework.util.MyMapper;
@Mapper
public interface DictMapper extends MyMapper<Dict> {
}