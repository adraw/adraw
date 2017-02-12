package io.github.adraw.framework.controller;


import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.adraw.framework.base.ResponseResult;
import io.github.adraw.framework.base.RestResultGenerator;
import io.github.adraw.framework.model.Message;
import io.github.adraw.framework.model.User;
import io.github.adraw.framework.service.IMessageService;

@RestController
@RequestMapping("/message")
public class MessageController {
	@Autowired
	private IMessageService messageService;
	
	@RequestMapping(value="sendDirectMessage")
	public ResponseResult<?> sendDirectMessage(@RequestBody Message message){
		User u = (User) SecurityUtils.getSubject().getPrincipal();
		Long userId = u.getId();
		message.setSendBy(userId);
		messageService.insertDirect(message);
		return RestResultGenerator.genResult("发送成功!");
	}
	
	@RequestMapping(value="sendCommentMessage")
	public ResponseResult<?> sendCommentMessage(@RequestBody Message message){
		User u = (User) SecurityUtils.getSubject().getPrincipal();
		Long userId = u.getId();
		message.setSendBy(userId);
		messageService.insertComment(message);
		return RestResultGenerator.genResult("发送成功!");
	}
	
	@RequestMapping(value="sendAttentionMessage")
	public ResponseResult<?> sendAttentionMessage(@RequestBody Message message){
		User u = (User) SecurityUtils.getSubject().getPrincipal();
		Long userId = u.getId();
		message.setSendBy(userId);
		messageService.insertAttention(message);
		return RestResultGenerator.genResult("发送成功!");
	}
	

}
