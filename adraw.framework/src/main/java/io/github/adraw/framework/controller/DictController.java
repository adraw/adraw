package io.github.adraw.framework.controller;


import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;

import io.github.adraw.framework.base.ResponseResult;
import io.github.adraw.framework.base.RestResultGenerator;
import io.github.adraw.framework.model.Dict;
import io.github.adraw.framework.service.IDictService;

@RestController
@RequestMapping("/dict")
public class DictController {
	@Autowired
	private IDictService dictService;
	
	@RequestMapping(value="save",method=RequestMethod.POST)
	public ResponseResult<Dict> add(@RequestBody Dict dict){
		dictService.save(dict);
		return RestResultGenerator.genResult(dict,"保存成功!");
	}
	
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public ResponseResult<Dict> delete(@RequestBody Dict dict){
		dictService.delete(dict);
		return RestResultGenerator.genResult(dict,"保存成功!");
	}
	
	@RequestMapping(value="update",method=RequestMethod.POST)
	public ResponseResult<Dict> update(@RequestBody Dict dict){
		dictService.update(dict);
		return RestResultGenerator.genResult(dict,"保存成功!");
	}
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	@RequiresRoles("systemManager")
	public ResponseResult<PageInfo<Dict>> list(Dict dict,int pageNum, int pageSize){
		PageInfo<Dict> list = dictService.page(dict,pageNum,pageSize);
		return RestResultGenerator.genResult(list,"成功!");
	}
}
