package com.hncis.common.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;

public class S3Util {

	 private String accessKey = "AKIAI7UBT3I5J3BGDQDQ";
	    private String secretKey = "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";
	     
	    private AmazonS3 conn;
	     
	    public S3Util() {
	        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
	        ClientConfiguration clientConfig = new ClientConfiguration();
	        clientConfig.setProtocol(Protocol.HTTP);
	        this.conn = new AmazonS3Client(credentials, clientConfig);
	        conn.setEndpoint("s3-ap-northeast-1.amazonaws.com");
	    }
	     
	    // Bucket List
	    public List<Bucket> getBucketList() {
	        return conn.listBuckets();
	    }
	     
	    // Bucket 생성 
	    public Bucket createBucket(String bucketName) {
	        return conn.createBucket(bucketName);
	    }
	     
	    // 폴더 생성 (폴더는 파일명 뒤에 "/"를 붙여야한다.)
	    public void createFolder(String bucketName, String folderName) {
	        conn.putObject(bucketName, folderName + "/", new ByteArrayInputStream(new byte[0]), new ObjectMetadata());
	    }
	     
	    // 파일 업로드
	    public void fileUpload(String bucketName, MultipartFile uploadFile) throws AmazonServiceException, AmazonClientException, IOException {
	        conn.putObject(bucketName, uploadFile.getName(), uploadFile.getInputStream(), new ObjectMetadata());
	    }
	     
	    // 파일 삭제
	    public void fileDelete(String bucketName, String fileName) {
	        conn.deleteObject(bucketName, fileName);
	    }
	     
	    // 파일 URL
	    public String getFileURL(String bucketName, String fileName) {
	        return conn.generatePresignedUrl(new GeneratePresignedUrlRequest(bucketName, fileName)).toString();
	    }
}
