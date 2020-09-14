package Test;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.tools.ant.util.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import com.google.common.io.Files;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class TestBase{
	
	public RemoteWebDriver driver;
	
	public static String reportName="Exec_report_"+ new SimpleDateFormat("yyyyMMddHHmm").format(new Date()) + ".html";
	
    public static ExtentReports extent=new ExtentReports(System.getProperty("user.dir") + "/Reports/"+reportName, true);
    
    public ExtentTest test;
	
	@BeforeMethod(alwaysRun = true)
    public void beforeTest(Method method) throws Exception {
				
		ChromeOptions options = new ChromeOptions();
	    options.addArguments("--incognito");
	    options.addArguments("--headless");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-gpu");
        options.addArguments("--start-maximixed");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-dev-shm-usage");
	    driver = new ChromeDriver(options);
	    driver.manage().window().maximize();
	    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	    test = extent.startTest(method.getName());
	    String url="https://rediff.com";
		test.setDescription("URL in test data is "+url); //Adding test data to test case in Report
		driver.get(url);
    }



    @AfterMethod(alwaysRun = true)
    public void afterTest(ITestResult result) throws Exception {
	if (result.getStatus() == ITestResult.SUCCESS) 
    	{
	    test.log(LogStatus.PASS, "Passed");
	} else {
		TakesScreenshot ts = (TakesScreenshot) driver;
	    File source = ts.getScreenshotAs(OutputType.FILE);
	    String dest = System.getProperty("user.dir") + "/Reports/"
		    + new SimpleDateFormat("yyyyMMddHHmm").format(new Date()) + "screenShotName" + ".png";
	    File destination = new File(dest);
	    Files.copy(source, destination);
	    test.log(LogStatus.FAIL,
		    result.getThrowable().getMessage() + " " + result.getThrowable().getStackTrace() + test.addScreenCapture(dest));
	}
	
	extent.endTest(test);
	extent.flush();
	driver.quit();
    }
    
    @AfterSuite(alwaysRun = true)
    public void afterSuite() throws Exception { 
    	extent.close(); 	
    }
   

}
