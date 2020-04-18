package com.shimh.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.shimh.entity.SensitiveWord;
import com.shimh.vo.PageVo;

/**
 * 敏感词管理 Service 接口
 * 
 * @author miansen.wang
 * @date 2020-04-19
 */
public interface SensitiveWordService {

	void save(SensitiveWord sensitiveWord);

	void update(SensitiveWord sensitiveWord);

	void remove(Integer id);

	void remove(Integer[] ids);
	
	SensitiveWord getbyId(Integer id);
	
	List<SensitiveWord> getAll();

	Page<SensitiveWord> page(final SensitiveWord sensitiveWord, final PageVo pageVo);

}
