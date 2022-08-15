package com.edusoln.runner;

import java.io.IOException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.LoggerAdapter;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.edusoln.base.CommonFunctions;
import com.edusoln.base.Constants;
import com.edusoln.pom.AssignLeavepage;
import com.edusoln.pom.LoginPage;
import com.edusoln.utilities.ExcelReader;
import com.edusoln.utilities.ExtentReportGenerator;
import com.edusoln.utilities.PropertyReader;


public class TestRunner extends CommonFunctions{

	public static WebDriver driver;

	public static ExtentTest test;
	ExtentReports report;
	static Logger log=LogManager.getLogger(TestRunner.class);

	@BeforeTest
	public void beforeTest() {
		report = ExtentReportGenerator.extentReportsetup();
	}

	@DataProvider(name = "dp")
	public Object[][] datasupplier() throws IOException {

		Object[][] data = ExcelReader.excelReader();

		return data;
	}

	@BeforeMethod
	public void browserSetup() {
		driver = CommonFunctions.browserLaunch(PropertyReader.getProperty(Constants.BROWSER));
		driver.get(PropertyReader.getProperty(Constants.BASEURL));
	}

		
	@Test(enabled = true, dataProvider = "dp")
	public void testcase(Map<Object, Object> data) {
		if(getStringData(data.get("Execute")).equalsIgnoreCase("Yes")){
		test = report.createTest(getStringData(data.get("TestCase_Name")));
		LoginPage login = new LoginPage(driver);
		login.login(test, data);		
		AssignLeavepage assignleave = new AssignLeavepage(driver);
		assignleave.assignLeave(test, data);
		softassert.assertAll("All soft assert executed");
	}
		else {
			log.debug("Test is Skipped:-"+getStringData(data.get("TestCase_Name")));
			//throw new SkipException("Test is Skipped");
		
			
		}
}

	@AfterMethod
	public void teardown() {		
		driver.quit();
	}

	@AfterTest
	public void afterTest() {
		report.flush();
	}

}
