package com.ppteam.dao.impl;

import com.ppteam.dao.ArticleDao;
import com.ppteam.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleDaoImpl extends BaseDaoImpl<Article> implements ArticleDao{

    @Autowired
    public ArticleDaoImpl(JdbcTemplate jdbcTemplate) {
        super(Article.class, jdbcTemplate, "id");
    }
}
