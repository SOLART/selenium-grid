package solovei.grid;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SeleniumGridTestNG {
	 	    	
    	WebDriver driver;
    	String baseURL, nodeURL, searchParam;
    	Logger log = LogManager.getLogger (SeleniumGridTestNG.class.getName());
    	    	
    	@BeforeTest
    	public void setUp() throws MalformedURLException {
    		baseURL = "https://www.google.ru/";
    		nodeURL = "http://localhost:4444/wd/hub";    		
    		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
    		driver = new RemoteWebDriver(new URL(nodeURL), capabilities);
    		capabilities.setBrowserName("firefox");
    		capabilities.setPlatform(Platform.VISTA);
    	}
    	  	
    	@Test
    	public void testFirefox() {
    		
    		searchParam = "Adsterra";
    		
    		driver.get(baseURL);
    		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    		if (driver.getCurrentUrl().equalsIgnoreCase(baseURL)) {
            	log.trace("Page reached");
            } else {
            	log.error("Page wasn't reached");
            }
            
            //Input search parameter
            WebElement element  = driver.findElement(By.name("q")); //".//*[@id='gs_htif0']"
            element.sendKeys("adsterra");
            element.submit();
                         
            //Get the element's text by Xpath
            String firstHeaderText = driver.findElement(By.xpath(".//*[@id='rso']/div[1]/div/div/h3/a")).getText();
            
            if (firstHeaderText.equalsIgnoreCase(searchParam)) {
            	log.trace("The header text equals to the parameter");      	
            } else {
            	log.error("The header text doesn't equal to the parameter"); 
            }
            
            log.info("First header text is: " + "\"" + firstHeaderText + "\"");
            log.info("Parameter to match is: " + "\"" + searchParam + "\"");
    	}     	    	
    	
    	@AfterTest
    	public void afterTest()	{
    		driver.quit();
    	}
    }

