package auto_pages;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class Ebayhomepage
{
	 protected AndroidDriver driver;
	 
	 @FindBy(xpath="//*[@text='Search for everything']")
	 public MobileElement search_item;
	 
	 public Ebayhomepage(AndroidDriver driver)
	 {
		 this.driver=driver;
		 PageFactory.initElements(driver,this);
	 }
	 
	 public void fillSearch(String item)
	 {
		 search_item.sendKeys(item);
	 }
	 

}
