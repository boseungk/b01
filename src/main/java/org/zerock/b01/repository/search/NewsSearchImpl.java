package org.zerock.b01.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.b01.domain.News;
import org.zerock.b01.domain.NewsReply;
import org.zerock.b01.domain.QNews;
import org.zerock.b01.domain.QNewsReply;
import org.zerock.b01.dto.NewsListNewsReplyCountDTO;

import java.util.List;

public class NewsSearchImpl extends QuerydslRepositorySupport implements NewsSearch {

    public NewsSearchImpl(){
        super(News.class);
    }

    @Override
    public Page<News> searchAll(String[] types, String keyword, Pageable pageable) {

        QNews news = QNews.news;
        JPQLQuery<News> query = from(news);

        if( (types != null && types.length > 0) && keyword != null ){

            BooleanBuilder booleanBuilder = new BooleanBuilder(); // (

            for(String type: types){

                switch (type){
                    case "t":
                        booleanBuilder.or(news.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(news.content.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(news.writer.contains(keyword));
                        break;
                }
            }
            query.where(booleanBuilder);
        }

        query.where(news.bno.gt(0L));


        this.getQuerydsl().applyPagination(pageable, query);

        List<News> list = query.fetch();

        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);

    }

    @Override
    public Page<NewsListNewsReplyCountDTO> searchWithNewsReplyCount(String[] types, String keyword, Pageable pageable) {

        QNews news = QNews.news;
        QNewsReply newsReply = QNewsReply.newsReply;

        JPQLQuery<News> query = from(news);
        query.leftJoin(newsReply).on(newsReply.news.eq(news));

        query.groupBy(news);

        if( (types != null && types.length > 0) && keyword != null ){

            BooleanBuilder booleanBuilder = new BooleanBuilder(); // (

            for(String type: types){

                switch (type){
                    case "t":
                        booleanBuilder.or(news.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(news.content.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(news.writer.contains(keyword));
                        break;
                }
            }//end for
            query.where(booleanBuilder);
        }

        //bno > 0
        query.where(news.bno.gt(0L));

        JPQLQuery<NewsListNewsReplyCountDTO> dtoQuery = query.select(Projections.bean(NewsListNewsReplyCountDTO.class,
                news.bno,
                news.title,
                news.writer,
                news.regDate,
                newsReply.count().as("newsReplyCount")
        ));

        this.getQuerydsl().applyPagination(pageable,dtoQuery);

        List<NewsListNewsReplyCountDTO> dtoList = dtoQuery.fetch();

        long count = dtoQuery.fetchCount();

        return new PageImpl<>(dtoList, pageable, count);
    }


}
