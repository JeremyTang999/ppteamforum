package com.ppteam.dao.impl;

import com.ppteam.dao.ArticleDao;
import com.ppteam.dto.ArticleDto;
import com.ppteam.entity.Article;
import com.ppteam.entity.Topic;
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

    /**
     *
     * @param count
     * @param page
     * @param topic 为空则对所有文章搜索
     * @return
     */
    @Override
    public List<ArticleDto> getHottestArticles(int count, int page,Topic topic) {
        String sql="select id,title,thumbnail_name,creation_time,read_count,like_count "+
                "from article ";
        Object[] objs;
        if(topic==null){
            sql+="order by read_count desc,id desc limit ?,?";
            objs=new Object[]{((page - 1) * count), count};
        }
        else {
            sql+="where topic=? order by read_count desc,id desc limit ?,?";
            objs=new Object[]{topic.toString(),((page - 1) * count), count};
        }

        return jdbcTemplate.query(sql, objs, new CustomRowMapper());
        //return null;
    }

    @Override
    public List<ArticleDto> getLatestArticles(int count, int page,Topic topic) {
        String sql="select id,title,thumbnail_name,creation_time,read_count,like_count "+
                "from article ";
        Object[] objs;
        if(topic==null){
            sql+="order by id desc limit ?,?";
            objs=new Object[]{((page - 1) * count), count};
        }
        else {
            sql+="where topic=? order by id desc limit ?,?";
            objs=new Object[]{topic.toString(),((page - 1) * count), count};
        }

        return jdbcTemplate.query(sql, objs, new CustomRowMapper());
    }

    /**
     *
     * @param topic 为空则对所有文章搜索
     * @return
     */
    @Override
    public Integer getCount(Topic topic) {
        String sql="select count(id) from article ";
        Object[] obj={};
        if(topic!=null){
            sql+="where topic = ?";
            obj=new Object[]{topic.toString()};
        }
        List<Integer> r=jdbcTemplate.query(sql,obj,
                new RowMapper<Integer>() {
                    @Override
                    public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                        return resultSet.getInt("count(id)");
                    }
                });
        if(r.size()==1)
            return r.get(0);
        else
            return null;
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