package com.pranamya.placementcoach.controller;

import com.pranamya.placementcoach.service.GeminiService;
import com.pranamya.placementcoach.service.ResumeTextExtractor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/ai")
public class AIQuestionController {

    private final GeminiService geminiService;
    private final ResumeTextExtractor resumeTextExtractor;

    public AIQuestionController(
            GeminiService geminiService,
            ResumeTextExtractor resumeTextExtractor
    ) {
        this.geminiService = geminiService;
        this.resumeTextExtractor = resumeTextExtractor;
    }

    @PostMapping(
            value = "/analyze-resume",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public String analyzeResume(
            @RequestParam("file") MultipartFile file
    ) {

        try {

            String resumeText =
                    resumeTextExtractor.extractText(file);

            return geminiService.analyzeResume(resumeText);

        } catch (Exception e) {

            return "Error analyzing resume: " + e.getMessage();
        }
    }

    @PostMapping("/questions")
    public String generateQuestions(
            @RequestParam String skills
    ) {
        return geminiService.generateQuestions(skills);
    }
}