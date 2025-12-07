package com.codeboy.mvc.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codeboy.mvc.model.service.QuizGenerationServiceImpl;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuizGenerationServiceImpl service;
    private final PdfTextExtractor extractor;

    public QuizController(QuizGenerationServiceImpl service, PdfTextExtractor extractor) {
        this.service = service;
        this.extractor = extractor;
    }

    @PostMapping(value = "/pdf", consumes = "multipart/form-data")
    public String generateFromPdf(@RequestPart("file") MultipartFile pdf,
                                  @RequestPart("count") Integer count) {
        String text = extractor.extractText(pdf);
        return service.generate(text, count);
    }
}
