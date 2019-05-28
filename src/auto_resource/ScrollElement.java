package auto_resource;


import java.time.Duration;

import org.openqa.selenium.Point;


import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.offset.PointOption;

public class ScrollElement 
{
	AndroidDriver<MobileElement> driver;
	public ScrollElement(AndroidDriver<MobileElement> driver)
	{
		this.driver = driver ;
	}
	public MobileElement scrollToExactElement(MobileElement ele,String str) 
	{
		return ((AndroidElement) ele)
				.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView("
						+ "new UiSelector().text(\""+str+"\"));");

	}

	 public MobileElement ScrollToElement(MobileElement ele, String str) 
	 {
		 return ((AndroidElement) ele)
			.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView("
					+ "new UiSelector().textContains(\""+str+"\"));");
		 
	}
	 public void ScrollStepWise(MobileElement ele, int step) 
	 {
		 ((AndroidElement) ele)
			.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollForward())");
	}
	 
	 public MobileElement ScrolltoInstance (MobileElement ele, int step) 
	 {
		 return ((AndroidElement) ele)
			.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView("
					+ "new UiSelector().resourceId(\"com.ebay.mobile:id/cell_collection_item\").instance("+step+"))");
		 
	}
	 
     public void flingToEnd(MobileElement ele, int step)
     {
             ((AndroidElement) ele)
                    .findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).flingToEnd("+step+")");
    }


     //Generic function for Scroll
     public void scrollByElements(MobileElement startElement, MobileElement endElement) 
     {

    	 Point point = startElement.getCenter();
    	 int endX = point.x;
    	 int endY = point.y;
    	 
    	 
    	 int startX, startY;
    	 
    	 if(endElement != null ) {

    	    point = endElement.getCenter();
    	    startX = point.x;
    	    startY = point.y;
    	 }else {
    		 
    		 startX = endX ;
    		 startY = endY + 40;
    	 }
    	 
    	 System.out.println("Move From: (" +  startX + "," + endY + "), To " + "(" + startX + "," + startY + ")");
    	
    	 TouchAction actions = new TouchAction(driver);
     	 actions.longPress(PointOption.point(startX, startY))
             .moveTo(PointOption.point(endX, endY))
             .release()
             .perform();

     }

}
