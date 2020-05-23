package com.restaurant.restaurant.rest;

import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/resto/V1/user/")
@CrossOrigin
public class FileRestControllerV1 {
    @SneakyThrows
    @RequestMapping("/getImage/{imagePath}")
    @ResponseBody
    public byte[] getImage(@PathVariable String imagePath, HttpServletRequest request)  {
        File file;
        try {
            file = new File(getClass().getResource("/static/images/"+imagePath).getFile());
        }catch (NullPointerException e){
            file = new File("src/main/resources/static/images/" + imagePath);
        }

        return Files.readAllBytes(file.toPath());
    }

    @SneakyThrows
    @RequestMapping("/getFile/{filePath}")
    @ResponseBody
    public byte[] getFile(@PathVariable String filePath, HttpServletRequest request)  {
        File file;
        try {
            file = new File(getClass().getResource("/static/receipts/"+filePath).getFile());
        }catch (NullPointerException e){

            file = new File("src/main/resources/static/receipts/"+filePath);
        }

        return Files.readAllBytes(file.toPath());
    }

    @SneakyThrows
    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity saveFile(@RequestParam MultipartFile file, HttpServletRequest request)  {
        try{
            Files.copy(file.getInputStream(),
                    Paths.get("src/main/resources/static/images/").resolve(file.getOriginalFilename()));
            return ResponseEntity.ok(file.getOriginalFilename());
        }catch (FileAlreadyExistsException e){
            return ResponseEntity.badRequest().body("Already exists");
        }
    }
}
