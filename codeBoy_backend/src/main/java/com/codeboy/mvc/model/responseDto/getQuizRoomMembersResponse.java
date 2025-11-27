package com.codeboy.mvc.model.responseDto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class getQuizRoomMembersResponse {
    private Long memberId;
    private String nickname;
}
