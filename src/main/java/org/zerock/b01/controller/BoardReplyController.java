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
import org.zerock.b01.dto.BoardReplyDTO;
import org.zerock.b01.service.BoardReplyService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/boardReplies")
@Log4j2
@RequiredArgsConstructor
public class BoardReplyController {

    private final BoardReplyService boardReplyService;


    @ApiOperation(value = "BoardReplies POST", notes = "POST 방식으로 댓글 등록")
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,Long> register(
            @Valid @RequestBody BoardReplyDTO boardReplyDTO,
            BindingResult bindingResult)throws BindException{

        log.info(boardReplyDTO);

        if(bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }

        Map<String, Long> resultMap = new HashMap<>();

        Long rno = boardReplyService.register(boardReplyDTO);

        resultMap.put("rno",rno);

        return resultMap;
    }

    @ApiOperation(value = "BoardReplies of Board", notes = "GET 방식으로 특정 게시물의 댓글 목록")
    @GetMapping(value = "/list/{bno}")
    public PageResponseDTO<BoardReplyDTO> getList(@PathVariable("bno") Long bno,
                                             PageRequestDTO pageRequestDTO){

        PageResponseDTO<BoardReplyDTO> responseDTO = boardReplyService.getListOfBoard(bno, pageRequestDTO);

        return responseDTO;
    }

    @ApiOperation(value = "Read BoardReply", notes = "GET 방식으로 특정 댓글 조회")
    @GetMapping("/{rno}")
    public BoardReplyDTO getBoardReplyDTO( @PathVariable("rno") Long rno ){

        BoardReplyDTO boardReplyDTO = boardReplyService.read(rno);

        return boardReplyDTO;
    }

    @ApiOperation(value = "Delete BoardReply", notes = "DELETE 방식으로 특정 댓글 삭제")
    @DeleteMapping("/{rno}")
    public Map<String,Long> remove( @PathVariable("rno") Long rno ){

        boardReplyService.remove(rno);

        Map<String, Long> resultMap = new HashMap<>();

        resultMap.put("rno", rno);

        return resultMap;
    }



    @ApiOperation(value = "Modify BoardReply", notes = "PUT 방식으로 특정 댓글 수정")
    @PutMapping(value = "/{rno}", consumes = MediaType.APPLICATION_JSON_VALUE )
    public Map<String,Long> remove( @PathVariable("rno") Long rno, @RequestBody BoardReplyDTO boardReplyDTO ){

        boardReplyDTO.setRno(rno); //번호를 일치시킴

        boardReplyService.modify(boardReplyDTO);

        Map<String, Long> resultMap = new HashMap<>();

        resultMap.put("rno", rno);

        return resultMap;
    }

}
