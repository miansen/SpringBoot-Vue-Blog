package com.shimh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.shimh.entity.SensitiveWord;

/**
 * @author miansen.wang
 * @date 2020-04-17
 */
public interface SensitiveWordRepository extends JpaRepository<SensitiveWord, Integer>, JpaSpecificationExecutor<SensitiveWord> {

}
