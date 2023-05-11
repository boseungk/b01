package org.zerock.b01.service;

import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.BoardReplyDTO;

public interface BoardReplyService {

    Long register(BoardReplyDTO BoardReplyDTO);

    BoardReplyDTO read(Long rno);

    void modify(BoardReplyDTO BoardReplyDTO);

    void remove(Long rno);

    PageResponseDTO<BoardReplyDTO> getListOfBoard(Long bno, PageRequestDTO pageRequestDTO);

}
