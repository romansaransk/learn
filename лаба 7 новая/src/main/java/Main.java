import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.internal.ProfilesIni;

import java.util.*;

public class Main {
    static Map<String, Float> rate = new HashMap<String,Float>();

    public  static WebDriver seleniumChrome() {
        System.setProperty("webdriver.chrome.driver", "C:/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        return driver;
    }

    private static void prnt(Map<String, Float> result){
        float total = 0;
        float mediane = 0;

        ArrayList<Float> costs = new ArrayList<>();

        for(Map.Entry<String, Float> eac : result.entrySet()){
            System.out.println("Cost: " + eac.getValue() + " рублей. link: " + eac.getKey());
            costs.add(eac.getValue());
        }
        costs = sortArray(costs);
        System.out.println(costs);
        int id_med = (int)((costs.size()-1)/2);
        if(costs.size()%2 == 0){
            mediane = (costs.get(id_med) + costs.get(id_med+1))/2;
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

    public static void main(String[] args) throws InterruptedException {

        WebDriver driver = seleniumChrome();

        driver.get("https://hh.ru/search/vacancy?only_with_salary=true&clusters=true&area=1&search_field=name&no_magic=true&enable_snippets=true&salary=&st=searchVacancy&text=java");

        Map<String, Float> result = new HashMap<String, Float>();
        do {
            List<WebElement> elementName = driver.findElements(By.className("vacancy-serp-item"));
            System.out.println("size:" + elementName.size());
            for (WebElement el : elementName) {
                String price = null;
                try {
                    price = el.findElement(By.className("vacancy-serp-item__compensation")).getText();
                }
                catch(Exception e) {
                    continue;
                }
                System.out.println("prc:" + price);
                result.put(el.findElement(By.cssSelector(".bloko-link.HH-LinkModifier")).getAttribute("href"), getCost(price));
                System.out.println("result:" + result.size());
            }

            WebElement element = null;

            try {
                element = driver.findElement(By.className("HH-Pager-Controls-Next"));
            }
            catch (Exception e) {
                System.out.println("end of pars");
                break;
            }
            int elementPosition = element.getLocation().getY();
            String js = String.format("window.scroll(0, %s)", elementPosition);
            ((JavascriptExecutor)driver).executeScript(js);
            element.click();

        } while (isElementExists(driver));

        prnt(result);
    }

    public static Boolean isElementExists(WebDriver driver)
    {
        try {
            driver.findElement(By.className("HH-Pager-Controls-Next"));
            return true;
        }
        catch (NoSuchElementException e) {
            return false;
        }
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

    private static float toRUB (String wordCost) {
        StringBuffer rateWord = new StringBuffer();

        for (int i = wordCost.lastIndexOf(" ") + 1; i < wordCost.length(); i++)
            rateWord.append(wordCost.charAt(i));
        if (rateWord.toString().equals("СЂСѓР±."))
            return 1;
        return  getRate(rateWord.toString());
    }

    private static float getRate(String currency){

//        if(selen.rate.get(currency) != null){
//            return selen.rate.get(currency);
//        }
//
//        FirefoxOptions options = new FirefoxOptions();
//        options.addArguments("--headless");
//        WebDriver driver = selen.seleniumFirefox();
//
//        driver.get("https://bankiros.ru/convert/" + currency.toLowerCase() + "-rub/1");
//        WebElement sub = driver.findElement(By.id("cbr_second"));
//        selen.rate.put(currency, Float.parseFloat(sub.getAttribute("value")));
//        driver.quit();

//        return selen.rate.get(currency);
        return (float) 65;
    }
}