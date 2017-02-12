package io.github.adraw.framework.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import io.github.adraw.framework.model.Member;
import io.github.adraw.framework.util.MyMapper;
@Mapper
public interface MemberMapper extends MyMapper<Member> {
	List<Member> list4Public(Member member);
	Member lockById(long id);
}