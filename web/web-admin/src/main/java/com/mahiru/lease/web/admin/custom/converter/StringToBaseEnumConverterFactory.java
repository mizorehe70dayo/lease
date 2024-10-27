package com.mahiru.lease.web.admin.custom.converter;

import com.mahiru.lease.model.enums.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @className StringToBaseEnumConverterFactory
 * @description 字符串到枚举类型工厂类
 * @author mahiru
 * @date 2024/10/26 21:57
 * @version v1.0.0
**/
@Component
public class StringToBaseEnumConverterFactory implements ConverterFactory<String, BaseEnum> {

    private static final String ILLEGAL_ENUM_VALUE = "非法的枚举值: ";
    private static final String ENUM_VALUE_CANNOT_BE_NULL = "枚举值不能为空";

    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
        return source -> {
            if (source.isEmpty()) {
                throw new IllegalArgumentException(ENUM_VALUE_CANNOT_BE_NULL);
            }

            return Arrays.stream(targetType.getEnumConstants())
                    .filter(enumConstant -> enumConstant.getCode().equals(Integer.valueOf(source)))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(ILLEGAL_ENUM_VALUE + source));
        };
    }
}
