package com.codeboy.mvc.model.requestDto;

import com.codeboy.common.ProblemType;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class IncorrectNoteRequest {
    private Long problemId;
    private Long userProblemId;
    private ProblemType problemType;
}
