package com.scm2.SmartContactManager.Services.Impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scm2.SmartContactManager.Helper.AppConstants;
import com.scm2.SmartContactManager.Services.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

    // @Autowired
    private Cloudinary cloudinary; // no need to autowire it

    public ImageServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uplaodImage(MultipartFile contactImage, String fileName) {

     

        // fileName=fileName+".jpg";

        try {
            byte[] data = new byte[contactImage.getInputStream().available()];
            contactImage.getInputStream().read(data);

            // now upload public_id is the v
            //
            // alue provided by cloudanary

            cloudinary.uploader().upload(data, ObjectUtils.asMap("public_id", fileName));

        } catch (IOException e) {

            e.printStackTrace();
        }

        return this.getUrlFromPublicId(fileName);
    }


// to get U

    @Override
    public String getUrlFromPublicId(String publicId) {

        return cloudinary.url().transformation(new Transformation<>()
                .crop(AppConstants.CONTACT_IMAGE_CROP)
                .width(AppConstants.COTACT_IMG_WIDTH)
                .height(AppConstants.COTACT_IMG_HEIGHT))
                .generate(publicId);

    }

}