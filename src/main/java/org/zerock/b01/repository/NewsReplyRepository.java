package org.zerock.b01.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.b01.domain.NewsReply;

public interface NewsReplyRepository extends JpaRepository<NewsReply, Long> {

    @Query("select r from NewsReply r where r.news.bno = :bno")
    Page<NewsReply> listOfNews(@Param("bno") Long bno, Pageable pageable);
}
