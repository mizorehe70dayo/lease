package com.mahiru.lease.common.exception;

import com.mahiru.lease.common.result.Result;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * @author mahiru
 * @date 2024/10/27 12:16
 * @version v1.0.0
 * @description 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FileUploadException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result handleFileUploadException(FileUploadException e) {
        // 记录日志
        System.err.println("文件上传失败： " + e.getMessage());
        return Result.fail("文件上传失败: " + e.getMessage());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseBody
    public Result handleMaxSizeException(MaxUploadSizeExceededException e) {
        return Result.fail("File size exceeds the maximum limit of " + e.getMaxUploadSize() + " bytes.");
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handleGenericException(Exception e) {
        // 记录日志
        e.printStackTrace();
        return Result.fail("An unexpected error occurred");
    }

    @ExceptionHandler(LeaseException.class)
    @ResponseBody
    public Result handleLeaseException(LeaseException e){
        e.printStackTrace();
        return Result.fail(e.getCode(), e.getMessage());
    }
}
