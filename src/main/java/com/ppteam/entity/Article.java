package com.ppteam.entity;

public class Article {
    private Integer id;
    private Topic topic;
    private String title;
    private String content;
    private Integer authorId;
    private Long creationTime;
    private Integer readCount=0;
    private Integer likeCount=0;

    public Article() {
    }

    public Article(Integer id, Topic topic, String title, String content, Integer authorId, Long creationTime, Integer readCount, Integer likeCount) {
        this.id = id;
        this.topic = topic;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.creationTime = creationTime;
        this.readCount = readCount;
        this.likeCount = likeCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
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
}
