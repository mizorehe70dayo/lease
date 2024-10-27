package com.mahiru.lease.web.admin.controller.apartment;

import com.mahiru.lease.common.result.Result;
import com.mahiru.lease.web.admin.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "文件管理")
@RequestMapping("/admin/file")
@RestController
@AllArgsConstructor
public class FileUploadController {
    private final FileService fileService;

    @Operation(summary = "上传文件")
    @PostMapping("upload")
    public Result<String> upload(@RequestParam MultipartFile file) throws FileUploadException,Exception{
        String url = fileService.upload(file);
        return Result.ok(url);
    }
}
