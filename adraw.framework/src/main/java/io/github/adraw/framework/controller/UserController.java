package io.github.adraw.framework.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.adraw.framework.model.User;
import io.github.adraw.framework.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;
	
	@RequestMapping("list")
	@ResponseBody
	public List<User> list(){
		return userService.selectAll();
	}
	
	@RequestMapping(value="save",method=RequestMethod.POST)
	@ResponseBody
	public User save(User user){
		userService.save(user);
		return user;
	}
	
}
