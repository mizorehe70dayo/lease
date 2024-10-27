package com.mahiru.lease.common.mybatisplus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
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

    /**
     * @author mahiru
     * @date 2024/10/27 16:51
     * @methodName mybatisPlusInterceptor
     * @description 分页插件
     * @param
     * @return com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
