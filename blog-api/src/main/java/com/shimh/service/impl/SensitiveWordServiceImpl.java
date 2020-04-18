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

import com.shimh.entity.SensitiveWord;
import com.shimh.repository.SensitiveWordRepository;
import com.shimh.service.SensitiveWordService;
import com.shimh.vo.PageVo;

/**
 * @author miansen.wang
 * @date 2020-04-19
 */
@Service
public class SensitiveWordServiceImpl implements SensitiveWordService {

	@Autowired
	private SensitiveWordRepository sensitiveWordRepository;

	@Transactional(rollbackOn = Exception.class)
	@Override
	public void save(SensitiveWord sensitiveWord) {
		sensitiveWord.setCreateDate(new Date());
		sensitiveWordRepository.save(sensitiveWord);
	}

	@Transactional(rollbackOn = Exception.class)
	@Override
	public void update(SensitiveWord sensitiveWord) {
		sensitiveWordRepository.save(sensitiveWord);
	}

	@Transactional(rollbackOn = Exception.class)
	@Override
	public void remove(Integer id) {
		sensitiveWordRepository.delete(id);
	}

	@Transactional(rollbackOn = Exception.class)
	@Override
	public void remove(Integer[] ids) {
		for (Integer id : ids) {
			remove(id);
		}
	}
	
	@Override
	public SensitiveWord getbyId(Integer id) {
		return sensitiveWordRepository.findOne(id);
	}

	@Override
	public List<SensitiveWord> getAll() {
		return sensitiveWordRepository.findAll();
	}
	
	@Override
	public Page<SensitiveWord> page(SensitiveWord sensitiveWord, PageVo pageVo) {
		
		final Integer limit = StringUtils.isEmpty(pageVo.getPageNumber()) ? 0 : (pageVo.getPageNumber() - 1);
		final Integer offset = StringUtils.isEmpty(pageVo.getPageSize()) ? 10 : pageVo.getPageSize();
		final String sort = StringUtils.isEmpty(pageVo.getSort()) ? "createDate" : pageVo.getSort();
		
		return sensitiveWordRepository.findAll((root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (!StringUtils.isEmpty(sensitiveWord.getWord())) {
				predicates.add(cb.like(root.<String>get("word"), "%" + sensitiveWord.getWord() + "%"));
			}
			return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
		}, new PageRequest(limit, offset, new Sort(Direction.DESC, sort)));
	}

}
