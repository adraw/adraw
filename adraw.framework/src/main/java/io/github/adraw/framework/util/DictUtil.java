package io.github.adraw.framework.util;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import io.github.adraw.framework.mapper.DictMapper;
import io.github.adraw.framework.model.Dict;
import tk.mybatis.mapper.entity.Example;

@Component
public class DictUtil {
	
	private static DictMapper dm;
	
	@Resource
	private DictMapper dictMapper;
	
	@PostConstruct
	public void init() {
		dm = dictMapper;
	}
	
	public static String getLabelByTypeAndKey(String type,String value){
		
		 Example example = new Example(Dict.class);
		 example.createCriteria().andEqualTo("type", type).andEqualTo("value", value);
		 
		 example.selectProperties("label");
		
		 List<Dict> list = dm.selectByExample(example);
		
		 if(null == list || list.size() == 0){
			 return "";
		 }
		 
		 Dict dict = list.get(0);
		 
		 return dict.getLabel();
	}
}
