package com.mahiru.lease.web.admin.custom.config;

import com.mahiru.lease.web.admin.custom.converter.StringToBaseEnumConverterFactory;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @className WebMvcConfiguration
 * @description webConfig配置文件
 * @author mahiru
 * @date 2024/10/26 22:01
 * @version v1.0.0
**/
@Configuration
@AllArgsConstructor
public class WebMvcConfiguration implements WebMvcConfigurer {
    private final StringToBaseEnumConverterFactory stringToBaseEnumConverterFactory;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(this.stringToBaseEnumConverterFactory);
    }
}
