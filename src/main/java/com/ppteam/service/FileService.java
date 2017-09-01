package com.ppteam.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    public String avatarUpload(MultipartFile file);
    public byte[] avatarDounload(String avatarName);
}
