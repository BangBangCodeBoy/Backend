package com.codeboy.mvc.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description="회원 퀴즈방 중간 DTO")
public class QuizRoomMember {
    private long quizRoomMemberId;
    private long memberId;
    private long roomId;
    private Boolean isHost;
}
