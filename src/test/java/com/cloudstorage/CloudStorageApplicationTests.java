package com.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() { WebDriverManager.chromedriver().setup(); }

	@BeforeEach
	public void beforeEach() { this.driver = new ChromeDriver(); }

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}
	//helper methods
	public void registerMethod(){
			driver.get("http://localhost:" + this.port + "/signup");
			WebElement inputField = driver.findElement(By.id("inputFirstName"));
			inputField.sendKeys("Jane");
			inputField = driver.findElement(By.id("inputLastName"));
			inputField.sendKeys("Doe");
			inputField = driver.findElement(By.id("inputUsername"));
			inputField.sendKeys("janedoe");
			inputField = driver.findElement(By.id("inputPassword"));
			inputField.sendKeys("aX38de-+ee");
			WebElement submitButton = driver.findElement(By.id("submit-button"));
			submitButton.click();
		}
	public void loginMethod() {
		driver.get("http://localhost:" + this.port + "/login");
		WebElement inputField =  driver.findElement(By.id("inputUsername"));
		inputField.sendKeys("janedoe");
		inputField = driver.findElement(By.id("inputPassword"));
		inputField.sendKeys("aX38de-+ee");
		WebElement loginButton = driver.findElement(By.id("submit-button"));
		loginButton.click();
	}

	public void accessTab(String name) throws InterruptedException {
//		driver.get("http://localhost:" + this.port + "/home");
		Thread.sleep(2000);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		WebElement tab = driver.findElement(By.id(name));
		jse.executeScript("arguments[0].click()", tab);
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void homePageAccessBlocked() {
		driver.get("http://localhost:" + this.port + "/home");
		//should redirect to login page
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void registerUser(){
		//signup as a new user
		registerMethod();
		//log in as previously registered user
		loginMethod();
		//homepage is accessible
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Home", driver.getTitle());
		//once logging out, homepage is no longer accessible
		WebElement logoutButton = driver.findElement(By.id("logout-button"));
		logoutButton.click();
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}
	@Test
	public void createNote() throws InterruptedException {
		loginMethod();
		accessTab("nav-notes-tab");
		WebElement newNoteButton = driver.findElement(By.id("create-note"));
		new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(newNoteButton)).click();
		WebElement titleInput = driver.findElement(By.id("note-title"));
		WebElement descriptionField = driver.findElement(By.id("note-description"));
		new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(titleInput)).sendKeys("To-Do List");
		new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(descriptionField)).sendKeys("1. Walk Dog. 2. Study Java");
		WebElement submitButton = driver.findElement(By.id("save-changes"));
		submitButton.click();
		Assertions.assertEquals("Result", driver.getTitle());
		Assertions.assertEquals("Success", driver.findElement(By.className("display-5")).getText());
	}

	@Test
	public void editNote() throws InterruptedException {
		loginMethod();
		accessTab("nav-notes-tab");

	}

	@Test
	public void deleteNote(){}

	@Test
	public void createCredential(){}

	@Test
	public void editCredential(){}

	@Test
	public void deleteCredential(){}

}
