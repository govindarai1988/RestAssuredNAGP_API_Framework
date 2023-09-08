package com.nagarro.config;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {

    public static ExtentReports extent;
    private static ExtentTest test;

    public static ExtentReports getInstance() {

        if (extent == null) {
            extent = new ExtentReports();
            ExtentSparkReporter htmlReporter = new ExtentSparkReporter("Reports//"+getFilename());
            extent.attachReporter(htmlReporter);
            // Customize the report appearance
            htmlReporter.config().setDocumentTitle("API Test Report");
            htmlReporter.config().setReportName("API Automation Report");
            htmlReporter.config().setTheme(Theme.STANDARD);
        }
            return extent;

        }



    public static ExtentTest createTest(String testName) {

        test = extent.createTest(testName);
        return test;
    }



    public static void flushReport() {

        if (extent != null) {
            extent.flush();

        }


    }
    public static String getFilename(){
        String fileName = new SimpleDateFormat("'ExtentReport_'yyyyMMddHHmm'.html'").format(new Date());
        return fileName;
    }

}

