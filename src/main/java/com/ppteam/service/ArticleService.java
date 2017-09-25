package com.ppteam.service;

import com.ppteam.dto.ArticleDto;
import com.ppteam.entity.Topic;

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
     * @param increaseReadCount 是否增加阅读数
     * @return
     */
    ArticleDto getArticle(int id,boolean increaseReadCount);

    /**
     * 获取多篇文章，按时间排序
     * @param count
     * @param page
     * @return
     */
    List<ArticleDto> getLatestArticles(int count,int page,String topic);

    /**
     * 获取多篇文章，按热度(阅读数)排序
     * @param count
     * @param page
     * @return
     */
    List<ArticleDto> getHottestArticles(int count,int page,String topic);

    /**
     * 获取页数
     * @param topic 文章板块
     * @param countPerPage 每页文章数
     * @return
     */
    Integer getPageCount(String topic,Integer countPerPage);
}
