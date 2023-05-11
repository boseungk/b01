package org.zerock.b01.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.NewsReplyDTO;
import org.zerock.b01.service.NewsReplyService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/newsReplies")
@Log4j2
@RequiredArgsConstructor
public class NewsReplyController {

    private final NewsReplyService newsReplyService;


    @ApiOperation(value = "NewsReplies POST", notes = "POST 방식으로 댓글 등록")
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,Long> register(
            @Valid @RequestBody NewsReplyDTO newsReplyDTO,
            BindingResult bindingResult)throws BindException{

        log.info(newsReplyDTO);

        if(bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }

        Map<String, Long> resultMap = new HashMap<>();

        Long rno = newsReplyService.register(newsReplyDTO);

        resultMap.put("rno",rno);

        return resultMap;
    }

    @ApiOperation(value = "NewsReplies of News", notes = "GET 방식으로 특정 게시물의 댓글 목록")
    @GetMapping(value = "/list/{bno}")
    public PageResponseDTO<NewsReplyDTO> getList(@PathVariable("bno") Long bno,
                                                  PageRequestDTO pageRequestDTO){

        PageResponseDTO<NewsReplyDTO> responseDTO = newsReplyService.getListOfNews(bno, pageRequestDTO);

        return responseDTO;
    }

    @ApiOperation(value = "Read NewsReply", notes = "GET 방식으로 특정 댓글 조회")
    @GetMapping("/{rno}")
    public NewsReplyDTO getNewsReplyDTO( @PathVariable("rno") Long rno ){

        NewsReplyDTO newsReplyDTO = newsReplyService.read(rno);

        return newsReplyDTO;
    }

    @ApiOperation(value = "Delete NewsReply", notes = "DELETE 방식으로 특정 댓글 삭제")
    @DeleteMapping("/{rno}")
    public Map<String,Long> remove( @PathVariable("rno") Long rno ){

        newsReplyService.remove(rno);

        Map<String, Long> resultMap = new HashMap<>();

        resultMap.put("rno", rno);

        return resultMap;
    }



    @ApiOperation(value = "Modify NewsReply", notes = "PUT 방식으로 특정 댓글 수정")
    @PutMapping(value = "/{rno}", consumes = MediaType.APPLICATION_JSON_VALUE )
    public Map<String,Long> remove( @PathVariable("rno") Long rno, @RequestBody NewsReplyDTO newsReplyDTO ){

        newsReplyDTO.setRno(rno); //번호를 일치시킴

        newsReplyService.modify(newsReplyDTO);

        Map<String, Long> resultMap = new HashMap<>();

        resultMap.put("rno", rno);

        return resultMap;
    }

}
