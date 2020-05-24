package com.restaurant.restaurant.util.aws;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

@Component
public class AWSUtil {
    @Value("${aws.secret.key}")
    private String AWS_SECRET;

    @Value("${aws.access.key}")
    private String AWS_ACCESS_KEY;

    private AmazonS3 s3;

    @PostConstruct
    public void init() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET);
        this.s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(Regions.EU_CENTRAL_1).build();
    }

    public boolean uploadToAws(File file, String bucket) {
        boolean answer;
        try {
            this.s3.putObject(bucket, file.getName(), file);
            answer = true;
        } catch (AmazonServiceException e) {
            e.printStackTrace();
            answer = false;
        }
        return answer;
    }

    public byte[] downloadFromAws(String fileName, String bucket) throws IOException {
        File temp = new File(fileName);
        temp.createNewFile();
        S3Object o = this.s3.getObject(bucket, fileName);
        S3ObjectInputStream s3ObjectInputStream = o.getObjectContent();
        FileOutputStream fos = new FileOutputStream(temp);
        byte[] read_buf = new byte[1024];
        int read_len = 0;
        while ((read_len = s3ObjectInputStream.read(read_buf)) > 0) {
            fos.write(read_buf, 0, read_len);
        }
        s3ObjectInputStream.close();
        fos.close();
        byte[] answer = Files.readAllBytes(temp.toPath());
        temp.delete();
        return answer;
    }
}
