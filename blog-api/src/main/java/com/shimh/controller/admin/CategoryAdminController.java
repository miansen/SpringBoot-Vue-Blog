package com.shimh.controller.admin;

import org.apache.shiro.authz.annotation.RequiresRoles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shimh.common.annotation.LogAnnotation;
import com.shimh.common.result.Result;
import com.shimh.entity.Category;
import com.shimh.service.CategoryAdminService;
import com.shimh.vo.PageVo;

/**
 * 分类管理 API
 * 
 * @author miansen.wang
 * @date 2020-04-19
 */
@RestController
@RequestMapping(value = "/admin/category")
public class CategoryAdminController {

	@Autowired
    private CategoryAdminService categoryAdminService;
	
	@PostMapping("/save")
	@RequiresRoles(value = "admin")
	@LogAnnotation(module = "分类管理", operation = "添加分类")
	public Result save(@RequestBody Category category) {
		categoryAdminService.save(category);
		Result result = Result.success();
		result.simple().put("id", category.getId());
		return result;
	}
	
	@PostMapping("/update")
	@RequiresRoles(value = "admin")
	@LogAnnotation(module = "分类管理", operation = "更新分类")
	public Result update(@RequestBody Category category) {
		categoryAdminService.update(category);
		Result result = Result.success();
		result.simple().put("id", category.getId());
		return result;
	}
	
	@GetMapping(value = "/delete")
	@RequiresRoles(value = "admin")
	@LogAnnotation(module = "分类管理", operation = "删除分类")
	public Result delete(@RequestParam(name = "id") Integer id) {
		categoryAdminService.remove(id);
		return Result.success();
	}
	
	@GetMapping(value = "/delete/batch")
	@RequiresRoles(value = "admin")
	@LogAnnotation(module = "分类管理", operation = "批量删除分类")
	public Result batchDelete(@RequestParam(name = "ids") Integer[] ids) {
		categoryAdminService.remove(ids);
		return Result.success();
	}

	@GetMapping(value = "/list")
	@RequiresRoles(value = "admin")
	@LogAnnotation(module = "分类管理", operation = "查询分类")
	public Result list(Category category, PageVo pageVo) {
		Page<Category> page = categoryAdminService.page(category, pageVo);
		return Result.success(page);
	}
	
}
