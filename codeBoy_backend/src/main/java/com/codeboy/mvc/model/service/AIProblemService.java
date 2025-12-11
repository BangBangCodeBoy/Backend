package com.codeboy.mvc.model.service;


import com.codeboy.mvc.model.dto.AIProblemDto;
import com.codeboy.mvc.model.dto.request.AIProblemRequest;

import java.util.List;

public interface AIProblemService {
    List<AIProblemDto> generateProblems(AIProblemRequest request);
}
