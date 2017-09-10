package com.ppteam.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    public String avatarUpload(MultipartFile file);
    public byte[] avatarDownload(String avatarName);
    public String articleImageUpload(MultipartFile file);
    public byte[] articleImageDownload(String imageName);
}
