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
import com.shimh.entity.SensitiveWord;
import com.shimh.service.SensitiveWordService;
import com.shimh.vo.PageVo;

/**
 * 用户管理 API
 * 
 * @author miansen.wang
 * @date 2020-04-16
 */
@RestController
@RequestMapping(value = "/admin/sensitiveword")
public class SensitiveWordAdminController {

	@Autowired
    private SensitiveWordService sensitiveWordAdminService;
	
	@PostMapping("/save")
	@RequiresRoles(value = "admin")
	@LogAnnotation(module = "敏感词管理", operation = "添加敏感词")
	public Result save(@RequestBody SensitiveWord sensitiveWord) {
		sensitiveWordAdminService.save(sensitiveWord);
		Result result = Result.success();
		result.simple().put("id", sensitiveWord.getId());
		return result;
	}
	
	@PostMapping("/update")
	@RequiresRoles(value = "admin")
	@LogAnnotation(module = "敏感词管理", operation = "更新敏感词")
	public Result update(@RequestBody SensitiveWord sensitiveWord) {
		sensitiveWordAdminService.update(sensitiveWord);
		Result result = Result.success();
		result.simple().put("id", sensitiveWord.getId());
		return result;
	}
	
	@GetMapping(value = "/delete")
	@RequiresRoles(value = "admin")
	@LogAnnotation(module = "敏感词管理", operation = "删除敏感词")
	public Result delete(@RequestParam(name = "id") Integer id) {
		sensitiveWordAdminService.remove(id);
		return Result.success();
	}
	
	@GetMapping(value = "/delete/batch")
	@RequiresRoles(value = "admin")
	@LogAnnotation(module = "敏感词管理", operation = "批量删除敏感词")
	public Result batchDelete(@RequestParam(name = "ids") Integer[] ids) {
		sensitiveWordAdminService.remove(ids);
		return Result.success();
	}

	@GetMapping(value = "/list")
	@RequiresRoles(value = "admin")
	@LogAnnotation(module = "敏感词管理", operation = "查询敏感词")
	public Result list(SensitiveWord sensitiveWord, PageVo pageVo) {
		Page<SensitiveWord> page = sensitiveWordAdminService.page(sensitiveWord, pageVo);
		return Result.success(page);
	}
	
}
