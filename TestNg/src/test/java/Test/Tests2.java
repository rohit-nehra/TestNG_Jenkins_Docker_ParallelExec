package Test;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import Pages.Homepage;

public class Tests2 extends TestBase{

	@Test
	public void Test2() throws InterruptedException
	{
		String url="https://rediff.com";
		test.setDescription("URL in test data is "+url);
		driver.get(url);
		Homepage homepageObj=new Homepage(driver);
		homepageObj.clickRediffmailLink();
	}

}
