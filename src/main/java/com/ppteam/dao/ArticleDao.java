package com.ppteam.dao;

import com.ppteam.dto.ArticleDto;
import com.ppteam.entity.Article;
import com.ppteam.entity.Topic;

import java.util.List;

public interface ArticleDao extends BaseDao<Article> {
    List<ArticleDto> getHottestArticles(int count,int page,Topic topic);
    List<ArticleDto> getLatestArticles(int count,int page,Topic topic);
    Integer getCount(Topic topic);
}
