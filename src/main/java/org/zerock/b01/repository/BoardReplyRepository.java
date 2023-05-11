package org.zerock.b01.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.b01.domain.BoardReply;

public interface BoardReplyRepository extends JpaRepository<BoardReply, Long> {

    @Query("select r from BoardReply r where r.board.bno = :bno")
    Page<BoardReply> listOfBoard(@Param("bno") Long bno, Pageable pageable);
}
