package com.shimh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.shimh.entity.Article;
import com.shimh.entity.User;

/**
 * 文章管理 Repository
 * 
 * @author miansen.wang
 * @date 2020-04-17
 */
public interface ArticleAdminRepository extends JpaRepository<Article, Integer>, JpaSpecificationExecutor<Article> {

	List<Article> findByAuthor(User user);
}
