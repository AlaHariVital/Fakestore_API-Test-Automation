package com.api.utility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

/**
 * Owns the single {@link ExtentReports} instance and a per-thread {@link ExtentTest}
 * so parallel API tests log into their own node of one HTML report.
 */
public final class ExtentReporterUtility {

    private static ExtentReports extentReports;
    private static final ThreadLocal<ExtentTest> EXTENT_TEST = new ThreadLocal<>();

    private ExtentReporterUtility() {
    }

    public static void setupSparkReporter(String reportName) {
        ExtentSparkReporter spark =
                new ExtentSparkReporter(System.getProperty("user.dir") + "/" + reportName);
        spark.config().setTheme(Theme.DARK);
        spark.config().setDocumentTitle("FakeStoreAPI Automation Report");
        spark.config().setReportName("FakeStoreAPI - REST Assured + TestNG");

        extentReports = new ExtentReports();
        extentReports.attachReporter(spark);
        extentReports.setSystemInfo("API", "https://fakestoreapi.com");
        extentReports.setSystemInfo("Java", System.getProperty("java.version"));
    }

    public static void createExtentTest(String testName) {
        EXTENT_TEST.set(extentReports.createTest(testName));
    }

    public static ExtentTest getTest() {
        return EXTENT_TEST.get();
    }

    public static void flushReport() {
        if (extentReports != null) {
            extentReports.flush();
        }
    }
}
