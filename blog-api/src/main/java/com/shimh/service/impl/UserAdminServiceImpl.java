package com.shimh.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.shimh.common.util.PasswordHelper;
import com.shimh.entity.Article;
import com.shimh.entity.User;
import com.shimh.repository.UserAdminRepository;
import com.shimh.service.ArticleAdminService;
import com.shimh.service.UserAdminService;
import com.shimh.vo.PageVo;
import com.shimh.vo.UserAdminVo;

/**
 * 用户管理 Service 实现
 * 
 * @author miansen.wang
 * @date 2020-04-18
 */
@Service
public class UserAdminServiceImpl implements UserAdminService {

	
	@Autowired
	private UserAdminRepository userAdminRepository;
	
	@Autowired
	private  ArticleAdminService articleAdminService;
	
	@Transactional(rollbackOn = Exception.class)
	@Override
	public void save(User user) {
		PasswordHelper.encryptPassword(user);
        int index = new Random().nextInt(6) + 1;
        String avatar = "/static/user/user_" + index + ".png";
        user.setAvatar(avatar);
        user.setIntegral(0);
        user.setCreateDate(new Date());
		userAdminRepository.save(user);
	}

	@Transactional(rollbackOn = Exception.class)
	@Override
	public void update(User user) {
		userAdminRepository.save(user);
		
	}

	@Transactional(rollbackOn = Exception.class)
	@Override
	public void remove(Long id) {
		User user = getbyId(id);
		if (user == null) {
			throw new IllegalArgumentException("用户不存在");
		}
		List<Article> articles = articleAdminService.listByAuthorId(id);
		if (!CollectionUtils.isEmpty(articles)) {
			throw new IllegalArgumentException("请先删除用户的文章 ");
		}
		userAdminRepository.delete(id);
	}

	@Transactional(rollbackOn = Exception.class)
	@Override
	public void remove(Long[] ids) {
		for (Long id : ids) {
			remove(id);
		}
	}
	
	@Override
	public User getbyId(Long id) {
		return userAdminRepository.findOne(id);
	}
	
	@Override
	public Page<User> page(UserAdminVo userAdminVo, PageVo pageVo) {
		
		final Integer limit = StringUtils.isEmpty(pageVo.getPageNumber()) ? 0 : (pageVo.getPageNumber() - 1);
		final Integer offset = StringUtils.isEmpty(pageVo.getPageSize()) ? 10 : pageVo.getPageSize();
		final String sort = StringUtils.isEmpty(pageVo.getSort()) ? "createDate" : pageVo.getSort();
		
		return userAdminRepository.findAll((root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (!StringUtils.isEmpty(userAdminVo.getAccount())) {
				predicates.add(cb.like(root.<String>get("account"), "%" + userAdminVo.getAccount() + "%"));
			}
			if (!StringUtils.isEmpty(userAdminVo.getNickname())) {
				predicates.add(cb.like(root.<String>get("nickname"), "%" + userAdminVo.getNickname() + "%"));
			}
			return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
		}, new PageRequest(limit, offset, new Sort(Direction.DESC, sort)));
		
	}

}
