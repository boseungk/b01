package org.zerock.b01.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
//@Table(name = "boardReply", indexes = {
//        @Index(name = "idx_boardReply_board_bno", columnList = "board_bno")
//})
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "board")
public class BoardReply extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    private String boardReplyText;

    private String boardReplyer;

    public void changeText(String text){
        this.boardReplyText = text;
    }
}
