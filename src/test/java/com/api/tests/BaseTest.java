package com.api.tests;

import com.api.listeners.TestListener;
import com.api.utility.ExtentReporterUtility;
import com.api.utility.LoggerUtility;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Listeners;

/** Attaches the Extent/log listener to every test class and offers a step logger. */
@Listeners(TestListener.class)
public class BaseTest {

    protected final Logger logger = LoggerUtility.getLogger(this.getClass());

    /** Log a step to both Log4j2 and the Extent report. */
    protected void step(String message) {
        logger.info(message);
        if (ExtentReporterUtility.getTest() != null) {
            ExtentReporterUtility.getTest().info(message);
        }
    }
}
