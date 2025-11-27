package com.codeboy.mvc.model.requestDto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class IncorrectNoteRequest {
    private Long problemId;
    private Long userProblemId;
    private Boolean isUserProblem;
}
