package io.github.adraw.framework.mapper;

import org.apache.ibatis.annotations.Mapper;

import io.github.adraw.framework.model.Student;
import io.github.adraw.framework.util.MyMapper;
@Mapper
public interface StudentMapper extends MyMapper<Student>{
}
