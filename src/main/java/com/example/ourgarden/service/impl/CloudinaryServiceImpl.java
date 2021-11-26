package com.example.ourgarden.service.impl;

import com.cloudinary.Cloudinary;
import com.example.ourgarden.model.service.CloudinaryImage;
import com.example.ourgarden.service.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {
    private final Cloudinary cloudinary;
    private static final String TEMP_FILE = "temp-file";
    private static final String URL = "url";
    private static final String PUBLIC_ID = "public_id";

    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public CloudinaryImage upload(MultipartFile file) throws IOException {
        File tempFile = File.createTempFile(TEMP_FILE, file.getOriginalFilename());
        file.transferTo(tempFile);
        CloudinaryImage image;
        try {
            @SuppressWarnings("unchecked")
            Map<String, String> uploadResult = cloudinary
                    .uploader()
                    .upload(tempFile, Map.of());

            String url = uploadResult.getOrDefault(URL, "https://codes4education.com/wp-content/uploads/2020/10/404-Error-Page-UI-Design-new-min.png");
            String publicId = uploadResult.getOrDefault(PUBLIC_ID, "");
            image = new CloudinaryImage();
            image.setPublicId(publicId);
            image.setUrl(url);
        }finally {
            tempFile.delete();
        }
        return image;
    }

    @Override
    public boolean delete(String publicId) {
        try {
            this.cloudinary.uploader().destroy(publicId, Map.of());
        }catch (IOException e){
            return false;
        }
        return true;
    }
}
