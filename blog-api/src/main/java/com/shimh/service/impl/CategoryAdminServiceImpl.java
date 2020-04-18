package com.shimh.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.shimh.entity.Category;
import com.shimh.repository.CategoryAdminRepository;
import com.shimh.service.CategoryAdminService;
import com.shimh.vo.PageVo;

/**
 * 分类管理 Service 实现

 * @author miansen.wang
 * @date 2020-04-19
 */
@Service
public class CategoryAdminServiceImpl implements CategoryAdminService {

	@Autowired
	private CategoryAdminRepository categoryAdminRepository;

	@Transactional(rollbackOn = Exception.class)
	@Override
	public void save(Category category) {
		category.setCreateDate(new Date());
		categoryAdminRepository.save(category);
	}

	@Transactional(rollbackOn = Exception.class)
	@Override
	public void update(Category category) {
		categoryAdminRepository.save(category);
	}

	@Transactional(rollbackOn = Exception.class)
	@Override
	public void remove(Integer id) {
		categoryAdminRepository.delete(id);
	}

	@Transactional(rollbackOn = Exception.class)
	@Override
	public void remove(Integer[] ids) {
		for (Integer id : ids) {
			remove(id);
		}
	}

	@Override
	public Category getbyId(Integer id) {
		return categoryAdminRepository.findOne(id);
	}

	@Override
	public List<Category> getAll() {
		return categoryAdminRepository.findAll();
	}

	@Override
	public Page<Category> page(Category category, PageVo pageVo) {
		
		final Integer limit = StringUtils.isEmpty(pageVo.getPageNumber()) ? 0 : (pageVo.getPageNumber() - 1);
		final Integer offset = StringUtils.isEmpty(pageVo.getPageSize()) ? 10 : pageVo.getPageSize();
		final String sort = StringUtils.isEmpty(pageVo.getSort()) ? "createDate" : pageVo.getSort();
		
		return categoryAdminRepository.findAll((root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (!StringUtils.isEmpty(category.getCategoryname())) {
				predicates.add(cb.like(root.<String>get("categoryname"), "%" + category.getCategoryname() + "%"));
			}
			return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
		}, new PageRequest(limit, offset, new Sort(Direction.DESC, sort)));
	}

}
