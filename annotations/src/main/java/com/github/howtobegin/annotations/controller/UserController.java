package com.github.howtobegin.annotations.controller;

import com.github.howtobegin.annotations.common.CommonResp;
import com.github.howtobegin.annotations.annotation.idempotent.Idempotent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lhl
 * @date 2022/10/27 下午6:38
 */
@RestController
@RequestMapping("user")
public class UserController {


    @Idempotent(key = "register", subKey = "#req.mobile", time = 3000)
    @RequestMapping("register")
    public CommonResp<String> register(@RequestBody RegisterReq req) {
        return CommonResp.success("success");
    }


    @Getter
    @Setter
    static class RegisterReq {
        private String mobile;
    }
}
