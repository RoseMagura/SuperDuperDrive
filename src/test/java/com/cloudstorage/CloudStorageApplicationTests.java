package com.cloudstorage;

import com.cloudstorage.model.Note;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Random;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-extensions");
		options.addArguments("--auto-open-devtools-for-tabs");
		this.driver = new ChromeDriver(options);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}
	public String generateRandom(){
		return RandomStringUtils.random(10, true, true);
	}
	//helper methods
	public String[] registerMethod(){
			driver.get("http://localhost:" + this.port + "/signup");
			// Generating random values to prevent repetition
			String firstName = generateRandom();
			String lastName = generateRandom();
			String username = generateRandom();
			String password = generateRandom();
			WebElement inputField = driver.findElement(By.id("inputFirstName"));
			inputField.sendKeys(firstName);
			inputField = driver.findElement(By.id("inputLastName"));
			inputField.sendKeys(lastName);
			inputField = driver.findElement(By.id("inputUsername"));
			inputField.sendKeys(username);
			inputField = driver.findElement(By.id("inputPassword"));
			inputField.sendKeys(password);
			WebElement submitButton = driver.findElement(By.id("submit-button"));
			submitButton.click();
			String credentials[] = new String[2];
			credentials[0] = username;
			credentials[1] = password;
			return credentials;
		}
	public String loginMethod(){
		String [] param = new String[2];
		param[0] = "janedoe";
		param[1] = "aX38de-+ee";
		return loginMethod(param);
	}
	public String loginMethod(String[] credentials) {
		driver.get("http://localhost:" + this.port + "/login");
		WebElement inputField =  driver.findElement(By.id("inputUsername"));
		inputField.sendKeys(credentials[0]);
		inputField = driver.findElement(By.id("inputPassword"));
		inputField.sendKeys(credentials[1]);
		WebElement loginButton = driver.findElement(By.id("submit-button"));
		loginButton.click();
		return "OK";
	}
	public void fillForm(String text, String field, WebDriver driver){
		WebElement input = driver.findElement(By.id(field));
		new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(input)).clear();
		input.sendKeys(text);
	}
	public void accessTab(String name) throws InterruptedException {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		if(!driver.getTitle().equals("Home")){
			Thread.sleep(2000);
			WebElement tab = (new WebDriverWait(driver, 30))
					.until(ExpectedConditions.presenceOfElementLocated(By.id(name)));
			jse.executeScript("arguments[0].click()", tab);
		}
		else{
			WebElement tab = (new WebDriverWait(driver, 30))
				.until(ExpectedConditions.presenceOfElementLocated(By.id(name)));
			jse.executeScript("arguments[0].click()", tab);
		}
	}
	public void setupNote(WebDriver driver) throws InterruptedException {
		accessTab("nav-notes-tab");
		WebElement newNoteButton = driver.findElement(By.id("create-note"));
		new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(newNoteButton)).click();
		fillForm("To-Do List", "note-title",driver);
		fillForm("1. Walk Dog. 2.Study Java", "note-description", driver);
		WebElement submitButton = driver.findElement(By.id("save-changes"));
		submitButton.click();
	}
	public void setupCredential(WebDriver driver){
		WebElement createCredentialButton = driver.findElement(By.id("create-credential"));
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(createCredentialButton)).click();
		fillForm("www.msn.com/", "credential-url",driver);
		fillForm("jjabrams", "credential-username", driver);
		fillForm("j000008?!", "credential-password", driver);
		WebElement submitButton = driver.findElement(By.id("save-credential"));
		submitButton.click();
	}
	public String encryptValue(String data, String key) {
		byte[] encryptedValue = null;
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			encryptedValue = cipher.doFinal(data.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
				| UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException e) {
			System.out.println(e.getMessage());
		}

		return Base64.getEncoder().encodeToString(encryptedValue);
	}
	public static boolean searchForId(String id, WebDriver driver) throws InterruptedException {
		List<WebElement> rows = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("tr")));
		for (WebElement row: rows) {
			if(row.getAttribute("id").equals(id)){
				return true;
			}
		}
		return false;
	}
	public void returnHome(WebDriver driver) throws InterruptedException {
		WebElement link = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.tagName("a")));
		link.sendKeys(Keys.ENTER);
	}
	public void checkNumber(String type) throws InterruptedException {
		List<WebElement> buttons = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(type)));
		if(buttons.size() <1){
			setupNote(driver);
			returnHome(driver);
		}
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
		String[] credentials = registerMethod();
		System.out.println(Arrays.toString(credentials));
		//log in as previously registered user
		loginMethod(credentials);
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
		setupNote(driver);
		Assertions.assertEquals("Result", driver.getTitle());
		Assertions.assertEquals("Success", driver.findElement(By.className("display-5")).getText());
		returnHome(driver);
		accessTab("nav-notes-tab");
		WebElement title = driver.findElement(By.id("To-Do List"));
		Assertions.assertEquals("To-Do List",title.getAttribute("innerHTML"));
		WebElement description = driver.findElement(By.id("1. Walk Dog. 2.Study Java"));
		Assertions.assertEquals("1. Walk Dog. 2.Study Java",description.getAttribute("innerHTML"));
	}

	@Test
	public void editNote() throws InterruptedException {
		loginMethod();
		accessTab("nav-notes-tab");
		checkNumber("edit-note");
		accessTab("nav-notes-tab");
		WebElement editNoteButton = driver.findElement(By.id("edit-note"));
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(editNoteButton)).click();
		fillForm("Goals", "note-title", driver);
		fillForm("1. Exercise everyday.", "note-description", driver);
		WebElement submitButton = driver.findElement(By.id("save-changes"));
		submitButton.click();
		Assertions.assertEquals("Result", driver.getTitle());
		Assertions.assertEquals("Success", driver.findElement(By.className("display-5")).getText());
		returnHome(driver);
		accessTab("nav-notes-tab");
		Assertions.assertEquals("Goals", driver.findElement(By.id("Goals")).getAttribute("innerHTML"));
		Assertions.assertEquals("1. Exercise everyday.", driver.findElement(By.id("1. Exercise everyday.")).getAttribute("innerHTML"));
	}

	@Test
	public void deleteNote() throws InterruptedException {
		loginMethod();
		accessTab("nav-notes-tab");
		checkNumber("delete-note");
		accessTab("nav-notes-tab");
		WebElement deleteNoteButton = driver.findElement(By.id("delete-note"));
		String id = deleteNoteButton.getAttribute("value");
		new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(deleteNoteButton)).click();
		Assertions.assertEquals("Result", driver.getTitle());
		Assertions.assertEquals("Success", driver.findElement(By.className("display-5")).getText());
		returnHome(driver);
		accessTab("nav-notes-tab");
		Assertions.assertFalse(searchForId(id, driver));
	}

	@Test
	public void createCredential() throws InterruptedException {
			loginMethod();
			accessTab("nav-credentials-tab");
			setupCredential(driver);
			Assertions.assertEquals("Result", driver.getTitle());
			Assertions.assertEquals("Success", driver.findElement(By.className("display-5")).getText());
			returnHome(driver);
			accessTab("nav-credentials-tab");
			WebElement url = driver.findElement(By.id("www.msn.com/"));
			Assertions.assertEquals("www.msn.com/",url.getAttribute("innerHTML"));
			WebElement username = driver.findElement(By.id("jjabrams"));
			Assertions.assertEquals("jjabrams",username.getAttribute("innerHTML"));
			String encryptedPassword = encryptValue("j000008?!", "nUu6QM51eXDp1tcEvGMiTQ==");
			WebElement password = driver.findElement(By.id(encryptedPassword));
			Thread.sleep(2000);
			Assertions.assertNotEquals("j000008?!", password.getAttribute("innerHTML"));
	}

	@Test
	public void deleteCredential() throws InterruptedException {
		loginMethod();
		accessTab("nav-credentials-tab");
		checkNumber("delete-credential");
		accessTab("nav-credentials-tab");
		WebElement deleteCredentialButton = driver.findElement(By.id("delete-credential"));
		String id = deleteCredentialButton.getAttribute("value");
		new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(deleteCredentialButton)).click();
		Assertions.assertEquals("Result", driver.getTitle());
		Assertions.assertEquals("Success", driver.findElement(By.className("display-5")).getText());
		returnHome(driver);
		accessTab("nav-credentials-tab");
		Assertions.assertFalse(searchForId(id, driver));
	}

	@Test
	public void editCredential() throws InterruptedException {
		loginMethod();
		accessTab("nav-credentials-tab");
		checkNumber("edit-credential");
		accessTab("nav-credentials-tab");
		WebElement editCredentialButton = driver.findElement(By.id("edit-credential"));
		new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(editCredentialButton)).click();
		fillForm("www.msn.com/email", "credential-url", driver);
		fillForm("jjabrams**", "credential-username", driver);
		fillForm("j000008?!__", "credential-password", driver);
		WebElement submitButton = driver.findElement(By.id("save-credential"));
		submitButton.click();
		Assertions.assertEquals("Result", driver.getTitle());
		Assertions.assertEquals("Success", driver.findElement(By.className("display-5")).getText());
		returnHome(driver);
		Assertions.assertEquals("www.msn.com/email", driver.findElement(By.id("www.msn.com/email")).getAttribute("innerHTML"));
		Assertions.assertEquals("jjabrams**", driver.findElement(By.id("jjabrams**")).getAttribute("innerHTML"));
		Assertions.assertEquals("7WOb+UTwRWiN7cCHlf2gow==", driver.findElement(By.id("7WOb+UTwRWiN7cCHlf2gow==")).getAttribute("innerHTML"));
	}
}
