package com.finpro.grocery.cloudinary;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {
    String uploadFile(MultipartFile file) throws IOException;
    String deleteFile(String publicId) throws IOException;
    String generateUrl(String publicId);
}
