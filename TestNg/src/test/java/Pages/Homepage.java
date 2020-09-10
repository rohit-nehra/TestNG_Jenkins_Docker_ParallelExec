package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Homepage {
	public RemoteWebDriver driver;
	public Homepage(RemoteWebDriver driver)
	{
		this.driver=driver;
	}
	
	public Rediffmail clickRediffmailLink() {
		driver.findElement(linkRediffmail).click();
		return new Rediffmail(driver);
	}

	
	public By linkRediffmail=By.linkText("Rediffmail");
}
