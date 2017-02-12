package io.github.adraw.framework.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.github.adraw.framework.base.ResponseResult;
import io.github.adraw.framework.base.RestResultGenerator;
import io.github.adraw.framework.model.Dict;
import io.github.adraw.framework.service.IDictService;

@RestController
@RequestMapping("/common")
public class CommonController {
	@Autowired
	private IDictService dictService;
	
	@RequestMapping(value="dict/list",method=RequestMethod.GET)
	public ResponseResult<List<Dict>> list(Dict dict){
		List<Dict> list = dictService.list(dict);
		return RestResultGenerator.genResult(list,"成功!");
	}
}
