package com.codeboy.mvc.model.service;


import com.codeboy.mvc.model.dto.AIProblem;
import com.codeboy.mvc.model.dto.request.AIProblemRequest;

import java.util.List;

public interface AIProblemService {
    List<AIProblem> generateProblems(AIProblemRequest request);
}
