package com.shimh.vo;

import com.shimh.entity.Article;
import com.shimh.entity.User;

import java.util.List;

public class GuanzhuVo {

    private User my;

    private List<User> quanquan;

    public User getMy() {
        return my;
    }

    public void setMy(User my) {
        this.my = my;
    }

    public List<User> getQuanquan() {
        return quanquan;
    }

    public void setQuanquan(List<User> quanquan) {
        this.quanquan = quanquan;
    }
}
