package com.pranamya.placementcoach.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {

    private final Client geminiClient;

    public GeminiService(Client geminiClient) {
        this.geminiClient = geminiClient;
    }

    public String generateQuestions(String skills) {

        String prompt = """
                You are a technical interviewer for a college placement.

                Generate exactly 5 technical interview questions based on
                these skills:

                %s

                Return ONLY valid JSON.
                Do not use markdown.
                Do not use ```json.
                Do not add explanations before or after the JSON.

                The JSON must have exactly this format:

                [
                  {
                    "skill": "Java",
                    "question": "question here",
                    "difficulty": "Easy"
                  }
                ]

                Requirements:
                - Generate questions specifically related to the given skills.
                - Do not ask HR or behavioral questions.
                - Mix Easy and Medium difficulty.
                - Do not provide answers.
                - The skill field must contain the relevant skill.
                """.formatted(skills);

        GenerateContentResponse response =
                geminiClient.models.generateContent(
                        "gemini-2.5-flash",
                        prompt,
                        null
                );

        return response.text();
    }


    // NEW: Analyze uploaded resume
    public String analyzeResume(String resumeText) {

        String prompt = """
                You are an AI resume analyzer for a college placement system.

                Analyze the following resume:

                ---------------- RESUME ----------------
                %s
                -----------------------------------------

                Identify the technical skills clearly mentioned
                in the resume.

                Return ONLY valid JSON.
                Do not use markdown.
                Do not use ```json.
                Do not add explanations.

                Use exactly this format:

                {
                  "skills": [
                    "Java",
                    "Spring Boot",
                    "SQL"
                  ]
                }

                Rules:
                - Include only technical skills.
                - Do not invent skills that are not present.
                - Keep each skill as a separate item.
                - Return an empty array if no technical skills are found.
                """.formatted(resumeText);

        GenerateContentResponse response =
                geminiClient.models.generateContent(
                        "gemini-2.5-flash",
                        prompt,
                        null
                );

        return response.text();
    }
}