package com.shimh.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.shimh.entity.Category;
import com.shimh.vo.PageVo;

/**
 * 分类管理 Service 接口
 * 
 * @author miansen.wang
 * @date 2020-04-19
 */
public interface CategoryAdminService {

	void save(Category category);

	void update(Category category);

	void remove(Integer id);

	void remove(Integer[] ids);
	
	Category getbyId(Integer id);
	
	List<Category> getAll();

	Page<Category> page(final Category category, final PageVo pageVo);

}
