package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		// after sign up successfully the page will redirect to login, so there is no such element and the message
//		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		// password must meet the complex requirement
		doMockSignUp("Redirection","Test","TestRedirection","123QWEasd!@#");
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}



	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123QWEasd!@#");
		doLogIn("UT", "123QWEasd!@#");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123QWEasd!@#");
		doLogIn("LFT", "123QWEasd!@#");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

	/**
	 * Check if allow duplicate usernames
	 */
	@Test
	public void testDuplicateUsername() {
		// Create a test account
		doMockSignUp("Redirection","Test","TestDuplicateUsername","123QWEasd!@#");

		WebElement linkBack2SignUp = driver.findElement(By.id("back-to-signup"));
		linkBack2SignUp.click();
		// Create the account using the same username
		doMockSignUp("Redirection","Test","TestDuplicateUsername","123QWEasd!@#");

		// Check if we have been redirected to the log in page.
		Assertions.assertTrue(driver.findElement(By.id("error-msg")).getText().contains("Username already exists!"));
	}

//	1. Write Tests for User Signup, Login, and Unauthorized Access Restrictions.
//	Write a test that verifies that an unauthorized user can only access the login and signup pages.
	@Test
	@Order(1)
	public void testUnauthenticated(){

		// Try to access a home URL and will redirect to login page
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertTrue(driver.getCurrentUrl().contains("/login"));

		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertTrue(driver.getCurrentUrl().contains("/login"));

		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertTrue(driver.getCurrentUrl().contains("/signup"));

	}
//	Write a test that signs up a new user, logs in, verifies that the home page is accessible, logs out, and verifies that the home page is no longer accessible.
	@Test
	@Order(2)
	public void testSignUpAndLoginAndLogout(){
		// signup
		doMockSignUp("Redirection","Test","Test","123QWEasd!@#");

		WebElement linkBack2SignUp = driver.findElement(By.id("back-to-signup"));
		linkBack2SignUp.click();

		// login then access home
		doLogIn("Test", "123QWEasd!@#");

		// Check if home page is still accessible, should accessible
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertTrue(driver.getCurrentUrl().contains("/home"));

		// logout and then access home
		WebElement logoutButton = driver.findElement(By.id("logout"));
		logoutButton.click();

		// Check if home page is still accessible should not be accessible
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertFalse(driver.getCurrentUrl().contains("/home"));

	}

//	2. Write Tests for Note Creation, Viewing, Editing, and Deletion.

	/**
	 * mock add, modify and delete note
	 * @param action 1. add note, 2. modify note, 3. delete note
	 * @param title note title
	 * @param description note content
	 */
	private void mockNoteAction(int action, String title, String description){

		WebElement noteTab = driver.findElement(By.id("nav-notes-tab"));
		noteTab.click();
//
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		// add note
		if(action == 1){
			WebElement addNote = driver.findElement(By.id("add-note"));

			// error is here show org.openqa.selenium.ElementNotInteractableException: element not interactable
			addNote.click();
		}
		if(action == 2){
			WebElement editNote = driver.findElement(By.id("edit-note"));
			editNote.click();
		}

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.name("noteTitle")));
		WebElement inputTitle = driver.findElement(By.name("noteTitle"));
		inputTitle.click();
		inputTitle.sendKeys(title);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.name("noteDescription")));
		WebElement inputDescription = driver.findElement(By.name("noteDescription"));
		inputDescription.click();
		inputDescription.sendKeys(description);


		// save note
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-save-btn")));

		WebElement saveNoteBtn = driver.findElement(By.id("note-save-btn"));
		saveNoteBtn.click();
	}
//	Write a test that creates a note, and verifies it is displayed.
	@Test
	@Order(3)
	public void testNoteCreat(){
		doMockSignUp("Redirection","Test","Test","123QWEasd!@#");

		doLogIn("Test", "123QWEasd!@#");

		// add note
		String inputTitle = "note title";
		String inputDesc = "note content";

		mockNoteAction(1,inputTitle ,inputDesc );

		String title = driver.findElement(By.id("note-title-tb")).getText();
		String description = driver.findElement(By.id("note-description-tb")).getText();
		Assertions.assertTrue(title.equals(inputTitle) && description.equals(inputDesc));



	}
//	Write a test that edits an existing note and verifies that the changes are displayed.
//	Write a test that deletes a note and verifies that the note is no longer displayed.

}
