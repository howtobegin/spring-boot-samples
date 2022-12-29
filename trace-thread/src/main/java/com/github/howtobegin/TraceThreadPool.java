package com.github.howtobegin;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lhl
 * @date 2022/11/21 下午5:02
 */
@Slf4j
public class TraceThreadPool {
    public static void main(String[] args) {
        new TraceThreadPool().log();
    }

    public void log() {
        log.info("1");
    }
}
