package com.mahiru.lease;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @className AdminWebApplication
 * @description web-admin启动类
 * @author mahiru
 * @date 2024/10/26 16:57
 * @version v1.0.0
**/
@SpringBootApplication
@MapperScan("com.mahiru.lease.web.*.mapper")
public class AdminWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminWebApplication.class, args);
    }
}
