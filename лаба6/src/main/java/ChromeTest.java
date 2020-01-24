import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class ChromeTest {
    WebDriver driver;

    @BeforeTest
    public void beforeTest() {
        String webDriverKey = "webdriver.chrome.driver";
        String webDriverValue = System.getProperty("user.dir") +
                "/target/tmp_webdrivers/chromedriver-mac-32bit";
        System.setProperty(webDriverKey, webDriverValue);
        driver = new ChromeDriver();
        driver.get("http://www.google.com");
    }

//todo: test

    @AfterTest
    public void afterTest() {
        driver.quit();
    }
}
