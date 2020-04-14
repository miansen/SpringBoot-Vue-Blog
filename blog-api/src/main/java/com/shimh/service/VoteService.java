package com.shimh.service;

import com.shimh.entity.Vote;

public interface VoteService {
	
	Vote getByArticleIdAndUserId(Integer articleId, Integer userId);
	
	void save(Vote vote) throws Exception;
	
	Integer countByArticleIdAndVoteValueGreaterThan(Integer articleId, Integer voteValue);
	
}
