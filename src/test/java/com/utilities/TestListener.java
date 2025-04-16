package com.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    private static final ExtentReports extent = new ExtentReports();
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static boolean isReporterAttached = false;

    @Override
    public void onStart(ITestContext context) {
        if (!isReporterAttached) {
            ExtentSparkReporter spark = new ExtentSparkReporter("target/extent-report.html");
            extent.attachReporter(spark);
            isReporterAttached = true;
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        String className = result.getTestClass().getName();
        String testName = className.substring(className.lastIndexOf(".") + 1);
        ExtentTest extentTest = extent.createTest(testName);
        test.set(extentTest);
        extentTest.log(Status.INFO, "Testul a început: " + testName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTest extentTest = test.get();
        if (extentTest != null) {
            extentTest.log(Status.PASS, "Testul a trecut cu succes");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest extentTest = test.get();
        if (extentTest != null) {
            extentTest.log(Status.FAIL, "Testul a eșuat: " + result.getThrowable().getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTest extentTest = test.get();
        if (extentTest != null) {
            extentTest.log(Status.SKIP, "Testul a fost sărit");
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    public static ExtentTest getTest() {
        ExtentTest extentTest = test.get();
        if (extentTest == null) {
            // Inițializează un test temporar dacă nu există unul
            extentTest = extent.createTest("TemporaryTest");
            test.set(extentTest);
        }
        return extentTest;
    }
}