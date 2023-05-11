package org.zerock.b01.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsDTO {

    private Long bno;

    @NotEmpty(message = "제목을 적어주세요.")
    private String title;

    @NotEmpty(message = "내용을 적어주세요.")
    private String content;

    @NotEmpty(message = "사용자ID는 필수항목입니다.")
    private String writer;

    private String password;

    private LocalDateTime regDate;

    private LocalDateTime modDate;
}
