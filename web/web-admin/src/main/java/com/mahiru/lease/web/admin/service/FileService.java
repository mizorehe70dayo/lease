package com.mahiru.lease.web.admin.service;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String upload(MultipartFile file) throws FileUploadException, Exception;
}
