package org.zerock.b01.service;

import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.NewsReplyDTO;

public interface NewsReplyService {

    Long register(NewsReplyDTO NewsReplyDTO);

    NewsReplyDTO read(Long rno);

    void modify(NewsReplyDTO NewsReplyDTO);

    void remove(Long rno);

    PageResponseDTO<NewsReplyDTO> getListOfNews(Long bno, PageRequestDTO pageRequestDTO);

}
