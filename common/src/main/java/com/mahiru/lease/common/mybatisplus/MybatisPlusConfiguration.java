package com.mahiru.lease.common.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @className MybatisPlusConfiguration
 * @description mybatis-plus配置类
 * @author mahiru
 * @date 2024/10/26 17:10
 * @version v1.0.0
**/
@Configuration
@MapperScan("com.mahiru.lease.web.*.mapper")
public class MybatisPlusConfiguration {

}
