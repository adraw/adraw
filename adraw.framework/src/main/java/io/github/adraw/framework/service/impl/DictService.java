package io.github.adraw.framework.service.impl;


import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.github.adraw.framework.mapper.DictMapper;
import io.github.adraw.framework.model.Dict;
import io.github.adraw.framework.model.User;
import io.github.adraw.framework.service.IDictService;

@Service
public class DictService implements IDictService{

	@Autowired
	private DictMapper dictMapper;

	@Override
	public void save(Dict dict) {
		Subject currentUser = SecurityUtils.getSubject();
		User user =  (User) currentUser.getPrincipal();
		String userId = user.getId()+"";
		dict.setCreateBy(userId );
		Date createDate = new Date();
		dict.setCreateDate(createDate);
		dict.setUpdateBy(userId);
		dict.setUpdateDate(createDate);
		dict.setDelFlag("0");
		dictMapper.insert(dict);
	}

	@Override
	public PageInfo<Dict> page(Dict dict,int pageNum, int pageSize) {
		dict.setDelFlag("0");
		return PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> dictMapper.select(dict));
	}

	@Override
	public void update(Dict dict) {
		Subject currentUser = SecurityUtils.getSubject();
		User user =  (User) currentUser.getPrincipal();
		String userId = user.getId()+"";
		Date updateDate = new Date();
		dict.setUpdateBy(userId);
		dict.setUpdateDate(updateDate);
		dictMapper.updateByPrimaryKeySelective(dict);
	}

	@Override
	public void delete(Dict dict) {
		dict.setDelFlag("1");
		dictMapper.updateByPrimaryKeySelective(dict);
	}

	@Override
	public List<Dict> list(Dict dict) {
		dict.setDelFlag("0");
		return dictMapper.select(dict);
	}

	
}
