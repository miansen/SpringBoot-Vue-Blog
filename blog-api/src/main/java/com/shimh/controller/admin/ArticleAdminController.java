package com.shimh.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.support.spring.annotation.FastJsonFilter;
import com.alibaba.fastjson.support.spring.annotation.FastJsonView;

import com.shimh.common.annotation.LogAnnotation;
import com.shimh.common.result.Result;
import com.shimh.entity.Article;
import com.shimh.entity.Tag;
import com.shimh.entity.User;
import com.shimh.service.ArticleAdminService;
import com.shimh.vo.ArticleAdminVo;
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
    private ArticleAdminService articleAdminService;
	
	@PostMapping("/update")
	@RequiresRoles(value = "admin")
	@LogAnnotation(module = "文章管理", operation = "更新文章")
	public Result update(@RequestBody Article article) {
		articleAdminService.update(article);
		Result result = Result.success();
		result.simple().put("id", article.getId());
		return result;
	}
	
	@GetMapping(value = "/delete")
	@RequiresRoles(value = "admin")
	@LogAnnotation(module = "文章管理", operation = "删除文章")
	public Result delete(@RequestParam(name = "id") Integer id) {
		articleAdminService.remove(id);
		return Result.success();
	}
	
	@GetMapping(value = "/delete/batch")
	@RequiresRoles(value = "admin")
	@LogAnnotation(module = "文章管理", operation = "批量删除文章")
	public Result batchDelete(@RequestParam(name = "ids") Integer[] ids) {
		articleAdminService.remove(ids);
		return Result.success();
	}

	@GetMapping(value = "/list")
	@RequiresRoles(value = "admin")
	@FastJsonView(
			exclude = {
					@FastJsonFilter(clazz = Article.class, props = {"comments"})
                    },
            include = {
            		@FastJsonFilter(clazz = User.class, props = {"nickname","id"})
            		})
	@LogAnnotation(module = "文章管理", operation = "查询文章")
	public Result list(ArticleAdminVo adminArticleVO, PageVo pageVo, HttpServletRequest request, HttpSession session) {
		Page<Article> page = articleAdminService.page(adminArticleVO, pageVo);
		HttpSession session2 = request.getSession();
		return Result.success(page);
	}
}
