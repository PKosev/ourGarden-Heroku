package com.example.ourgarden.service.impl;

import com.example.ourgarden.model.entity.PictureEntity;
import com.example.ourgarden.model.service.CloudinaryImage;
import com.example.ourgarden.repository.PictureRepository;
import com.example.ourgarden.service.CloudinaryService;
import com.example.ourgarden.service.PictureService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;
    private final CloudinaryService cloudinaryService;

    public PictureServiceImpl(PictureRepository pictureRepository, CloudinaryService cloudinaryService) {
        this.pictureRepository = pictureRepository;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public PictureEntity createPicture(MultipartFile picture, String title) throws IOException {
        CloudinaryImage uploaded = cloudinaryService.upload(picture);

        PictureEntity pictureEntity = new PictureEntity();
        pictureEntity.setPublicId(uploaded.getPublicId());
        pictureEntity.setTitle(title);
        pictureEntity.setUrl(uploaded.getUrl());

        pictureRepository.save(pictureEntity);
        return pictureEntity;
    }

    @Override
    public void deletePicture(String publicId) {
        cloudinaryService.delete(publicId);
        pictureRepository.deleteAllByPublicId(publicId);
    }
}
