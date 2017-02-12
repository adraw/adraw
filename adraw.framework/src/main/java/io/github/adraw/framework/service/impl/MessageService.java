package io.github.adraw.framework.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.adraw.framework.mapper.MemberMapper;
import io.github.adraw.framework.mapper.MemberStatMapper;
import io.github.adraw.framework.mapper.MessageMapper;
import io.github.adraw.framework.mapper.UserMapper;
import io.github.adraw.framework.model.Message;
import io.github.adraw.framework.service.IMessageService;

@Service
public class MessageService implements IMessageService {

	@Autowired
	private MessageMapper messageMapper;
	
	@Autowired
	private MemberStatMapper memberStatMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private MemberMapper memberMapper;
	
	
	@Override
	public Message insertDirect(Message message) {
		//Long id = message.getEntityId();
		//memberMapper.lockById(id);
		Date createAt = new Date();
		message.setCreateAt(createAt);
		message.setUpdateAt(createAt);
		message.setType("comment");
		message.setEntityType("user");
		message.setStatus("init");
		messageMapper.insert(message);
		
//		MemberStat ms = new MemberStat();
//		ms.setMemberId(id);
//		MemberStat memberStat = memberStatMapper.selectOne(ms);
//		if(memberStat==null){
//			ms.setDirectNum(1);
//			memberStatMapper.insertSelective(ms);
//			return message;
//		}
//		
//		Integer directNum = memberStat.getDirectNum();
//		
//		MemberStat memberStatUpdate = new MemberStat();
//		memberStatUpdate.setId(memberStat.getId());
//		memberStatUpdate.setDirectNum(++directNum);
//		memberStatMapper.updateByPrimaryKeySelective(memberStatUpdate);
		return message;
		
	}

	@Override
	public Message insertComment(Message message) {
		
		//Long id = message.getEntityId();
		//memberMapper.lockById(id);
		Date createAt = new Date();
		message.setCreateAt(createAt);
		message.setUpdateAt(createAt);
		message.setType("direct");
		message.setEntityType("user");
		message.setStatus("init");
		messageMapper.insert(message);
		
//		
//		MemberStat ms = new MemberStat();
//		ms.setMemberId(id);
//		MemberStat memberStat = memberStatMapper.selectOne(ms);
//		if(memberStat==null){
//			ms.setCommentNum(1);
//			memberStatMapper.insertSelective(ms);
//			return message;
//		}
//		
//		Integer commentNum = memberStat.getCommentNum();
//		
//		MemberStat memberStatUpdate = new MemberStat();
//		memberStatUpdate.setId(memberStat.getId());
//		memberStatUpdate.setCommentNum(++commentNum);
//		memberStatMapper.updateByPrimaryKeySelective(memberStatUpdate);
		
		return message;
	}

	@Override
	public Message insertAttention(Message message) {
		
		Long id = message.getEntityId();
		memberMapper.lockById(id);
		
		message.setType("attention");
		message.setEntityType("user");
		
		int count = messageMapper.selectCount(message);
		
		if(count > 0){
			return message;
		}
		
		
		Date createAt = new Date();
		message.setCreateAt(createAt);
		message.setUpdateAt(createAt);
		message.setStatus("init");
		messageMapper.insert(message);
		
//		MemberStat ms = new MemberStat();
//		ms.setMemberId(id);
//		MemberStat memberStat = memberStatMapper.selectOne(ms);
//		if(memberStat==null){
//			ms.setAttentionNum(1);
//			memberStatMapper.insertSelective(ms);
//			return message;
//		}
//		
//		Integer AttentionNum = memberStat.getAttentionNum();
//		
//		MemberStat memberStatUpdate = new MemberStat();
//		memberStatUpdate.setId(memberStat.getId());
//		memberStatUpdate.setAttentionNum(++AttentionNum);
//		memberStatMapper.updateByPrimaryKeySelective(memberStatUpdate);
//		
		
		return message;
	}  

	
	
}
