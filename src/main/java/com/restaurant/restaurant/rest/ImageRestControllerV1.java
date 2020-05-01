package com.restaurant.restaurant.rest;

import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Files;

@RestController
@RequestMapping("/resto/V1/user/")
public class ImageRestControllerV1 {
    @SneakyThrows
    @RequestMapping("/getImage/{imagePath}")
    @ResponseBody
    public byte[] getImage(@PathVariable String imagePath, HttpServletRequest request)  {
        File file = new File(getClass().getResource("/static/images/"+imagePath).getFile());
        byte[] data = Files.readAllBytes(file.toPath());
        System.out.println(file.getAbsolutePath());
        return data;
    }
}
