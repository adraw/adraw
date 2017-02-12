package io.github.adraw.framework.service;



import java.util.List;

import com.github.pagehelper.PageInfo;

import io.github.adraw.framework.model.Dict;

public interface IDictService {
	void save(Dict dict);

	PageInfo<Dict> page(Dict dict, int pageNum, int pageSize);

	void update(Dict dict);

	void delete(Dict dict);

	List<Dict> list(Dict dict);
}
