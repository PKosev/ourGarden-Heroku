package com.example.ourgarden.service;

import com.example.ourgarden.model.entity.PictureEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PictureService {
    PictureEntity createPicture(MultipartFile picture, String title) throws IOException;

    void deletePicture(String publicId);
}
