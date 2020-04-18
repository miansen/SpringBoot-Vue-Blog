package com.shimh.service;

import org.springframework.data.domain.Page;

import com.shimh.entity.Article;
import com.shimh.vo.AdminArticleVO;
import com.shimh.vo.PageVo;

/**
 * 文章管理 Service 接口
 * 
 * @author miansen.wang
 * @date 2020-04-17
 */
public interface ArticleAdminService {
	
	void save(Article article);
	
	void update(Article article);
	
	void remove(Integer id);
	
	void remove(Integer[] ids);
	
	Page<Article> page(final AdminArticleVO articleVo, final PageVo pageVo);
	
}
