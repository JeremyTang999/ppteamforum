package com.ppteam.dto;

import com.ppteam.entity.*;

public class ArticleDto {
    private Integer id;
    private String topic;
    private String title;
    private String thumbnailName;
    private String content;
    private Integer authorId;
    private Long creationTime;
    private Integer readCount;
    private Integer likeCount;

    public ArticleDto() {
    }

    public ArticleDto(Integer id, String topic, String title, String thumbnailName, String content, Integer authorId, Long creationTime, Integer readCount, Integer likeCount) {
        this.id = id;
        this.topic = topic;
        this.title = title;
        this.thumbnailName = thumbnailName;
        this.content = content;
        this.authorId = authorId;
        this.creationTime = creationTime;
        this.readCount = readCount;
        this.likeCount = likeCount;
    }

    public ArticleDto(Article article){
        id=new Integer(article.getId());
        topic=new String(article.getTopic().toString());
        title=new String(article.getTitle());
        thumbnailName =new String(article.getThumbnailName());
        content=new String(article.getContent());
        authorId=new Integer(article.getAuthorId());
        creationTime=new Long(article.getCreationTime());
        readCount=new Integer(article.getReadCount());
        likeCount=new Integer(article.getLikeCount());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Long creationTime) {
        this.creationTime = creationTime;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public String getThumbnailName() {
        return thumbnailName;
    }

    public void setThumbnailName(String thumbnailName) {
        this.thumbnailName = thumbnailName;
    }
}
