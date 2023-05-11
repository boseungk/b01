package org.zerock.b01.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardListBoardReplyCountDTO {

    private Long bno;

    private String title;

    private String writer;

    private LocalDateTime regDate;

    private Long boardReplyCount;

}
