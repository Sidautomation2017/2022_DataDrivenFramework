package com.edusoln.base;

import java.io.IOException;

import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.edusoln.runner.TestRunner;


public class Listners implements ITestListener  {

	

	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("============On test Start=========");
		
	}
	
	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println("============On test failure=========");
		TestRunner.test.log(Status.FAIL, "Test is failed").addScreenCaptureFromPath(CommonFunctions.getScreenshot(TestRunner.driver, "LoginFilure"));		
			
	}
	
	@Override
	public void onTestSuccess(ITestResult result) {
	
		TestRunner.test.log(Status.INFO, "======Test is Scucesfull=====");
	}
	
	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("============On test Skipped=========");	
	
	}
	

}
