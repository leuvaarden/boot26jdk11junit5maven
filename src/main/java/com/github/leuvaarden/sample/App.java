package com.github.leuvaarden.sample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler(new GlobalUncaughtExceptionHandler());
        SpringApplication.run(App.class, args);
    }

    public static class GlobalUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread thread, Throwable throwable) {
            logger.error("Uncaught exception [threadId={}]:", thread.getId(), throwable);
        }
    }
}
