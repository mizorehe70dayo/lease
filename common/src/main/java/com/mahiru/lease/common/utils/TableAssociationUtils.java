package com.mahiru.lease.common.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @className AssociationUtils
 * @description 表关系处理工具类
 * @author mahiru
 * @date 2024/10/27 16:06
 * @version v1.0.0
**/
public class TableAssociationUtils {

    public static <T> void removeTableAssociation(SFunction<T, ?> column, Long id, IService<T> service) {
        service.remove(
                new LambdaQueryWrapper<T>()
                        .eq(column, id)
        );
    }
}
