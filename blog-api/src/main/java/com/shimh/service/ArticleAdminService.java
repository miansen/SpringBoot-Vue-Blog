package com.shimh.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.shimh.entity.Article;
import com.shimh.vo.ArticleAdminVo;
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
	
	List<Article> listByAuthorId(Long authorId);
	
	Page<Article> page(final ArticleAdminVo articleVo, final PageVo pageVo);
	
}
