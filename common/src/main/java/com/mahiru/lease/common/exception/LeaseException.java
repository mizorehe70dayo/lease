package com.mahiru.lease.common.exception;

import com.mahiru.lease.common.result.ResultCodeEnum;
import lombok.Data;

/**
 * @author mahiru
 * @version v1.0.0
 * @className LeaseException
 * @description 判断该公寓下是否存在房间信息
 * @date 2024/10/27 18:13
 **/
@Data
public class LeaseException extends RuntimeException {

    //异常状态码
    private Integer code;

    /**
     * 通过状态码和错误消息创建异常对象
     *
     * @param message
     * @param code
     */
    public LeaseException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    /**
     * 根据响应结果枚举对象创建异常对象
     *
     * @param resultCodeEnum
     */
    public LeaseException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "LeaseException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }
}
