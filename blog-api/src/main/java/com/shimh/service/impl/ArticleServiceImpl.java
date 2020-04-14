package com.shimh.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import com.shimh.entity.*;
import com.shimh.repository.GuanzhuRepository;
import com.shimh.repository.UserRepository;
import com.shimh.vo.ArticleVo;
import com.shimh.vo.GuanzhuVo;
import com.shimh.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shimh.common.util.UserUtils;
import com.shimh.config.IntegralConfig;
import com.shimh.repository.ArticleRepository;
import com.shimh.service.ArticleService;

/**
 * @author shimh
 * <p>
 * 2018年1月25日
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GuanzhuRepository guanzhuRepository;

    @Autowired
    private IntegralConfig integralConfig;

    @Override
    public List<Article> listArticles(PageVo page) {

        return articleRepository.listArticles(page);
    }

    @Override
    public List<Article> listArticles(ArticleVo article, PageVo page) {

        return articleRepository.listArticles(article, page);
    }

    @Override
    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    @Override
    public Article getArticleById(Integer id) {
        return articleRepository.getOne(id);
    }

    @Override
    @Transactional
    public Integer publishArticle(Article article) {

        if (null != article.getId()) {
            return this.updateArticle(article);
        } else {
            return this.saveArticle(article);
        }

    }

    @Override
    @Transactional
    public Integer saveArticle(Article article) {

        User currentUser = UserUtils.getCurrentUser();

        if (null != currentUser) {
            article.setAuthor(currentUser);
        }

        article.setCreateDate(new Date());
        article.setWeight(Article.Article_Common);
        Integer articleId = articleRepository.save(article).getId();
        currentUser.setIntegral(currentUser.getIntegral() + integralConfig.getCreateArticle());
        userRepository.save(currentUser);
        return articleId;
    }

    //  void guanzhu(Integer userId);
    @Override
    @Transactional
    public Long guanzhu(Integer userId) {

        User currentUser = UserUtils.getCurrentUser();
        Guanzhu guanzhu = new Guanzhu();
        if (null != currentUser) {
            guanzhu.setUserme(currentUser.getId());
        }
        User one = userRepository.getOne(Long.valueOf(userId));
        guanzhu.setCreateDate(new Date());
        guanzhu.setAuthor(one.getId());

        return guanzhuRepository.save(guanzhu).getId();
    }

    @Override
    @Transactional
    public void deleteGuanzhuById(Integer authorId) {
        Guanzhu guanzhu1 = new Guanzhu();
        guanzhu1.setAuthor(Long.valueOf(authorId));

//将匹配对象封装成Example对象
        Example<Guanzhu> example = Example.of(guanzhu1);

        List<Guanzhu> all = guanzhuRepository.findAll(example);
        for (Guanzhu v : all
        ) {
            guanzhuRepository.delete(v);
        }


    }


    @Override
    public GuanzhuVo myquanquan() {

        User currentUser = UserUtils.getCurrentUser();
        GuanzhuVo guanzhu = new GuanzhuVo();
        if (null != currentUser) {
            guanzhu.setMy(currentUser);
        }


        Guanzhu guanzhu1 = new Guanzhu();
        guanzhu1.setUserme(currentUser.getId());

        //将匹配对象封装成Example对象
        Example<Guanzhu> example = Example.of(guanzhu1);
        List<User> quanquan = new ArrayList<>();
        List<Guanzhu> all = guanzhuRepository.findAll(example);
        for (Guanzhu v : all
        ) {
            User one = userRepository.getOne(Long.valueOf(v.getAuthor()));

            quanquan.add(one);

        }
        LinkedHashSet<User> hashSet = new LinkedHashSet<User>(quanquan);

        ArrayList<User> listWithoutDuplicates = new ArrayList<>(hashSet);


        guanzhu.setQuanquan(listWithoutDuplicates);
        return guanzhu;
    }

    @Override
    @Transactional
    public Integer updateArticle(Article article) {
        Article oldArticle = articleRepository.getOne(article.getId());

        oldArticle.setTitle(article.getTitle());
        oldArticle.setSummary(article.getSummary());
        oldArticle.setBody(article.getBody());
        oldArticle.setCategory(article.getCategory());
        oldArticle.setTags(article.getTags());

        return oldArticle.getId();
    }

    @Override
    @Transactional
    public void deleteArticleById(Integer id) {
    	User currentUser = UserUtils.getCurrentUser();
    	currentUser.setIntegral(currentUser.getIntegral() - integralConfig.getDeleteArticle());
        userRepository.save(currentUser);
        articleRepository.delete(id);
    }

    @Override
    public List<Article> listArticlesByTag(Integer id) {
        Tag t = new Tag();
        t.setId(id);
        return articleRepository.findByTags(t);
    }

    @Override
    public List<Article> listArticlesByCategory(Integer id) {
        Category c = new Category();
        c.setId(id);

        return articleRepository.findByCategory(c);
    }

    @Override
    @Transactional
    public Article getArticleAndAddViews(Integer id) {
        int count = 1;
        Article article = articleRepository.getOne(id);
        article.setViewCounts(article.getViewCounts() + count);
        return article;
    }

    @Override
    public List<Article> listHotArticles(int limit) {

        return articleRepository.findOrderByViewsAndLimit(limit);
    }

    @Override
    public List<Article> listNewArticles(int limit) {

        return articleRepository.findOrderByCreateDateAndLimit(limit);
    }

    @Override
    public List<ArticleVo> listArchives(Integer id) {

        return articleRepository.listArchives(id);
    }
}
