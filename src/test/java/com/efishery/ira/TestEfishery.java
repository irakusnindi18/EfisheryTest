package com.efishery.ira;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by: Ira Kusnindi
 * Email: irakusnindi1805@gmail.com
 * Date: 21-Juni-2020
 * Running test menggunakan browser firefox
 * IDE Eclipse
 */

public class TestEfishery {
	static WebDriver driver;

	private static String url = "https://fresh.efishery.com";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		logInfo("Start chrome browser");
		String projectPath = System.getProperty("user.dir");
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		FirefoxOptions option = new FirefoxOptions();
		capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS,option);
		System.setProperty("webdriver.gecko.driver", projectPath+"/drivers/geckodriver/geckodriver.exe");	    
		driver= new FirefoxDriver(capabilities);
		
		logInfo("Set the page load timeout for any page load");
		driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
		
		logInfo("Navigate to url : " + url);
		driver.navigate().to(url);
		
		logInfo("Maximize window");
		driver.manage().window().maximize();
	}

	//Memastikan dapat memilih kota
	@Test(priority=1)
	public void test1() {
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		logInfo("Set implicit wait for all the activities on the browser");
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
		logInfo("Set selenium script timeout");
		driver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
		
		// Memastikan dapat memilih kota
		logInfo("Click Location");
		boolean submitbuttonPresence=driver.findElement(By.xpath("//span[@class='f-16 f-bold']")).isDisplayed();
        System.out.println(submitbuttonPresence);
        FindElement(driver, By.xpath("//span[@class='f-16 f-bold']"), 7).click();
        logInfo("Dapat Memilih Kota");
        
        
        //span[@class='f-16 f-bold']
		logInfo("Location Can Select");
	
	
	}
	
	//Memastikan ada banners
	@Test(priority=2)
	public void test2() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		// Forward navigation banner
		for(int i =0;i<2;i++)
		{
		      driver.findElement(By.xpath("//span[@class='carousel-control-next-icon']")).click();
		      Thread.sleep(1000);
		  }

		// back navigation banner
		for(int j=0;j<2;j++)
		{
		     driver.findElement(By.xpath("//span[@class='carousel-control-prev-icon']")).click();
		     Thread.sleep(1000);
		}
		
		logInfo("Banner Displayed");
	
	}
	
	//Memastikan ada catalog options
	@Test(priority=3)
	public void test3() {
		driver.findElement(By.xpath("//div[@id='Channel-eFishery']")).click();
		Set<String> winids = driver.getWindowHandles();  
		System.out.println("Total number of windows/tabs are:" +winids.size());   
		String waEfishery = winids.iterator().next();     
		System.out.println(waEfishery);        
		driver.switchTo().window(waEfishery);
		
		driver.findElement(By.xpath("//div[@id='Channel-Blibli']")).click(); 
		System.out.println("Total number of windows/tabs are:" +winids.size());  
		String blibli = winids.iterator().next();   
		System.out.println(blibli);        
		driver.switchTo().window(blibli);
		
		driver.findElement(By.xpath("//div[@id='Channel-Bukalapak']")).click();
		System.out.println("Total number of windows/tabs are:" +winids.size());  
		String bukalapak = winids.iterator().next();  
		System.out.println(bukalapak);        
		driver.switchTo().window(bukalapak);
		
		driver.findElement(By.xpath("//div[@id='Channel-Tokopedia']")).click();
		System.out.println("Total number of windows/tabs are:" +winids.size());  
		String tokopedia = winids.iterator().next();     
		System.out.println(tokopedia);        
		driver.switchTo().window(tokopedia);
		
		logInfo("Catalog Options Successfull");
		
	}
	
	
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
		logInfo("Closing Browser");
		driver.quit();
		logInfo("DONE!");
	}
	
	public static void sleepPause(int ms) {
		
		try {
			Thread.sleep(ms);
		} catch (Exception e) {
			logInfo("Exception : " + e.toString() );
		}
		
	}
	
	private static WebElement FindElement(WebDriver driver, By by, int timeoutInSeconds) {
		
		try {
			
			WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			logInfo( "FindElement *** " + by + " *** Found");
			return driver.findElement(by);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logInfo( "FindElement --> " + by + " --> Not found");
		return null;
	}
	
	public static void logInfo(String msg) {
		
		System.out.println( LocalDateTime.now() + " : " + msg );
		
	}

}