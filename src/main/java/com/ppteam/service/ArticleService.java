package com.ppteam.service;

import com.ppteam.dto.ArticleDto;

import java.util.List;

public interface ArticleService {

    /**
     * 增加文章
     * @param title
     * @param content
     * @param thumbnailName
     * @param topic
     * @return
     */
    boolean addArticle(String title,String content,String thumbnailName,String topic);

    /**
     * 获取单篇文章
     * @param id
     * @return
     */
    ArticleDto getArticle(int id);

    /**
     * 获取多篇文章，按时间排序
     * @param count
     * @param page
     * @return
     */
    List<ArticleDto> getLatestArticles(int count,int page);

    /**
     * 获取多篇文章，按热度(阅读数)排序
     * @param count
     * @param page
     * @return
     */
    List<ArticleDto> getHottestArticles(int count,int page);
}
