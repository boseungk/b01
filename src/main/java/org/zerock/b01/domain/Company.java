package org.zerock.b01.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Company extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(length = 50, nullable = false)
    private String writer;

    @Column(length = 2000, nullable = false)
    private String content;


    public void change(String title, String content){
        this.title = title;
        this.content = content;
    }
}
