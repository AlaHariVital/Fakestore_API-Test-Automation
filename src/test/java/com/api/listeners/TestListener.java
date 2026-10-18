package com.api.listeners;

import com.api.utility.ExtentReporterUtility;
import com.api.utility.LoggerUtility;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/** Bridges TestNG events to both Log4j2 and the Extent HTML report. */
public class TestListener implements ITestListener {

    private static final Logger logger = LoggerUtility.getLogger(TestListener.class);

    @Override
    public void onStart(ITestContext context) {
        logger.info("Suite started: {}", context.getName());
        ExtentReporterUtility.setupSparkReporter("report.html");
    }

    @Override
    public void onTestStart(ITestResult result) {
        String name = result.getMethod().getMethodName() + paramSuffix(result);
        logger.info("START {}", name);
        ExtentReporterUtility.createExtentTest(name);
        String description = result.getMethod().getDescription();
        if (description != null) {
            ExtentReporterUtility.getTest().info(description);
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("PASS  {}", result.getMethod().getMethodName());
        ExtentReporterUtility.getTest().log(Status.PASS, "Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("FAIL  {} - {}", result.getMethod().getMethodName(),
                result.getThrowable() == null ? "" : result.getThrowable().getMessage());
        ExtentReporterUtility.getTest().log(Status.FAIL, result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("SKIP  {}", result.getMethod().getMethodName());
        if (ExtentReporterUtility.getTest() != null) {
            ExtentReporterUtility.getTest().log(Status.SKIP, "Skipped");
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("Suite finished: {}", context.getName());
        ExtentReporterUtility.flushReport();
    }

    private String paramSuffix(ITestResult result) {
        Object[] params = result.getParameters();
        if (params == null || params.length == 0) {
            return "";
        }
        return " [" + params[0] + "]";
    }
}
