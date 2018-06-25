package com.prestashop.tests;

import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PrestashopLoginNegativeScenarios {

	WebDriver driver;

	@BeforeClass
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().window().fullscreen();

	}

	@BeforeMethod
	public void goToPage() {
		driver.get("http://automationpractice.com/index.php");
	}

	@Test
	public void credentialsTest() {
		driver.findElement(By.xpath("//div[@class='header_user_info']")).click();
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("austin@gmail.com");
		driver.findElement(By.xpath("//input[@id='passwd']")).sendKeys("austin1234%");
		driver.findElement(By.xpath("//button[@id='SubmitLogin']")).click();

		assertEquals(driver.findElement(By.xpath("//li[.='Authentication failed.']")).getText(),
				"Authentication failed.");
	}

	@Test
	public void invalidEmailTest() {
		driver.findElement(By.xpath("//div[@class='header_user_info']")).click();
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("cybertry72");
		driver.findElement(By.xpath("//input[@id='passwd']")).sendKeys("CyberTry7272*");
		driver.findElement(By.xpath("//button[@id='SubmitLogin']")).click();

		assertEquals(driver.findElement(By.xpath("//li[.='Invalid email address.']")).getText(),
				"Invalid email address.");
	}

	@Test
	public void blankEmailTest() {
		driver.findElement(By.xpath("//div[@class='header_user_info']")).click();
		driver.findElement(By.xpath("//input[@id='email']")).clear();
		driver.findElement(By.xpath("//input[@id='passwd']")).sendKeys("CyberTry7272*");
		driver.findElement(By.xpath("//button[@id='SubmitLogin']")).click();

		assertEquals(driver.findElement(By.xpath("//li[.='An email address required.']")).getText(),
				"An email address required.");
	}

	@Test
	public void blankPasswordTest() {
		driver.findElement(By.xpath("//div[@class='header_user_info']")).click();
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("cybertry72@gmail.com");
		driver.findElement(By.xpath("//input[@id='passwd']")).clear();
		;
		driver.findElement(By.xpath("//button[@id='SubmitLogin']")).click();

		assertEquals(driver.findElement(By.xpath("//li[.='Password is required.']")).getText(),
				"Password is required.");
	}

	@AfterClass
	public void driverClosing() {
		driver.quit();
	}
}
