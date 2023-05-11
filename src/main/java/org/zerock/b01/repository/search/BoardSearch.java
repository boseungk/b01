package org.zerock.b01.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.b01.domain.Board;
import org.zerock.b01.dto.BoardListBoardReplyCountDTO;

public interface BoardSearch {

    Page<Board> searchAll(String[] types, String keyword, Pageable pageable);

    Page<BoardListBoardReplyCountDTO> searchWithBoardReplyCount(String[] types,
                                                           String keyword,
                                                           Pageable pageable);

}
