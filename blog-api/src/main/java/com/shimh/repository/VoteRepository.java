package com.shimh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shimh.entity.Vote;

public interface VoteRepository extends JpaRepository<Vote, Integer> {

	Vote findByArticleIdAndUserId(Integer articleId, Integer userId);
	
	Integer countByArticleIdAndVoteValueGreaterThan(Integer articleId, Integer voteValue);
	
}
