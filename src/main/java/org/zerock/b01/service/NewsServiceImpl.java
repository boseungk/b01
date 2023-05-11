package org.zerock.b01.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.News;
import org.zerock.b01.dto.*;
import org.zerock.b01.repository.NewsRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class NewsServiceImpl implements NewsService{

    private final ModelMapper modelMapper;

    private final NewsRepository newsRepository;

    @Override
    public Long register(NewsDTO newsDTO) {

        News news = modelMapper.map(newsDTO, News.class);

        Long bno = newsRepository.save(news).getBno();

        return bno;
    }

    @Override
    public NewsDTO readOne(Long bno) {

        Optional<News> result = newsRepository.findById(bno);

        News news = result.orElseThrow(() -> new NoSuchElementException());

        NewsDTO newsDTO = modelMapper.map(news, NewsDTO.class);

        return newsDTO;
    }

    @Override
    public int modify(NewsDTO newsDTO) {

        Optional<News> result = newsRepository.findById(newsDTO.getBno());

        News news = result.orElseThrow(() -> new NoSuchElementException());

        if(newsDTO.getPassword().isEmpty() || !news.getPassword().equals(newsDTO.getPassword())){// 로그인 실패
            return 1;
        }

        news.change(newsDTO.getTitle(), newsDTO.getContent());

        newsRepository.save(news);

        return 0;
    }

    @Override
    public void remove(Long bno) {

        newsRepository.deleteById(bno);

    }


    @Override
    public PageResponseDTO<NewsDTO> list(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");

        Page<News> result = newsRepository.searchAll(types, keyword, pageable);

        List<NewsDTO> dtoList = result.getContent().stream()
                .map(news -> modelMapper.map(news, NewsDTO.class)).collect(Collectors.toList());


        return PageResponseDTO.<NewsDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();

    }

    @Override
    public PageResponseDTO<NewsListNewsReplyCountDTO> listWithNewsReplyCount(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");

        Page<NewsListNewsReplyCountDTO> result = newsRepository.searchWithNewsReplyCount(types, keyword, pageable);

        return PageResponseDTO.<NewsListNewsReplyCountDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.getContent())
                .total((int)result.getTotalElements())
                .build();
    }


}
