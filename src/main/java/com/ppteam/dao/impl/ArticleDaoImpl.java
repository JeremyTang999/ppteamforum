package com.ppteam.dao.impl;

import com.ppteam.dao.ArticleDao;
import com.ppteam.dto.ArticleDto;
import com.ppteam.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ArticleDaoImpl extends BaseDaoImpl<Article> implements ArticleDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ArticleDaoImpl(JdbcTemplate jdbcTemplate) {
        super(Article.class, jdbcTemplate, "id");
    }

    @Override
    public List<ArticleDto> getHottestArticles(int count, int page) {
        String sql="select id,title,thumbnail_name,creation_time,read_count,like_count "+
                "from article order by read_count desc limit ?,?";
        return jdbcTemplate.query(sql, new Object[]{((page - 1) * count), count}, new CustomRowMapper());
        //return null;
    }

    @Override
    public List<ArticleDto> getLatestArticles(int count, int page) {
        String sql="select id,title,thumbnail_name,creation_time,read_count,like_count "+
                "from article order by id desc limit ?,?";
        return jdbcTemplate.query(sql, new Object[]{((page - 1) * count), count},new CustomRowMapper());
    }
}

class CustomRowMapper implements RowMapper<ArticleDto>{
    @Override
    public ArticleDto mapRow(ResultSet rs, int i) throws SQLException {
        ArticleDto article=new ArticleDto();
        article.setId(rs.getInt("id"));
        article.setTitle(rs.getString("title"));
        article.setThumbnailName(rs.getString("thumbnail_name"));
        article.setCreationTime(rs.getLong("creation_time"));
        article.setReadCount(rs.getInt("read_count"));
        article.setLikeCount(rs.getInt("like_count"));
        return article;
    }
}