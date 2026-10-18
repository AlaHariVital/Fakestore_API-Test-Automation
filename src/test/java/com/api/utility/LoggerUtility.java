package com.api.utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** Thin factory over Log4j2. */
public final class LoggerUtility {

    private LoggerUtility() {
    }

    public static Logger getLogger(Class<?> clazz) {
        return LogManager.getLogger(clazz);
    }
}
