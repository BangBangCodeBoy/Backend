package com.codeboy.mvc.model.service;

import com.codeboy.ai.dto.AIProblemDto;
import com.codeboy.ai.dto.AIProblemRequest;

import java.util.List;

public interface AIProblemService {
    List<AIProblemDto> generateProblems(AIProblemRequest request);
}
