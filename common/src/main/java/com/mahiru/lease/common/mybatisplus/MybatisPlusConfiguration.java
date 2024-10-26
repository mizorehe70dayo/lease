package com.mahiru.lease.common.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author mahiru
 * @version v1.0.0
 * @className MybatisPlusConfiguration
 * @description mybatis-plus配置类
 * @date 2024/10/26 17:10
 **/
@Configuration
@MapperScan("com.mahiru.lease.web.*.mapper")
public class MybatisPlusConfiguration {

}
