package org.zerock.b01.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.b01.domain.Board;
import org.zerock.b01.domain.News;
import org.zerock.b01.repository.search.BoardSearch;
import org.zerock.b01.repository.search.NewsSearch;

public interface NewsRepository extends JpaRepository<News, Long>, NewsSearch {


}
