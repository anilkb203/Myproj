package auto_test;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import auto_pages.Ebayhomepage;
import auto_resource.ScrollElement;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class InterApp
{
    public AndroidDriver driver;
    public WebDriverWait w;
    Properties pr;
    Ebayhomepage ebhp;
    FileInputStream fis;
    private List<MobileElement> searchResults;


    @BeforeClass
    public void SetUp() throws InterruptedException, InterruptedException, Exception
    {
        
      
        System.out.println("*****************************************************");
         System.out.println("Setting up Ebay App capabilities");
       
      //maintain app & ard details 
  	    DesiredCapabilities dc= new DesiredCapabilities();
  	    dc.setCapability(CapabilityType.BROWSER_NAME,"");
  	    dc.setCapability("deviceName","Z60E418EA592884");
        dc.setCapability("platformName","android");
        dc.setCapability("platformVersion","7.0");
        dc.setCapability("autoDismissAlerts", true);
        dc.setCapability("appPackage","com.ebay.mobile");
        dc.setCapability("appActivity","com.ebay.mobile.activities.MainActivity");
        
        //appium server URL
         URL url=new URL("http://127.0.0.1:4723/wd/hub");
         driver=new AndroidDriver(url,dc);
         driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
         
          searchResults = new ArrayList<MobileElement> ();
    }
    
   
    @Test(priority=1,description="properties file  details")
    public void propertiesfilelocationdetails()
    {
        System.out.println("Ebay app started");

        try {
        	 
              fis=new FileInputStream("C:\\ak\\auto_proj\\src\\Properties\\ebayproperties.properties") ;
   	          pr=new Properties();
   	          pr.load(fis);
        	            
        }
        catch(Exception e)
        {
            System.out.println("Error trying to search");
            e.printStackTrace();
        }

    }
    
    @Test(priority=2,description="Display the collection search results")
    public void displayCollectedSearchResults() throws Exception
    {   
  	  searchItem(pr.getProperty("schitem")); // search item method
      processResults(); //based on the search results
    	for (int i = 0; i < searchResults.size(); i++)
		{
			String title = searchResults.get(i).findElement(By.xpath("//android.widget.TextView[@resource-id='com.ebay.mobile:id/textview_item_title']")).getText();
			String price = searchResults.get(i).findElement(By.xpath("//android.widget.TextView[@resource-id='com.ebay.mobile:id/textview_item_price']")).getText();
			System.out.println("Title : "+ title + ", Price :" + price );
		}
    	    	// select the item in search_results randomly
        Random r = new Random();
        int index = r.nextInt(searchResults.size());
        String ExpectedPrice = searchResults.get(index).findElement(By.xpath("//android.widget.TextView[@resource-id='com.ebay.mobile:id/textview_item_price']")).getText();
        String ExpectedTitle = searchResults.get(index).findElement(By.xpath("//android.widget.TextView[@resource-id='com.ebay.mobile:id/textview_item_title']")).getText();
        searchResults.get(index).click();//Clicking to random Item page
        Thread.sleep(5000);
        String Actualtitle = driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.ebay.mobile:id/textview_item_name']")).getText();
        String Actualprice = driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.ebay.mobile:id/converted_prices_textview']")).getText();
       
       try {
    	    MobileElement itemList = (MobileElement) driver.findElement(By.id("com.ebay.mobile:id/pager"));
    	   ScrollElement se = new ScrollElement((AndroidDriver<MobileElement>) driver);
    	   MobileElement me = se.ScrollToElement(itemList,"DESCRIPTION");
    	   me.click();  // causing an exception, working on it.
       }catch(Exception e){
    	   e.printStackTrace();
       }
        
    // Assertions for  selected random element    product title & price 
        Assert.assertEquals(Actualtitle,ExpectedTitle,"Random element title test passed");
        Assert.assertEquals(Actualprice,ExpectedPrice,"Random element price test passed");
       // Assert.assertEquals(ActualDescription,ExpectedDescription,"Random element description test passed");
    }
    
    // Display the device orientation details
  @Test(priority=3,description="Display the device orientation details")
   public void displayCurrentOrientation() throws Exception
    {
	 
	  String x=driver.getOrientation().name();
	  System.out.println(" the current orientation is "+ x);
	/*if(x.equalsIgnoreCase("PORTRAIT"))
	{
		  driver.rotate(ScreenOrientation.LANDSCAPE);	
	      Thread.sleep(5000);
	}
	else
	{
	      driver.rotate(ScreenOrientation.PORTRAIT);
	      Thread.sleep(5000);
	 }*/
	Assert.assertEquals("PORTRAIT", "PORTRAIT","Device Orientation test passed");
}
    @AfterClass
    public void tearDown() throws Exception
    {
        System.out.println("Destroying Test Environment");

        if(driver!=null)
        {
            System.out.println("******************************************************");
            System.out.println("Destroying Test Environment");
            driver.quit();
        }
    }

    // searchItem functionality
    public void searchItem(String schitem) throws InterruptedException, IOException
    {
        try
        {  
            searchResults.clear();  // function for search results
            String ExpectedItem = pr.getProperty("schitem")	;
            System.out.println("searchItem:" + pr.getProperty("schitem"));
            w=new WebDriverWait(driver,100); // explicit wait initialization
            // wait until searchbox is visibile
            w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(pr.getProperty("search_item"))));

            driver.findElement(By.xpath(pr.getProperty("search_item"))).click(); // click on the current focus of the element
            // input the search keyword (ex- 65-Inch TV)
            driver.findElement(By.xpath(pr.getProperty("search_item"))).sendKeys(pr.getProperty("schitem"));

            //click on the search icon
            w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.ImageView[@bounds='[396,792][480,854]']"))); 
            // search icon element
            WebElement e = driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.ImageView\").instance(10)");
            e.click();
           
            // close the tooltip
            w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@bounds='[18,192][401,293]']")));
            WebElement e1 = driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.TextView\").instance(0)");
            e1.click();
            Thread.sleep(5000);
            // display the input string
          w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@resource-id='com.ebay.mobile:id/title']")));
          WebElement  e2= driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.ebay.mobile:id/title']"));
          String ActualItem=e2.getText();
          
          System.out.println("the display string:" + ActualItem);
            // validation for input string
            Assert.assertEquals(ActualItem, ExpectedItem,"search input test passed");
        }
        catch(NoSuchElementException e)
        {
            System.out.println("Error for  trying to search");
            e.printStackTrace();
        }
    }

    //display and process the results 
    public void processResults() throws InterruptedException, IOException
    {
        try
        {
            System.out.println("i am in processResults");
            w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[contains(@text,'Results')]")));

            //Scan for result list size
            String resultCount=driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'Results')]")).getText();
            int resultSize = Integer.parseInt(resultCount.replaceAll("\\D",""));
            System.out.println(" the resultSi is :"+resultSize); // resultSize
            
            int MaxResultSize = 10; // this size can be changed depends on the display of search_results
            
            if ( resultSize > MaxResultSize) 
              {
            	
            	resultSize = MaxResultSize;
            	System.out.println("Restricting resultsize to process to " + resultSize);
            	
               }
  
            // ScrollElement functionality
            ScrollElement se = new ScrollElement((AndroidDriver<MobileElement>) driver);
            int count = 0;
            int elementsFound = 0;

            List<String> titleList = new ArrayList<String>();
           
            while(2>1)
            {
                if ( elementsFound >= resultSize ) break;
                ++count;

                if (count > resultSize *2 ) {

                    System.out.println("Aborting. Check scrolling logic");
                    return;
                }

                try
                {

                    //Read and scroll through elements.
                    List<MobileElement> ele =  driver.findElements(By.xpath("//android.widget.RelativeLayout[@resource-id='com.ebay.mobile:id/cell_collection_item']"));

                    System.out.println("While Iteration: " + count + ":Elements found :" + ele.size());
                    
                    MobileElement startElement = null;
                    MobileElement endElement = null;

                    int endCount = 0;
                    boolean isTitle = false;

                    for(int i=0;i<ele.size();i++)
                    {
                        isTitle = false;
    
                        try {

                            MobileElement myEle = ele.get(i);

                            if (i == 0 ) {

                                startElement = myEle.findElement(By.xpath("//android.widget.TextView[@resource-id='com.ebay.mobile:id/textview_item_title']"));
                                endElement = null;
                            }
                           
                            String title = myEle.findElement(By.xpath("//android.widget.TextView[@resource-id='com.ebay.mobile:id/textview_item_title']")).getText();
                            isTitle = true;
                            System.out.println("Item : " + i + " Title:"  + title );
                            if(titleList.contains(title) ) continue; 

                            String price = ele.get(i).findElement(By.xpath("//android.widget.TextView[@resource-id='com.ebay.mobile:id/textview_item_price']")).getText();
                            //endCount++;

                            System.out.println("Item : " + i + ": "  + title + " Price: " + price );

                            if(!titleList.contains(title) ) {

                                System.out.println("Adding Item to list : "  + title + " Price: " + price );
                                titleList.add(title);
                                searchResults.add(myEle);
                                ++elementsFound;
                                System.out.println(elementsFound + ": Adding Item to list : "   + title + " Price: " + price );

                            }
                                System.out.println("Item : " + elementsFound + ": "  + title + " Price: " + price );
                            if ( elementsFound >= resultSize ) break;

                        }  
                         catch (Exception ex) {

                            
                            if ( !isTitle) {

                                if ( i != 0) {

                                  endElement  = ele.get(i-1);
                                }else{

                                    //Means No title even in top element.
                                    startElement = ele.get(0);
                                    
                                }
                                System.out.println("price Not found. Breaking to Scrolling");
                                break;

                            }
                            else {

                                
                                endElement = ele.get(i);
                                System.out.println("price Not found. Breaking to Scrolling");
                                break;
                            }


                        }
                    
                    }

                    se.scrollByElements(startElement, endElement);
                    Thread.sleep(1000);

                }
                catch(Exception ex)
                {
                        System.out.println("Exception :" + ex.getMessage());
                        
                }
            }
              Thread.sleep(2000);
            System.out.println("Items added total: " + searchResults.size() );
         
        }
        catch(Exception ex)
        {
        	   ex.getMessage();
        }
    }
}
	
