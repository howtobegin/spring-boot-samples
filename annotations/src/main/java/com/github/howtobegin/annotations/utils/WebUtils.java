package com.github.howtobegin.annotations.utils;

import com.alibaba.fastjson.JSON;
import com.github.howtobegin.annotations.common.CommonResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author lhl
 * @date 2022/10/27 下午5:55
 */
public class WebUtils {

    private static final Logger log = LoggerFactory.getLogger(WebUtils.class);

    /**
     * 直接向前端抛出异常
     * @param msg 提示信息
     */
    public static void responseFail(String msg)  {
        HttpServletResponse response=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        CommonResp<Object> resultData = CommonResp.error("999999", msg);
        WebUtils.writeJson(response,resultData);
    }

    /**
     * 返回json数据
     *
     * @param response
     * @param object
     */
    public static void writeJson(HttpServletResponse response, int status, Object object) {
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(status);
        PrintWriter out = null;
        try {
            String data = object instanceof String ? (String) object : JSON.toJSONString(object);
            out = response.getWriter();
            out.print(data);
            out.flush();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 返回json数据
     *
     * @param response
     * @param object
     */
    public static void writeJson(HttpServletResponse response, Object object) {
        writeJson(response, HttpServletResponse.SC_OK, object);
    }

    /**
     * 返回json数据
     *
     * @param response
     * @param object
     */
    public static void writeJson(ServletResponse response, Object object) {
        if (response instanceof HttpServletResponse) {
            writeJson((HttpServletResponse) response, object);
        }
    }
}
