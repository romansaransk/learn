import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {
    static WebDriver driver = null;

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "E:/chromedriver.exe");
        driver = new ChromeDriver();
        try{
            driver.get("https://ya.ru");
            driver.findElement(By.name("text")).sendKeys("Задание 6 - выполнено!" + Keys.ENTER);
            //driver.findElement(By.name("q")).submit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}