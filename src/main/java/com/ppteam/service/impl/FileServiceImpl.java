package com.ppteam.service.impl;

import com.ppteam.dao.UserInfoDao;
import com.ppteam.dao.exceptions.DaoQueryFailException;
import com.ppteam.dao.exceptions.DaoUpdateFailException;
import com.ppteam.dao.exceptions.MoreThanOneResultException;
import com.ppteam.entity.UserInfo;
import com.ppteam.service.FileService;
import com.ppteam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class FileServiceImpl implements FileService {

    private static final String AVATAR_FILE_PATH_NAME ="C:/ppteamforum/avatar/";

    private static final String ARTICLE_IMAGE_FILE_PATH_NAME="C:/ppteamforum/article_image/";

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public String avatarUpload(MultipartFile multipartFile) {

        int id=userService.getUserIdFromSecurity();
        String name=id+"";
        //System.out.println(id);
        File file=new File(AVATAR_FILE_PATH_NAME +name);
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        try {
            UserInfo userInfo=userInfoDao.get(id);
            userInfo.setAvatarPath(name);
            userInfoDao.update(userInfo);
        } catch (DaoQueryFailException | MoreThanOneResultException | DaoUpdateFailException e) {
            e.printStackTrace();
            return null;
        }
        return name;

    }

    @Override
    public byte[] avatarDownload(String avatarName) {

        try {
            File file=new File(AVATAR_FILE_PATH_NAME +avatarName);
            //FileInputStream fileInputStream=new FileInputStream(file);
            BufferedInputStream inputStream=new BufferedInputStream(new FileInputStream(file));
            byte[] bytes=new byte[(int)file.length()];
            inputStream.read(bytes,0,(int)( file.length()-1));
            inputStream.close();
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String articleImageUpload(MultipartFile multipartFile) {
        String name=System.currentTimeMillis()+"";
        File file=new File(ARTICLE_IMAGE_FILE_PATH_NAME +name);
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return name;
    }

    @Override
    public byte[] articleImageDownload(String imageName) {
        try {
            File file=new File(ARTICLE_IMAGE_FILE_PATH_NAME +imageName);
            BufferedInputStream inputStream=new BufferedInputStream(new FileInputStream(file));
            byte[] bytes=new byte[(int)file.length()];
            inputStream.read(bytes,0,(int)( file.length()-1));
            inputStream.close();
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
