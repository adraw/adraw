package io.github.adraw.framework.controller;


import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.adraw.framework.base.ResponseResult;
import io.github.adraw.framework.base.RestResultGenerator;
import io.github.adraw.framework.model.Member;
import io.github.adraw.framework.model.User;
import io.github.adraw.framework.service.IMemberService;

@RestController
@RequestMapping("/member")
public class MemberController {
	@Autowired
	private IMemberService memberService;
	
	@RequestMapping(value="list4Public.json")
	public ResponseResult<List<Member>> list4Public(Member member){
		List<Member> list = memberService.list4Public(member);
		return RestResultGenerator.genResult(list,"查询成功!");
	}
	
	@RequestMapping(value="currentMember")
	public ResponseResult<Member> currentMember(){
		User u = (User) SecurityUtils.getSubject().getPrincipal();
		Long userId = u.getId();
		Member member = memberService.byUserId(userId);
		return RestResultGenerator.genResult(member,"查询成功!");
	}
	

}
