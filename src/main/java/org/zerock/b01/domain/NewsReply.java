package org.zerock.b01.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@Table(name = "newsReply", indexes = {
        @Index(name = "idx_newsReply_news_bno", columnList = "news_bno")
})
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "news")
public class NewsReply extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    @ManyToOne(fetch = FetchType.LAZY)
    private News news;

    private String newsReplyText;

    private String newsReplyer;

    public void changeText(String text){
        this.newsReplyText = text;
    }
}
