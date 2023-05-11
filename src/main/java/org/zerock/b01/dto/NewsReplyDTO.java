package org.zerock.b01.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsReplyDTO {

    private Long rno;

    @NotNull
    private Long bno;

    @NotEmpty
    private String newsReplyText;

    @NotEmpty
    private String newsReplyer;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDate;

    @JsonIgnore
    private LocalDateTime modDate;

}
