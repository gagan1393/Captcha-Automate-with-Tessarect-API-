package captchaTesting;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class Readcaptcha {

	public static void main(String[] args) throws IOException, TesseractException, InterruptedException {
		System.setProperty("webdriver.chrome.driver","D:/Selenium_software/chromedriver.exe");
		
		ChromeOptions opt = new ChromeOptions();
		opt.addArguments("--disable-notifications ");
		
		WebDriver driver = new ChromeDriver(opt);
		driver.manage().window().maximize();
		
		driver.get("https://www.irctc.co.in/nget/train-search");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		driver.findElement(By.xpath("//div[@class='h_menu_drop_button hidden-xs']")).click();
		driver.findElement(By.xpath("//button[contains(text(),'LOGIN')]")).click();
		
		Thread.sleep(2000);
		JavascriptExecutor js=(JavascriptExecutor)driver;
		
		for(int i=0;i<=100;i++)
		     {
		js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
		     }
		
		Thread.sleep(5000);
		
		File src = driver.findElement(By.xpath("//img[@id=\"nlpCaptchaImg\"]")).getScreenshotAs(OutputType.FILE);
		
		String path = System.getProperty("user.dir")+"./screenshots/captcha.png";
		FileHandler.copy(src, new File(path));
		
		//Tessarect API
		
		ITesseract image = new Tesseract();
		String imagetext = image.doOCR(new File(path));
		System.out.println(imagetext);
		String finalimagetext = imagetext.split("below")[1].replaceAll("[^a-zA-Z0-9]", "");
		System.out.println("final captcha is "+ finalimagetext);
		
		driver.findElement(By.id("nlpAnswer")).sendKeys(finalimagetext);
		
		driver.quit();
		
		
		
		

	}

}
