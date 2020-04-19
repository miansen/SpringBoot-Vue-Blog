package com.shimh.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotBlank;

import com.alibaba.fastjson.annotation.JSONField;
import com.shimh.common.entity.BaseEntity;

/**
 * 文章分类
 *
 * @author shimh
 * <p>
 * 2018年1月23日
 */
@Entity
@Table(name = "me_category")
public class Category extends BaseEntity<Integer> {

    /**
     *
     */
    private static final long serialVersionUID = 5025313969040054182L;

    @NotBlank
    private String categoryname;

    private String description;

    private String avatar;
    
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "create_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
    
}
