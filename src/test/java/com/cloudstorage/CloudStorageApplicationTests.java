package com.cloudstorage;

import com.cloudstorage.model.Note;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

import com.cloudstorage.service.NoteService;
import com.cloudstorage.service.CredentialService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {
//	private NoteService noteService;
//
//	public CloudStorageApplicationTests(NoteService noteService) {
//		this.noteService = noteService;
//	}

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
	public void fillForm(String text, String field, WebDriver driver){
		WebElement input = driver.findElement(By.id(field));
		new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(input)).clear();
		input.sendKeys(text);
	}
	public void accessTab(String name) throws InterruptedException {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
//		System.out.println(driver.getTitle());
		if(!driver.getTitle().equals("Home")){
//			System.out.println("not yet");
			Thread.sleep(2000);
//			System.out.println(driver.getCurrentUrl());
			WebElement tab = (new WebDriverWait(driver, 30))
					.until(ExpectedConditions.presenceOfElementLocated(By.id(name)));
			jse.executeScript("arguments[0].click()", tab);
		}
		else{
//			System.out.println("Ready");
//			System.out.println(driver.getCurrentUrl());

			WebElement tab = (new WebDriverWait(driver, 30))
				.until(ExpectedConditions.presenceOfElementLocated(By.id(name)));
//				driver.findElement(By.id(name));
			jse.executeScript("arguments[0].click()", tab);
		}
	}
	public void setupNote(WebDriver driver) throws InterruptedException {
//		 return noteService.createNote(new Note(null, "a", "b", userId));
//		loginMethod();
		accessTab("nav-notes-tab");
		WebElement newNoteButton = driver.findElement(By.id("create-note"));
		new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(newNoteButton)).click();
		fillForm("To-Do List", "note-title",driver);
		fillForm("1. Walk Dog. 2.Study Java", "note-description", driver);
		WebElement submitButton = driver.findElement(By.id("save-changes"));
		submitButton.click();
	}
//	public String setupCredential(){}
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
		List<WebElement> rows = driver.findElements(By.tagName("tr"));
		Thread.sleep(5000);
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
//		Thread.sleep(2000);
	}
	public void checkNumber(String type) throws InterruptedException {
		List<WebElement> buttons = driver.findElements(By.id(type));
		Thread.sleep(5000);
		if(buttons.size() <1){
			setupNote(driver);
			returnHome(driver);
		}
	}
//	@Test
//	public void getLoginPage() {
//		driver.get("http://localhost:" + this.port + "/login");
//		Assertions.assertEquals("Login", driver.getTitle());
//	}
//
//	@Test
//	public void homePageAccessBlocked() {
//		driver.get("http://localhost:" + this.port + "/home");
//		//should redirect to login page
//		Assertions.assertEquals("Login", driver.getTitle());
//	}
//
//	@Test
//	public void registerUser(){
//		//signup as a new user
//		registerMethod();
//		//log in as previously registered user
//		loginMethod();
//		//homepage is accessible
//		driver.get("http://localhost:" + this.port + "/home");
//		Assertions.assertEquals("Home", driver.getTitle());
//		//once logging out, homepage is no longer accessible
//		WebElement logoutButton = driver.findElement(By.id("logout-button"));
//		logoutButton.click();
//		driver.get("http://localhost:" + this.port + "/home");
//		Assertions.assertEquals("Login", driver.getTitle());
//	}

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
//		setupNote(driver);
//		returnHome(driver);
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
			WebElement createCredentialButton = driver.findElement(By.id("create-credential"));
			new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(createCredentialButton)).click();
			fillForm("www.msn.com/", "credential-url",driver);
			fillForm("jjabrams", "credential-username", driver);
			fillForm("j000008?!", "credential-password", driver);
			WebElement submitButton = driver.findElement(By.id("save-credential"));
			submitButton.click();
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
		WebElement editCredentialButton = driver.findElement(By.id("edit-credential"));
		new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(editCredentialButton)).click();
		String id = editCredentialButton.getAttribute("value");
		fillForm("email", "credential-url", driver);
		fillForm("**", "credential-username", driver);
		fillForm("__", "credential-password", driver);
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
