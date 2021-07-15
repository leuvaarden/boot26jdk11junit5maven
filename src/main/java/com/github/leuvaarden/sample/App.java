package com.github.leuvaarden.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

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
