package com.example.ourgarden.service;

import com.example.ourgarden.model.service.CloudinaryImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {

    CloudinaryImage upload(MultipartFile file) throws IOException;

    void delete(String publicId);
}
