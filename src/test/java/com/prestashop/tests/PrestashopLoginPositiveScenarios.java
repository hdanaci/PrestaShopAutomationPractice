package com.prestashop.tests;

import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PrestashopLoginPositiveScenarios {

	WebDriver driver;
	Faker faker;
	String eMail;
	String firstName;
	String lastName;
	int dd;
	int mm;
	int yy;
	String address;
	String city;
	int state;
	String zipCode;
	String phoneNumber;

	@BeforeClass
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		faker = new Faker();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().window().fullscreen();

	}

	@BeforeMethod
	public void setData() {
		driver.get("http://automationpractice.com/index.php");
		this.firstName = faker.name().firstName();
		this.lastName = faker.name().lastName();
		this.eMail = firstName + lastName + "@gmail.com";
		this.dd = faker.number().numberBetween(1, 30);
		this.mm = faker.number().numberBetween(1, 12);
		this.yy = faker.number().numberBetween(1, 50);
		this.address = faker.address().streetAddress();
		this.city = faker.address().city();
		this.state = faker.number().numberBetween(1, 50);
		String zip = faker.address().zipCode();
		this.zipCode = zip.substring(0, 5);
		this.phoneNumber = faker.phoneNumber().cellPhone();

	}

	@Test
	public void loginTest() throws InterruptedException {
		driver.findElement(By.xpath("//div[@class='header_user_info']")).click();
		driver.findElement(By.xpath("//input[@id='email_create']")).sendKeys(eMail);
		driver.findElement(By.xpath("//button[@id='SubmitCreate']")).click();

		driver.findElement(By.xpath("//input[@id='customer_firstname']")).sendKeys(firstName);
		driver.findElement(By.xpath("//input[@id='customer_lastname']")).sendKeys(lastName);
		driver.findElement(By.xpath("//input[@id='passwd']")).sendKeys(lastName + firstName + "*");

		Select bDay = new Select(driver.findElement(By.xpath("//select[@id='days']")));
		bDay.selectByIndex(dd);
		Select bMonth = new Select(driver.findElement(By.xpath("//select[@id='months']")));
		bMonth.selectByIndex(mm);
		;
		Select bYear = new Select(driver.findElement(By.xpath("//select[@id='years']")));
		bYear.selectByIndex(yy);

		driver.findElement(By.xpath("//input[@id='firstname']")).sendKeys(firstName);
		driver.findElement(By.xpath("//input[@id='lastname']")).sendKeys(lastName);

		driver.findElement(By.xpath("//input[@id='address1']")).sendKeys(address);
		driver.findElement(By.xpath("//input[@id='city']")).sendKeys(city);
		Select sState = new Select(driver.findElement(By.xpath("//Select[@id='id_state']")));
		sState.selectByIndex(state);
		driver.findElement(By.xpath("//input[@id='postcode']")).sendKeys(zipCode);
		Select sCountry = new Select(driver.findElement(By.xpath("//select[@id='id_country']")));
		sCountry.selectByIndex(1);

		driver.findElement(By.xpath("//input[@id='phone_mobile']")).sendKeys(phoneNumber);
		driver.findElement(By.xpath("//input[@id='alias']")).sendKeys("My Address.");

		driver.findElement(By.xpath("//button[@id='submitAccount']")).click();

		String actual = driver.findElement(By.xpath("//div[@class='header_user_info']/a/span")).getText();
		String expected = firstName + " " + lastName;
		assertEquals(actual, expected);

		Thread.sleep(5000);
		driver.findElement(By.xpath("//div[2][@class='header_user_info']")).click();

	}

	@AfterClass
	public void driverClosing() {
		driver.quit();
	}

}
