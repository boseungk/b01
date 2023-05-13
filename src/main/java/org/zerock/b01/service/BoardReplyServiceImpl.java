package org.zerock.b01.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.BoardReply;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.BoardReplyDTO;
import org.zerock.b01.repository.BoardReplyRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Log4j2
public class BoardReplyServiceImpl implements BoardReplyService{

    private final BoardReplyRepository boardReplyRepository;

    private final ModelMapper modelMapper;

    @Override
    public Long register(BoardReplyDTO boardReplyDTO) {

        BoardReply boardReply = modelMapper.map(boardReplyDTO, BoardReply.class);

        Long rno = boardReplyRepository.save(boardReply).getRno();

        return rno;
    }

    @Override
    public BoardReplyDTO read(Long rno) {

        Optional<BoardReply> boardReplyOptional = boardReplyRepository.findById(rno);

        BoardReply boardReply = boardReplyOptional.orElseThrow(() -> new NoSuchElementException());

        return modelMapper.map(boardReply, BoardReplyDTO.class);
    }

    @Override
    public void modify(BoardReplyDTO boardReplyDTO) {

        Optional<BoardReply> boardReplyOptional = boardReplyRepository.findById(boardReplyDTO.getRno());

        BoardReply boardReply = boardReplyOptional.orElseThrow(() -> new NoSuchElementException());

        boardReply.changeText(boardReplyDTO.getBoardReplyText());

        boardReplyRepository.save(boardReply);

    }
    @Override
    public void remove(Long rno) {

        boardReplyRepository.deleteById(rno);

    }
    @Override
    public PageResponseDTO<BoardReplyDTO> getListOfBoard(Long bno, PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() <=0? 0: pageRequestDTO.getPage() -1,
                pageRequestDTO.getSize(),
                Sort.by("rno").ascending());

        Page<BoardReply> result = boardReplyRepository.listOfBoard(bno, pageable);

        List<BoardReplyDTO> dtoList =
                result.getContent().stream().map(boardReply -> modelMapper.map(boardReply, BoardReplyDTO.class))
                        .collect(Collectors.toList());

        return PageResponseDTO.<BoardReplyDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }
}
