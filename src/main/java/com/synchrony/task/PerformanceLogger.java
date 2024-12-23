package com.synchrony.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PerformanceLogger {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceLogger.class);

    public void logQueryPerformance(long startTime) {
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        logger.info("Query Execution Time: {} ms", duration);
    }
}
