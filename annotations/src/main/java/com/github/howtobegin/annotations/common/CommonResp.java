package com.github.howtobegin.annotations.common;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

/**
 * @author JimChery
 */
@Slf4j
@Data
public class CommonResp<T> {
    private static final String SUCCESS_RESP_CODE = "000000";
    private static final String SUCCESS_MSG = "success";
    public static final String TRACE_ID = "X-B3-TraceId";
    public static final String SPAN_ID = "X-B3-SpanId";

    private String code;

    private String msg;

    private T data;

    private boolean success;

    private String traceId;

    private Long systemTime;

    public CommonResp() {
        systemTime = System.currentTimeMillis();
    }

    private CommonResp(String code, String msg) {
        this.code = code;
        this.msg = msg;
        this.traceId = MDC.get(TRACE_ID);
        systemTime = System.currentTimeMillis();
    }

    private CommonResp(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.traceId = MDC.get(TRACE_ID);
        systemTime = System.currentTimeMillis();
    }

    public static <C> CommonResp<C> success(C data) {
        CommonResp<C> resp = new CommonResp<>(SUCCESS_RESP_CODE, SUCCESS_MSG);
        resp.setData(data);
        return resp;
    }

    public static CommonResp success() {
        return new CommonResp(SUCCESS_RESP_CODE, SUCCESS_MSG);
    }

    public static CommonResp error(String code, String msg) {
        return new CommonResp(code, msg);
    }

    public boolean isSuccess() {
        log.info("respCode={} traceId={}", this.code, traceId);
        return SUCCESS_RESP_CODE.equals(this.code);
    }
}
