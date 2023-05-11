package org.zerock.b01.service;

import org.zerock.b01.dto.*;

public interface NewsService {

    Long register(NewsDTO newsDTO);

    NewsDTO readOne(Long bno);

    int modify(NewsDTO newsDTO);

    void remove(Long bno);

    PageResponseDTO<NewsDTO> list(PageRequestDTO pageRequestDTO);

    PageResponseDTO<NewsListNewsReplyCountDTO> listWithNewsReplyCount(PageRequestDTO pageRequestDTO);

}
