package com.shimh.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.shimh.entity.Article;
import com.shimh.entity.User;
import com.shimh.repository.ArticleAdminRepository;
import com.shimh.service.ArticleAdminService;
import com.shimh.vo.ArticleAdminVo;
import com.shimh.vo.ArticleVo;
import com.shimh.vo.PageVo;

/**
 * 文章管理 Service 实现
 * 
 * @author miansen.wang
 * @date 2020-04-17
 */
@Service
public class ArticleAdminServiceImpl implements ArticleAdminService {

	@Autowired
	private ArticleAdminRepository articleAdminRepository;
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void save(Article article) {
		articleAdminRepository.save(article);
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void update(Article article) {
		if (StringUtils.isEmpty(article.getId())) {
			throw new IllegalArgumentException("文章 ID 不能为空");
		}
		Article oldArticle = articleAdminRepository.findOne(article.getId());
		oldArticle.setTitle(article.getTitle());
		oldArticle.setSummary(article.getSummary());
		oldArticle.setBody(article.getBody());
		oldArticle.setCategory(article.getCategory());
		oldArticle.setTags(article.getTags());
		articleAdminRepository.save(oldArticle);
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void remove(Integer id) {
		articleAdminRepository.delete(id);
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void remove(Integer[] ids) {
		for (Integer id : ids) {
			remove(id);
		}
	}
	
	@Override
	public Page<Article> page(final ArticleAdminVo adminArticleVO, final PageVo pageVo) {
		
		final Integer limit = StringUtils.isEmpty(pageVo.getPageNumber()) ? 0 : (pageVo.getPageNumber() - 1);
		final Integer offset = StringUtils.isEmpty(pageVo.getPageSize()) ? 10 : pageVo.getPageSize();
		final String sort = StringUtils.isEmpty(pageVo.getSort()) ? "createDate" : pageVo.getSort();
		
		return articleAdminRepository.findAll((root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (!StringUtils.isEmpty(adminArticleVO.getTitle())) {
				predicates.add(cb.like(root.<String>get("title"), "%" + adminArticleVO.getTitle() + "%"));
			}
			if (!StringUtils.isEmpty(adminArticleVO.getAuthor())) {
				Join<Article, User> userJoin = root.join(root.getModel().getSingularAttribute("author", User.class), JoinType.INNER);
				predicates.add(cb.like(userJoin.get("nickname").as(String.class), "%" + adminArticleVO.getAuthor() + "%"));
			}
			return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
		}, new PageRequest(limit, offset, new Sort(Direction.DESC, sort)));
	}

	@Override
	public List<Article> listByAuthorId(Long authorId) {
		User user = new User();
		user.setId(authorId);
		return articleAdminRepository.findByAuthor(user);
	}

}
