package com.github.howtobegin.annotations.service;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author hello
 * @date 2022/12/29 17:54
 */
@Getter
@Setter
public class ValidBo {
    @NotBlank(message = "订单号不能为空")
    private String orderId;
    @NotBlank(message = "用户号不能为空")
    private String userId;
}
