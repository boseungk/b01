package org.zerock.b01.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.b01.domain.News;
import org.zerock.b01.dto.NewsListNewsReplyCountDTO;

public interface NewsSearch {

    Page<News> searchAll(String[] types, String keyword, Pageable pageable);

    Page<NewsListNewsReplyCountDTO> searchWithNewsReplyCount(String[] types,
                                                                String keyword,
                                                                Pageable pageable);


}
