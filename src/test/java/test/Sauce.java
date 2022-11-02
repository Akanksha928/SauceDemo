package test;

import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import org.testng.annotations.BeforeTest;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;

public class Sauce {

	WebDriver driver;
	ExtentTest test;
	ExtentReports report;

	@Test
	public void SauceDemo() throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://saucedemo.com");
		driver.manage().window().maximize();
		test.log(Status.PASS, "Navigated to login page.");
		
		/* Standard User */

		driver.findElement(By.id("user-name")).sendKeys("standard_user");
		driver.findElement(By.id("password")).sendKeys("secret_sauce");
		driver.findElement(By.id("login-button")).click();
		test.log(Status.PASS, "Standard user logged in.");
		
		/* Add to cart */

		driver.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-backpack\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-bike-light\"]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a")).click();

		driver.get("https://saucedemo.com");
		
		/* Locked Out User */

		driver.findElement(By.id("user-name")).sendKeys("locked_out_user");
		driver.findElement(By.id("password")).sendKeys("secret_sauce");
		driver.findElement(By.id("login-button")).sendKeys(Keys.ENTER);

		/* Screenshot 1 */

		Screenshot s1 = new AShot().takeScreenshot(driver);
		try {
			ImageIO.write(s1.getImage(), "PNG",
					new File("C:\\Users\\Administrator\\Java Workspace\\SauceDemo\\img1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String error1 = driver
				.findElement(
						By.cssSelector("#login_button_container > div > form > div.error-message-container.error > h3"))
				.getText();
		test.fail("Error displayed while logging in locked out user: " + error1, MediaEntityBuilder
				.createScreenCaptureFromPath("C:\\Users\\Administrator\\Java Workspace\\SauceDemo\\img1.png").build());

		driver.navigate().refresh();
		
		/* Problem User */ 
		
		driver.findElement(By.id("user-name")).sendKeys("problem_user");
		driver.findElement(By.id("password")).sendKeys("secret_sauce");
		driver.findElement(By.id("login-button")).click();
		test.log(Status.PASS, "Problem user logged in");
		
		/* Screenshot 2 */
		
		Screenshot s2 = new AShot().takeScreenshot(driver);
		try {
			ImageIO.write(s2.getImage(), "PNG",
					new File("C:\\Users\\Administrator\\Java Workspace\\SauceDemo\\img2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		test.fail("Error with problem user", MediaEntityBuilder
				.createScreenCaptureFromPath("C:\\Users\\Administrator\\Java Workspace\\SauceDemo\\img2.png").build());
		
		
		/* Performance Glitch User */
		
		driver.get("https://saucedemo.com");
		driver.findElement(By.id("user-name")).sendKeys("performance_glitch_user");
		driver.findElement(By.id("password")).sendKeys("secret_sauce");
		driver.findElement(By.id("login-button")).click();
		test.log(Status.PASS, "Performance Glitch user logged in");
		
		
		/* Scroll Into View and Add to cart */
		
		WebElement product1 = driver.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-bolt-t-shirt\"]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", product1);
		product1.click();
		driver.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-fleece-jacket\"]")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a")).click();
		
		
		
		

//			Screenshot s = new AShot().takeScreenshot(driver);
//			try {
//				ImageIO.write(s.getImage(), "PNG",
//						new File("C:\\Users\\Administrator\\Java Workspace\\SauceDemo\\img.png"));
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			String error1 = driver.findElement(By.cssSelector("#login_button_container > div > form > div.error-message-container.error > h3")).getText();
//			test.fail("Error Displayed: "+ error1, MediaEntityBuilder
//					.createScreenCaptureFromPath("C:\\Users\\Administrator\\Java Workspace\\SauceDemo\\img.png")
//					.build());
//			String error1 = driver.findElement(By.cssSelector("#login_button_container > div > form > div.error-message-container.error > h3")).getText();
//			test.info(error1);
//			test.fail("Failed", test.addScreenCaptureFromPath("C:\\Users\\Administrator\\Java Workspace\\SauceDemo\\img.png"));
//			String error1 = driver.findElement(By.cssSelector("#login_button_container > div > form > div.error-message-container.error > h3")).getText();
//			test.log(Status.FAIL, error1);

	}

	@BeforeTest
	public void beforeTest() {

		report = new ExtentReports();
		ExtentSparkReporter spark = new ExtentSparkReporter("target/spark.html");
		report.attachReporter(spark);
		test = report.createTest("ExtentDemo");
	}

	@AfterTest
	public void afterTest() {
		report.flush();
	}

}
