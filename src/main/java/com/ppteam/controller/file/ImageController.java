package com.ppteam.controller.file;

import com.ppteam.service.FileService;
import com.ppteam.service.impl.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/avatar",method = RequestMethod.POST)
    public ResponseEntity avatarUpload(@RequestParam("pic")MultipartFile file, HttpServletResponse response) throws URISyntaxException{
        String name=fileService.avatarUpload(file);
        Map<String,String> json=new HashMap<String,String>();
        //HttpHeaders headers=new HttpHeaders();
        //headers.setLocation(new URI("/space.html"));
        json.put("avatarName",name);
        /*try {
            response.sendRedirect("/space.html");
        }catch (IOException e){
            e.printStackTrace();
        }*/
        return name==null ?
                new ResponseEntity(HttpStatus.NOT_FOUND):
                //new ResponseEntity(headers,HttpStatus.TEMPORARY_REDIRECT);
                new ResponseEntity(json,HttpStatus.OK);
    }

    @RequestMapping(value = "/avatar/{avatarName}",method = RequestMethod.GET)
    public ResponseEntity avatarDownload(@PathVariable("avatarName")String name){
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        byte[] file=fileService.avatarDounload(name);
        return file==null ?
                new ResponseEntity(HttpStatus.NOT_FOUND):
                new ResponseEntity(file,headers,HttpStatus.OK);
    }
}
