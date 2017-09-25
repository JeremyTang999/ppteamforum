package com.ppteam.controller.api;

import com.ppteam.dto.ArticleDto;
import com.ppteam.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/article/{id}",method = RequestMethod.GET)
    public ResponseEntity getArticle(@PathVariable("id") int id,
                                     @RequestParam(value = "increase_read_count",required = false)Boolean increaseReadCount){
        ArticleDto article=articleService.getArticle(id,
                increaseReadCount==null ? false : increaseReadCount);
        return article!=null ?
                new ResponseEntity(article, HttpStatus.OK) :
                new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/article",method = RequestMethod.POST)
    public ResponseEntity postArticle(@RequestBody ArticleDto articleDto){
        boolean status=articleService.addArticle(
                articleDto.getTitle(),
                articleDto.getContent(),
                articleDto.getThumbnailName(),
                articleDto.getTopic()
        );
        return new ResponseEntity(
                status ?
                HttpStatus.OK :
                HttpStatus.BAD_REQUEST
        );
    }

    @RequestMapping(value = "/articles",method = RequestMethod.GET)
    public List<ArticleDto> getArticles(@RequestParam("order") String order,
                                @RequestParam(value = "topic",required = false) String topic,
                                @RequestParam("count") int count,@RequestParam("page") int page){

        if(order.equals("latest")){
            return articleService.getLatestArticles(count,page,topic);
        }
        else if (order.equals("hottest")) {
            return articleService.getHottestArticles(count, page,topic);
        }
        return null;
    }

    @RequestMapping("/articles/page_count")
    public ResponseEntity getPageCount(@RequestParam(value = "topic",required = false) String topic,
                               @RequestParam("count_per_page") Integer countPerPage){
        Integer count=articleService.getPageCount(topic,countPerPage);

        return count==null ?
                new ResponseEntity(HttpStatus.BAD_REQUEST) :
                new ResponseEntity(count,HttpStatus.OK);
    }
}
