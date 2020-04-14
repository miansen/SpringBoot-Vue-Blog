package com.shimh.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresAuthentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shimh.common.constant.ResultCode;
import com.shimh.common.result.Result;
import com.shimh.common.util.UserUtils;
import com.shimh.entity.Vote;
import com.shimh.service.VoteService;
import com.shimh.common.annotation.LogAnnotation;

@RestController
@RequestMapping(value = "/vote")
public class VoteController {

	private static final Logger logger = LoggerFactory.getLogger(VoteController.class);

	@Autowired
	private VoteService voteService;

	@PostMapping("/publish")
	@RequiresAuthentication
	@LogAnnotation(module = "投票", operation = "投票")
	public Result save(@Validated @RequestBody Vote vote) {
		try {
			Integer articleId = vote.getArticleId();
			Integer voteValue = vote.getVoteValue();
			Vote voted = null;
			Result result = Result.success();
			if (voteValue == null || voteValue <= 0) {
				voted = voteService.getByArticleIdAndUserId(articleId, UserUtils.getCurrentUser().getId().intValue());
				if (voted != null) {
					result.simple().put("value", voted.getVoteValue());
				} else {
					result.simple().put("value", 0);
				}
			} else {
				voteService.save(vote);
				result.simple().put("value", voteValue);
			}
			return result;
		} catch (Exception e) {
			logger.error("投票失败：" + e);
			return Result.error(ResultCode.ERROR);
		}
	}
	
	@GetMapping("/rate/{articleId}")
	@LogAnnotation(module = "投票", operation = "统计投票")
	public Result rate(@PathVariable(name = "articleId") Integer articleId) {
		Integer all = voteService.countByArticleIdAndVoteValueGreaterThan(articleId, 0);
		Integer good = voteService.countByArticleIdAndVoteValueGreaterThan(articleId, 3);
		Result result = Result.success();
		Map<String, Object> map = new HashMap<>();
		if (all == 0) {
			map.put("total", 0);
			map.put("rate", 0);
		} else {
			double a = all.doubleValue();
			double b = good.doubleValue();
			double rate = new BigDecimal(b / a * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			map.put("total", all);
			map.put("rate", rate);
		}
		result.setData(map);
		return result;
	}

}
