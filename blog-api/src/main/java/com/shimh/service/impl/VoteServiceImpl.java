package com.shimh.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shimh.common.util.UserUtils;
import com.shimh.entity.Article;
import com.shimh.entity.User;
import com.shimh.entity.Vote;
import com.shimh.repository.VoteRepository;
import com.shimh.service.ArticleService;
import com.shimh.service.VoteService;

@Service
public class VoteServiceImpl implements VoteService {

	@Autowired
	private VoteRepository voteRepository;

	@Autowired
	private ArticleService articleService;

	@Override
	public Vote getByArticleIdAndUserId(Integer articleId, Integer userId) {
		return voteRepository.findByArticleIdAndUserId(articleId, userId);
	}
	
	@Transactional
	@Override
	public void save(Vote vote) throws Exception {
		User user = UserUtils.getCurrentUser();
		Integer articleId = vote.getArticleId();
		Integer voteValue = vote.getVoteValue();
		if (user == null) {
			throw new IllegalAccessException("请先登录");
		}
		if (articleId == null || articleId <= 0) {
			throw new IllegalArgumentException("非法的文章ID");
		}
		if (voteValue == null || voteValue <= 0) {
			throw new IllegalArgumentException("非法的投票结果");
		}
		Article article = articleService.getArticleById(articleId);
		if (article == null) {
			throw new IllegalArgumentException("文章不存在");
		}
		Vote updateVote = getByArticleIdAndUserId(articleId, user.getId().intValue());
		if (updateVote != null) {
			updateVote.setVoteValue(voteValue);
			updateVote.setUpdateDate(new Date());
			voteRepository.save(updateVote);
		} else {
			vote.setUserId(user.getId().intValue());
			vote.setCreateDate(new Date());
			voteRepository.save(vote);
		}
	}

	@Override
	public Integer countByArticleIdAndVoteValueGreaterThan(Integer articleId, Integer voteValue) {
		return voteRepository.countByArticleIdAndVoteValueGreaterThan(articleId, voteValue);
	}
	
}
