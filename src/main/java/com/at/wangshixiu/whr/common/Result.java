package com.at.wangshixiu.whr.common;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 王世秀
 * @Date 2022/12/19
 * @Description
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "Result",description = "统一的数据返回类")
public class Result<T> implements Serializable {
    @ApiModelProperty("http状态码")
    private Integer code;
    @ApiModelProperty("消息")
    private String message;
    @ApiModelProperty("数据")
    private T data;
    @ApiModelProperty("是否成功")
    private Boolean success;

    public static Result<Object> ok(Object data) {
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(200);
        result.setData(data);
        result.setMessage("操作成功");
        return result;
    }
    public static Result<Object> ok() {
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(200);
//        result.setData(data);
        result.setMessage("操作成功");
        return result;
    }

    public static Result<Object> error(String message) {
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(200);
        result.setData(null);
        result.setMessage(message);
        return result;
    }

    public static Result<Object> error(Integer httpStatus, String message) {
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(httpStatus);
        result.setData(null);
        result.setMessage(message);
        return result;
    }
}
