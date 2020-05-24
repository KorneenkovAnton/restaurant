package com.restaurant.restaurant.util;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class FileConverter {

    public File convert(MultipartFile file){
        File convFile = new File(file.getOriginalFilename());
        try(FileOutputStream fos = new FileOutputStream(convFile)) {
            convFile.createNewFile();
            fos.write(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
       return convFile;
    }
}
