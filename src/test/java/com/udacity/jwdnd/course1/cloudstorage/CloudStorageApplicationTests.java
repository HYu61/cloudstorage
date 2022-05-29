package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private void doMockSignUp(String firstName, String lastName, String userName, String password) {
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
    private void doLogIn(String userName, String password) {
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
     * <p>
     * If this test is failing, please ensure that you are handling redirecting users
     * back to the login page after a succesful sign up.
     * Read more about the requirement in the rubric:
     * https://review.udacity.com/#!/rubrics/2724/view
     */
    @Test
    public void testRedirection() {
        // Create a test account
        // password must meet the complex requirement
        doMockSignUp("Redirection", "Test", "TestRedirection", "123QWEasd!@#");
        // Check if we have been redirected to the log in page.
        Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
    }


    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling bad URLs
     * gracefully, for example with a custom error page.
     * <p>
     * Read more about custom error pages at:
     * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
     */
    @Test
    public void testBadUrl() {
        // Create a test account
        doMockSignUp("URL", "Test", "UT", "123QWEasd!@#");
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
     * <p>
     * If this test is failing, please ensure that you are handling uploading large files (>1MB),
     * gracefully in your code.
     * <p>
     * Read more about file size limits here:
     * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
     */
    @Test
    public void testLargeUpload() {
        // Create a test account
        doMockSignUp("Large File", "Test", "LFT", "123QWEasd!@#");
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
        doMockSignUp("Redirection", "Test", "TestDuplicateUsername", "123QWEasd!@#");

        WebElement linkBack2SignUp = driver.findElement(By.id("back-to-signup"));
        linkBack2SignUp.click();
        // Create the account using the same username
        doMockSignUp("Redirection", "Test", "TestDuplicateUsername", "123QWEasd!@#");

        // Check if we have been redirected to the log in page.
        Assertions.assertTrue(driver.findElement(By.id("error-msg")).getText().contains("Username already exists!"));
    }

    /**
     * Test User Signup, Login, and Unauthorized Access Restrictions.
     */
    //	1. Write Tests for User Signup, Login, and Unauthorized Access Restrictions.
//	Write a test that verifies that an unauthorized user can only access the login and signup pages.
    @Test
    @Order(1)
    public void testUnauthenticated() {

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
    public void testSignUpAndLoginAndLogout() {
        // signup
        doMockSignUp("Redirection", "Test", "Test", "123QWEasd!@#");

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

    /**
     *  Test note part
     */

//	2. Write Tests for Note Creation, Viewing, Editing, and Deletion.

    /**
     * mock add, modify and delete note
     *
     * @param action      1. add note, 2. modify note, 3. delete note
     * @param title       note title
     * @param description note content
     */
    private void mockNoteAction(int action, String title, String description) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        WebElement noteTab = driver.findElement(By.id("nav-notes-tab"));
        noteTab.click();


        // add note
        if (action == 1) {
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-note")));
            WebElement addNote = driver.findElement(By.id("add-note"));
            addNote.click();
        }

        // edit note
        if (action == 2) {
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-note")));
            WebElement editNote = driver.findElement(By.id("edit-note"));
            editNote.click();
        }

        // delete note
        if (action == 3) {
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-note")));
            WebElement deleteNote = driver.findElement(By.id("delete-note"));
            deleteNote.click();
            return;
        }

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        WebElement inputTitle = driver.findElement(By.id("note-title"));
        inputTitle.click();
        inputTitle.clear();
        inputTitle.sendKeys(title);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
        WebElement inputDescription = driver.findElement(By.id("note-description"));
        inputDescription.click();
        inputDescription.clear();
        inputDescription.sendKeys(description);


        // save note
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-save-btn")));

        WebElement saveNoteBtn = driver.findElement(By.id("note-save-btn"));
        saveNoteBtn.click();
    }

    /**
     * Get the note title, description and message after action at the home page
     *
     * @return the values in a map
     */
    private Map<String, String> getNoteTitleAndDescFromHome() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        List<WebElement> tbody = driver.findElements(By.name("note-title-tb"));

        // get message for actions
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-msg")));
        String msg = driver.findElement(By.id("note-msg")).getText();
        Map<String, String> re = new HashMap<>();
        re.put("msg", msg);

        if (tbody.isEmpty()) {
            return re;
        }


        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.name("note-title-tb")));
        String title = driver.findElements(By.name("note-title-tb")).get(0).getText();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.name("note-description-tb")));
        String description = driver.findElements(By.name("note-description-tb")).get(0).getText();

        re.put("title", title);
        re.put("desc", description);
        return re;
    }


    //	Write a test that creates a note, and verifies it is displayed.
    @Test
    @Order(3)
    public void testNoteCreat() {
        doMockSignUp("Redirection", "Test", "Test", "123QWEasd!@#");

        doLogIn("Test", "123QWEasd!@#");

        // add note
        String inputTitle = "note title";
        String inputDesc = "note content";

        mockNoteAction(1, inputTitle, inputDesc);

        Map<String, String> noteInfo = getNoteTitleAndDescFromHome();

        // check if displayed
        Assertions.assertTrue(noteInfo.get("title").equals(inputTitle)
                && noteInfo.get("desc").equals(inputDesc)
                && noteInfo.get("msg").contains("is created"));

    }

    //	Write a test that edits an existing note and verifies that the changes are displayed.
    @Test
    @Order(4)
    public void testNoteEdit() {
        doLogIn("Test", "123QWEasd!@#");

        // edit note
        String inputChangedTitle = "note title changed";
        String inputChangedDesc = "note content changed";

        mockNoteAction(2, inputChangedTitle, inputChangedDesc);

        Map<String, String> noteInfo = getNoteTitleAndDescFromHome();

        // check if displayed
        Assertions.assertTrue(noteInfo.get("title").equals(inputChangedTitle)
                && noteInfo.get("desc").equals(inputChangedDesc)
                && noteInfo.get("msg").contains("is edited"));
    }

    //	Write a test that deletes a note and verifies that the note is no longer displayed.
    @Test
    @Order(5)
    public void testDeleteNote() {
        doLogIn("Test", "123QWEasd!@#");

        // delete note
        mockNoteAction(3, null, null);

        List<WebElement> tbody = driver.findElements(By.name("note-title-tb"));

        Map<String, String> noteInfo = getNoteTitleAndDescFromHome();

        // check if displayed
        Assertions.assertTrue(tbody.isEmpty()
                && noteInfo.get("msg").contains("is removed"));
    }

    /**
     * Test credential part
     */
//	3. Write Tests for Credential Creation, Viewing, Editing, and Deletion.

    /**
     * Mock create, modify and remove credential
     *
     * @param action   1.add 2.edit 3.delete
     * @param url
     * @param username
     * @param password
     * @return The original password
     */
    private String mockCredentialAction(int action, String url, String username, String password) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        WebElement credTab = driver.findElement(By.id("nav-credentials-tab"));
        credTab.click();

        // add note
        if (action == 1) {
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-cred")));
            WebElement addCred = driver.findElement(By.id("add-cred"));
            addCred.click();
        }

        // edit note
        if (action == 2) {
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-cred")));
            WebElement editCred = driver.findElement(By.id("edit-cred"));
            editCred.click();
        }

        // delete note
        if (action == 3) {
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-cred")));
            WebElement deleteCred = driver.findElement(By.id("delete-cred"));
            deleteCred.click();
            return null;
        }

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
        WebElement inputUrl = driver.findElement(By.id("credential-url"));
        if (action == 1) {
            inputUrl.click();
            inputUrl.sendKeys(url);
        } else {
            inputUrl.sendKeys(inputUrl.getText());
        }


        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
        WebElement inputUsername = driver.findElement(By.id("credential-username"));
        if (action == 1) {
            inputUsername.click();
            inputUsername.sendKeys(username);
        } else {
            inputUsername.sendKeys(inputUsername.getText());
        }


        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
        WebElement inputPassword = driver.findElement(By.id("credential-password"));

        String originalPassword = inputPassword.getAttribute("value");

        inputPassword.click();
        inputPassword.clear();
        inputPassword.sendKeys(password);


        // save note
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-save-btn")));

        WebElement saveCredBtn = driver.findElement(By.id("credential-save-btn"));
        saveCredBtn.click();
        return originalPassword;
    }

    /**
     * Get the Credential url, username and password after action at the home page
     *
     * @return the values in a map
     */
    private Map<String, String> getCredInfoFromHome() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        List<WebElement> tbody = driver.findElements(By.name("cred-url-tb"));

        // get message for actions
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cred-msg")));
        String msg = driver.findElement(By.id("cred-msg")).getText();
        Map<String, String> re = new HashMap<>();
        re.put("msg", msg);

        if (tbody.isEmpty()) {
            return re;
        }


        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.name("cred-url-tb")));
        String url = driver.findElements(By.name("cred-url-tb")).get(0).getText();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.name("cred-username-tb")));
        String username = driver.findElements(By.name("cred-username-tb")).get(0).getText();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.name("cred-password-tb")));
        String password = driver.findElements(By.name("cred-password-tb")).get(0).getText();

        re.put("url", url);
        re.put("username", username);
        re.put("password", password);
        return re;
    }
//	Write a test that creates a set of credentials, verifies that they are displayed, and verifies that the displayed password is encrypted.

    @Test
    @Order(6)
    public void testCredentialAdd() {
        doLogIn("Test", "123QWEasd!@#");

        String url = "url";
        String username = "username";
        String password = "password";

        // add cred
        mockCredentialAction(1, url, username, password);

        // verify it is displayed
        Map<String, String> credInfo = getCredInfoFromHome();
        Assertions.assertTrue(credInfo.get("msg").contains("is created")
                && credInfo.get("url").equals(url)
                && credInfo.get("username").equals(username)
                // verify the password is encrypted
                && !credInfo.get("password").equals(password));

    }


    // Test if the user add the same username with the same url, will get error message
    @Test
    @Order(7)
    public void testCredentialAddWithDuplicatedUsername() {
        doLogIn("Test", "123QWEasd!@#");

        String originalUrl = "url";
        String originalUsername = "username";
        String password = "newPassword";

        // add cred
        mockCredentialAction(1, originalUrl, originalUsername, password);

        // verify throw duplicate error message displayed
        WebElement errMsg = driver.findElement(By.id("error-message")).findElement(By.tagName("h3"));
        Assertions.assertTrue(errMsg.getText().contains("already exists"));
    }

    //	Write a test that views an existing set of credentials, verifies that the viewable password is unencrypted, edits the credentials, and verifies that the changes are displayed.
    @Test
    @Order(8)
    public void testCredentialEdit() {
        doLogIn("Test", "123QWEasd!@#");

        String originalUrl = "url";
        String originalUsername = "username";
        String originalPassword = "password";
        String newPassword = "newPass";

        // edit cred and get the viewable password
        String viewablePass = mockCredentialAction(2, null, null, newPassword);
        Assertions.assertTrue(viewablePass.equals(originalPassword));


        // verify it is displayed
        Map<String, String> credInfo = getCredInfoFromHome();

        Assertions.assertTrue(credInfo.get("msg").contains("is edited")
                && credInfo.get("url").equals(originalUrl)
                && credInfo.get("username").equals(originalUsername)
                // verify the password is encrypted
                && !credInfo.get("password").equals(viewablePass) && !credInfo.get("password").equals(newPassword));

    }

    //	Write a test that deletes an existing set of credentials and verifies that the credentials are no longer displayed.
    @Test
    @Order(9)
    public void testCredentialDelete() {
        doLogIn("Test", "123QWEasd!@#");

        // delete credential
        mockCredentialAction(3, null, null, null);

        List<WebElement> tbody = driver.findElements(By.name("cred-url-tb"));

        Map<String, String> credInfo = getCredInfoFromHome();

        // check if the credential is displayed
        Assertions.assertTrue(tbody.isEmpty()
                && credInfo.get("msg").contains("is removed"));
    }
}
