package io.github.adraw.framework.mapper;

import org.apache.ibatis.annotations.Mapper;

import io.github.adraw.framework.model.MemberStat;
import io.github.adraw.framework.util.MyMapper;
@Mapper
public interface MemberStatMapper extends MyMapper<MemberStat> {
}