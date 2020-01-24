import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class task7 {

    private static WebDriver driver;
    private static WebDriverWait wait;

    public static void main (String[] args)  {
        SetUp();
    }

    public static void SetUp () {

        //Для браузера Google Chrome
        System.setProperty("webdriver.chrome.driver", "E:/chromedriver.exe");
        driver = new ChromeDriver();

        // Для браузера Firefox
        //System.setProperty("webdriver.gecko.driver", "D:\\! Мифи институт\\GECKodrive\\geckodriver.exe");
        //driver = new FirefoxDriver();

        final String previousURL = "https://hh.ru/";
        final int limit = 4;
        String current="";
        wait = new WebDriverWait(driver,10);
        driver.manage().window().maximize();
        driver.get(previousURL);
        if(driver.findElement(By.className("supernova-dashboard")).isDisplayed()){
            driver.findElement(By.name("text")).sendKeys("Java" + Keys.ENTER);
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            ExpectedCondition<Boolean> e = new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    return (!d.getCurrentUrl().equals(previousURL));
                }
            };
            wait.until(e);
            current = driver.getCurrentUrl();
            driver.get(current);
        }

        List<WebElement> elements = driver.findElements(By.xpath("*//div[@class='vacancy-serp']/div/div/div/div[@class='vacancy-serp-item__compensation']"));
        ArrayList<Float> zpStr = new ArrayList<>(); //Создание списка
        int page = 1;
        do{
            List<Float> finalZpStr = zpStr;
            elements.forEach(x -> finalZpStr.add(getCost(x.getText())));
            driver.get(current+"&page="+page);
            elements = driver.findElements(By.xpath("*//div[@class='vacancy-serp']/div/div/div/div[@class='vacancy-serp-item__compensation']"));
            page++;

        }while (page<limit);

        float mediane = 0;
        zpStr = sortArray(zpStr);
        System.out.println(zpStr);
        int id_med = (int)((zpStr.size()-1)/2);
        if(zpStr.size()%2 == 0){
            mediane = (zpStr.get(id_med) + zpStr.get(id_med+1))/2;
        }
        System.out.println("Average salary of java programmer: " + mediane);

    }

    private static ArrayList<Float> sortArray(ArrayList<Float> arr){
        for(int i = 0; i < arr.size() - 1; i++)
            for (int j = arr.size() - 1; j > i; j--){
                if(arr.get(i) > arr.get(j)){
                    float var = arr.get(j);
                    arr.set(j, arr.get(i));
                    arr.set(i, var);
                }
            }
        return arr;
    }

    private static float getCost (String wordCost){
        float cost, mult = 1;

        if(wordCost.indexOf("USD") >= 0){
            mult = (float)63.77;
        }
        if(wordCost.indexOf("EUR") >= 0){
            mult = (float)70.71;
        }
        if (wordCost.indexOf('-') >= 0){

            float minCost = Float.parseFloat((wordCost.substring(0, wordCost.indexOf('-'))).replaceAll("[^\\d+]", ""));
            float maxCost = Float.parseFloat((wordCost.substring(wordCost.indexOf('-'), wordCost.length())).replaceAll("[^\\d+]", ""));
            cost = (minCost + maxCost) / 2;
        }else {
            cost = Integer.parseInt(wordCost.replaceAll("[^\\d+]", ""));
        }

        return cost * mult;
    }
}


