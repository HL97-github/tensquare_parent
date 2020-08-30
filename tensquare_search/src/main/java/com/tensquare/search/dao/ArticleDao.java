package com.tensquare.search.dao;

import com.tensquare.search.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 *  文章数据访问接口
 */
public interface ArticleDao extends ElasticsearchRepository<Article,String> {


    public Page<Article> findByTitleOrContent(String title, String content, Pageable pageable);

}
