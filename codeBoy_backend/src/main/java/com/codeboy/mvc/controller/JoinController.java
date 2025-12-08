package com.codeboy.mvc.controller;

import com.codeboy.mvc.model.dto.request.JoinRequest;
import com.codeboy.mvc.model.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JoinController {
    private final JoinService joinService;

    @PostMapping("/join")
    public String adminP(JoinRequest joinRequest) {
        joinService.joinProcess(joinRequest);
        return "ok";
    }

}
