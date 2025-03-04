package com.scm2.SmartContactManager.Services;


import org.springframework.web.multipart.MultipartFile;


public interface ImageService {


    // this will return the path of profile fic at cloud
    String uplaodImage(MultipartFile contactImage,String filename);


    // to get url
    String getUrlFromPublicId(String publicId);
}
