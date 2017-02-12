package io.github.adraw.framework.mapper;

import org.apache.ibatis.annotations.Mapper;

import io.github.adraw.framework.model.Archive;
import io.github.adraw.framework.util.MyMapper;

@Mapper
public interface ArchiveMapper extends MyMapper<Archive> {
}