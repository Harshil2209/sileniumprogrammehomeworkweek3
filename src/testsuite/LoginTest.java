package testsuite;

import browserfactory.BaseTest;
import com.google.common.base.Verify;
import io.opentelemetry.sdk.trace.samplers.SamplingResult;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Result;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LoginTest extends BaseTest {
    static String baseUrl = " https://demo.nopcommerce.com/";

    @Before
    public void setUp() {
        openBrowser(baseUrl);
    }

    @Test
    public void userShouldNavigateToLoginPageSuceessfully() {
        //Find the login link and click on login link
        WebElement loginLink = driver.findElement(By.linkText("Log in"));
        loginLink.click();
        String expectedText = "Welcome, Please Sign In!";

        //Find actual text element and get text from element
        WebElement actualTextElement = driver.findElement(By.xpath("//h1"));
        String actualText = actualTextElement.getText();
        //Verufy Expected and Actual Text
        Assert.assertEquals(expectedText, actualText);

    }

    @Test
    public void userShouldLoginSuccessfullyWithValidCredentials() {
        userShouldNavigateToLoginPageSuceessfully();

        driver.findElement(By.name("Email")).sendKeys("abcd@gmail.com"); // find element for Email field and enter valid login-id
        driver.findElement(By.name("Password")).sendKeys("abcd123"); // find element for Password field and enter valid password
        driver.findElement(By.xpath("//button[@class='button-1 login-button']")).click(); // find element for login button and click
        String actualValue = driver.findElement(By.xpath("//a[text() = 'Log out']")).getText(); // get the text from page to verify the re-direction
        String expectedValue = "Log out"; // expected text
        Assert.assertEquals(expectedValue, actualValue); // verify expected and actual text
    }

    @Test
    public void verifyTheErrorMessage() {
        userShouldNavigateToLoginPageSuceessfully();


        driver.findElement(By.id("Email")).sendKeys("abc123@gmail.com"); // find element for Email field and enter valid login-id
        driver.findElement(By.name("Password")).sendKeys("1234"); // find element for Password field and enter in-valid password
        driver.findElement(By.xpath("//button[@class = 'button-1 login-button']")).click(); // find element for login button and click

        String expectedErrorMessage = "Login was unsuccessful. Please correct the errors and try again.\n" +
                "No customer account found"; // expected error message

        //finding actual error message
        String actualErrorMessage = driver.findElement(By.xpath("//div[@class = 'message-error validation-summary-errors']")).getText();

        Assert.assertEquals("Error Message not matched", expectedErrorMessage, actualErrorMessage); // verify expected and actual text
    }

    @After
    public void tearDown() {
        closeBrowser(); // calling method from BaseTest class to close the browser
    }


}
