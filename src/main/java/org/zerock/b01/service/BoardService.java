package org.zerock.b01.service;

import org.zerock.b01.dto.BoardDTO;
import org.zerock.b01.dto.BoardListBoardReplyCountDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;

public interface BoardService {

    Long register(BoardDTO boardDTO);

    BoardDTO readOne(Long bno);

    int modify(BoardDTO boardDTO);

    void remove(Long bno);

    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);

    PageResponseDTO<BoardListBoardReplyCountDTO> listWithBoardReplyCount(PageRequestDTO pageRequestDTO);
}
