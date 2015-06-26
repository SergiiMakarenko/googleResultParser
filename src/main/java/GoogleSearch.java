

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author Sergii Makarenko
 */

public class GoogleSearch {
    private final String search;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input text to search");
        GoogleSearch search = new GoogleSearch(reader.readLine());
        search.doSearch();
    }

    public GoogleSearch(String search){
        this.search=search;
    }

    public void doSearch() {
        WebDriver driver = new FirefoxDriver();
        driver.get("https://www.google.com.ua/?gws_rd=ssl");
        WebElement searchField = driver.findElement(By.xpath(".//*[@id='lst-ib']"));
        searchField.sendKeys(this.search);

        searchField.submit();
                (new WebDriverWait(driver, 5)).until(new ExpectedCondition<Boolean>() {
                    @Override
                    public Boolean apply(WebDriver webDriver) {
                        return webDriver.getTitle().startsWith(search);
                    }
                });
        final String titleFirst = driver.getTitle();

        List<WebElement> resultLinks = driver.findElements(By.xpath(".//div/h3/a"));

        for(int i=0;i<resultLinks.size();i++){
            List<WebElement> resultLinks2 = driver.findElements(By.xpath(".//div/h3/a"));
            resultLinks2.get(i).click();
            (new WebDriverWait(driver, 5)).until(new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver webDriver) {
                    return !webDriver.getTitle().equals(titleFirst);
                }
            });
            System.out.println( driver.getTitle());
            driver.get("https://www.google.com.ua/?gws_rd=ssl#q=" + search);
            (new WebDriverWait(driver, 5)).until(new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver webDriver) {
                    return webDriver.getTitle().equals(titleFirst);
                }
            });
        }
    }
}
