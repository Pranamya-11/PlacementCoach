package com.pranamya.placementcoach.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ResumeTextExtractor {

    public String extractText(MultipartFile file) throws IOException {

        String fileName = file.getOriginalFilename();

        if (fileName == null) {
            throw new IllegalArgumentException("File name is missing");
        }

        if (fileName.toLowerCase().endsWith(".pdf")) {

            try (PDDocument document =
                         Loader.loadPDF(file.getBytes())) {

                PDFTextStripper stripper =
                        new PDFTextStripper();

                return stripper.getText(document);
            }
        }

        if (fileName.toLowerCase().endsWith(".docx")) {

            try (XWPFDocument document =
                         new XWPFDocument(file.getInputStream());

                 XWPFWordExtractor extractor =
                         new XWPFWordExtractor(document)) {

                return extractor.getText();
            }
        }

        throw new IllegalArgumentException(
                "Only PDF and DOCX files are supported"
        );
    }
}