package com.restaurant.restaurant.rest;

import com.restaurant.restaurant.util.FileConverter;
import com.restaurant.restaurant.util.aws.AWSUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
@RequestMapping("/resto/V1/user/")
@CrossOrigin
public class FileRestControllerV1 {

    @Autowired
    private AWSUtil awsUtil;
    @Autowired
    private FileConverter fileConverter;

    @Value("${aws.s3.bucket.images}")
    private String AWS_BUCKET_IMAGES;
    @Value("${aws.s3.bucket.receipts}")
    private String AWS_BUCKET_RECEIPTS;

    @SneakyThrows
    @RequestMapping("/getImage/{imagePath}")
    @ResponseBody
    public byte[] getImage(@PathVariable String imagePath, HttpServletRequest request) {
        return awsUtil.downloadFromAws(imagePath, AWS_BUCKET_IMAGES);
    }

    @SneakyThrows
    @RequestMapping("/getFile/{filePath}")
    @ResponseBody
    public byte[] getFile(@PathVariable String filePath, HttpServletRequest request) {
//        File file;
//        try {
//            file = new File(getClass().getResource("/static/receipts/" + filePath).getFile());
//        } catch (NullPointerException e) {
//
//            file = new File("src/main/resources/static/receipts/" + filePath);
//        }

        return awsUtil.downloadFromAws(filePath,AWS_BUCKET_RECEIPTS);
    }

    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity saveFile(@RequestParam MultipartFile file, HttpServletRequest request) {
        return awsUtil.uploadToAws(fileConverter.convert(file), AWS_BUCKET_IMAGES) ?
                ResponseEntity.ok(Objects.requireNonNull(file.getOriginalFilename())) : ResponseEntity.badRequest().body("Error");
    }
}
