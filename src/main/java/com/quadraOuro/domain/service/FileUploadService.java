package com.quadraOuro.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileUploadService {

    public List<String> uploadFiles(List<MultipartFile> files) {
        // Simulate file upload and return URLs
        return files.stream()
                .map(file -> "https://storage.example.com/" + file.getOriginalFilename())
                .collect(Collectors.toList());
    }
}