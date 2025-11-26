package com.codeboy.mvc.model.requestDto;

import com.codeboy.common.Category;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class GetProblemsRequest {
    private int limit;
    private Category category;
}
