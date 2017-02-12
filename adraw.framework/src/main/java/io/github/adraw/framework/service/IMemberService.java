package io.github.adraw.framework.service;

import java.util.List;

import io.github.adraw.framework.model.Member;
import io.github.adraw.framework.model.User;

public interface IMemberService {
	void insert(Member member,User user, String uid);
	List<Member> list4Public(Member member);
	Member byUserId(Long userId);
}
