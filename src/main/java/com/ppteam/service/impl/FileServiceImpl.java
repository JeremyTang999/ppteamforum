package com.ppteam.service.impl;

import com.ppteam.dao.UserInfoDao;
import com.ppteam.dao.exceptions.DaoQueryFailException;
import com.ppteam.dao.exceptions.DaoUpdateFailException;
import com.ppteam.dao.exceptions.MoreThanOneResultException;
import com.ppteam.entity.UserInfo;
import com.ppteam.service.FileService;
import com.ppteam.service.UserService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.security.x509.AVA;

import java.io.*;

@Service
public class FileServiceImpl implements FileService {

    private static final String AVATAR_FILE_NAME="C:/ppteamforum/avatar/";

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public String avatarUpload(MultipartFile multipartFile) {

        int id=userService.getUserIdFromSecurity();
        String name=id+"";
        System.out.println(id);
        File file=new File(AVATAR_FILE_NAME+name);
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
    public byte[] avatarDounload(String avatarName) {

        try {
            File file=new File(AVATAR_FILE_NAME+avatarName);
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
}
