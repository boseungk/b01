package org.zerock.b01.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.NewsReply;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.NewsReplyDTO;
import org.zerock.b01.repository.NewsReplyRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Log4j2
public class NewsReplyServiceImpl implements NewsReplyService{

    private final NewsReplyRepository newsReplyRepository;

    private final ModelMapper modelMapper;

    @Override
    public Long register(NewsReplyDTO newsReplyDTO) {

        NewsReply newsReply = modelMapper.map(newsReplyDTO, NewsReply.class);

        Long rno = newsReplyRepository.save(newsReply).getRno();

        return rno;
    }

    @Override
    public NewsReplyDTO read(Long rno) {

        Optional<NewsReply> newsReplyOptional = newsReplyRepository.findById(rno);

        NewsReply newsReply = newsReplyOptional.orElseThrow(() -> new NoSuchElementException());

        return modelMapper.map(newsReply, NewsReplyDTO.class);
    }

    @Override
    public void modify(NewsReplyDTO newsReplyDTO) {

        Optional<NewsReply> newsReplyOptional = newsReplyRepository.findById(newsReplyDTO.getRno());

        NewsReply newsReply = newsReplyOptional.orElseThrow(() -> new NoSuchElementException());

        newsReply.changeText(newsReplyDTO.getNewsReplyText());

        newsReplyRepository.save(newsReply);

    }

    @Override
    public void remove(Long rno) {

        newsReplyRepository.deleteById(rno);

    }

    @Override
    public PageResponseDTO<NewsReplyDTO> getListOfNews(Long bno, PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() <=0? 0: pageRequestDTO.getPage() -1,
                pageRequestDTO.getSize(),
                Sort.by("rno").ascending());

        Page<NewsReply> result = newsReplyRepository.listOfNews(bno, pageable);

        List<NewsReplyDTO> dtoList =
                result.getContent().stream().map(newsReply -> modelMapper.map(newsReply, NewsReplyDTO.class))
                        .collect(Collectors.toList());

        return PageResponseDTO.<NewsReplyDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }
}
