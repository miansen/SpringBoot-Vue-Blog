package com.shimh.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.support.spring.annotation.FastJsonFilter;
import com.alibaba.fastjson.support.spring.annotation.FastJsonView;
import com.shimh.common.annotation.LogAnnotation;
import com.shimh.common.result.Result;
import com.shimh.entity.Article;
import com.shimh.entity.Tag;
import com.shimh.entity.User;
import com.shimh.service.ArticleService;
import com.shimh.vo.ArticleVo;
import com.shimh.vo.PageVo;

/**
 * 文章管理 API
 * 
 * @author miansen.wang
 * @date 2020-04-16
 */
@RestController
@RequestMapping(value = "/admin/article")
public class ArticleAdminController {

	@Autowired
    private ArticleService articleService;

	@RequestMapping(value = "/search")
	@FastJsonView(
			exclude = {
					@FastJsonFilter(clazz = Article.class, props = {"body", "comments"}),
                    @FastJsonFilter(clazz = Tag.class, props = {"id", "avatar"})
                    },
            include = {
            		@FastJsonFilter(clazz = User.class, props = {"nickname","id"})
            		})
	@LogAnnotation(module = "文章管理", operation = "搜索所有文章")
	public Result search(ArticleVo articleVo, PageVo pageVo) {
		Page<Article> page = articleService.page(articleVo, pageVo);
		return Result.success(page);
	}
}
