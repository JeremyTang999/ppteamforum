package com.ppteam.service.impl;

import com.ppteam.dao.ArticleDao;
import com.ppteam.dao.exceptions.DaoQueryFailException;
import com.ppteam.dao.exceptions.DaoUpdateFailException;
import com.ppteam.dao.exceptions.MoreThanOneResultException;
import com.ppteam.dto.ArticleDto;
import com.ppteam.entity.Article;
import com.ppteam.entity.Topic;
import com.ppteam.service.ArticleService;
import com.ppteam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private UserService userService;

    @Override
    public boolean addArticle(String title, String content,String thumbnailName,String topic) {
        Article article=new Article(
                null,
                Topic.fromString(topic),
                title,
                thumbnailName,
                content,
                userService.getUserIdFromSecurity(),
                System.currentTimeMillis(),
                0,
                0
        );
        try {
            articleDao.add(article);
        } catch (DaoUpdateFailException | MoreThanOneResultException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public ArticleDto getArticle(int id,boolean increaseReadCount) {
        ArticleDto articleDto;
        try {
            Article article=articleDao.get(id);
            if(article==null)
                return null;
            articleDto=new ArticleDto(article);

            if(increaseReadCount) {
                article.setReadCount(article.getReadCount() + 1);
                articleDao.update(article);
                articleDto.setReadCount(articleDto.getReadCount()+1);
            }

            return articleDto;
        } catch (DaoQueryFailException | MoreThanOneResultException | DaoUpdateFailException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ArticleDto> getLatestArticles(int count, int page,String topic) {
        return articleDao.getLatestArticles(count,page,Topic.fromString(topic));
    }

    @Override
    public List<ArticleDto> getHottestArticles(int count, int page,String topic) {
        return articleDao.getHottestArticles(count,page,Topic.fromString(topic));
    }

    @Override
    public Integer getPageCount(String topic, Integer countPerPage) {
        Integer count=articleDao.getCount(Topic.fromString(topic));
        if(count==null)
            return null;

        return count/countPerPage+1;
    }
}
