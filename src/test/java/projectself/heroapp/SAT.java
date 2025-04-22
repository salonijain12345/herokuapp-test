package projectself.heroapp;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;

public class SAT {

    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.setCapability("se:cdpVersion", "");
        WebDriver driver = new ChromeDriver(options);
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://admin:admin@the-internet.herokuapp.com/");
        
        // A/B Testing
        driver.findElement(By.linkText("A/B Testing")).click();
        driver.navigate().back();

        // Add/Remove Elements
        driver.findElement(By.linkText("Add/Remove Elements")).click();
        driver.findElement(By.xpath("//*[@id=\"content\"]/div/button")).click();
        driver.findElement(By.className("added-manually")).click();
        driver.navigate().back();

        // Basic Auth
        driver.findElement(By.linkText("Basic Auth")).click();
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#content>div>p"))
        );
        String text = driver.findElement(By.cssSelector("div#content>div>p")).getText();
        System.out.print(text);
        Assert.assertEquals(text, "Congratulations! You must have the proper credentials.");
        driver.navigate().back();

        // Broken Images
        driver.findElement(By.linkText("Broken Images")).click();
        List<WebElement> images = driver.findElements(By.tagName("img"));
        List<String> url = new ArrayList<>();
        for (WebElement element : images)
            url.add(element.getAttribute("src"));

        for (String imageurl : url) {
            System.out.println(imageurl);
            System.out.println(RestAssured.given().when().get(imageurl).statusCode());
        }
        driver.navigate().back();

        // Challenging DOM
        driver.findElement(By.linkText("Challenging DOM")).click();
        List<WebElement> rows = driver.findElements(By.xpath("//table[@class='large-10 columns']//tbody/tr"));

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            System.out.println("Row starts with: " + cells.get(0).getText());

            if (cells.get(0).getText().equals("Iuvaret5")) {
                WebElement editBtn = cells.get(cells.size() - 2).findElement(By.tagName("a"));
                editBtn.click();
                System.out.println("Clicked Edit for Iuvaret5");
                driver.navigate().back();

                rows = driver.findElements(By.xpath("//table[@class='large-10 columns']//tbody/tr"));
                row = rows.get(5);
                cells = row.findElements(By.tagName("td"));
                WebElement deleteBtn = cells.get(cells.size() - 1).findElement(By.tagName("a"));
                deleteBtn.click();
                System.out.println("Clicked Delete for Iuvaret5");
                break;
            }
        }
        driver.navigate().back();

        // Checkboxes
        driver.findElement(By.linkText("Checkboxes")).click();
        driver.findElement(By.xpath("(//input[@type='checkbox'])[1]")).click();
        driver.navigate().back();

        // Context Menu
        driver.findElement(By.linkText("Context Menu")).click();
        Actions actions = new Actions(driver);
        actions.contextClick(driver.findElement(By.id("hot-spot"))).perform();
        Alert alert = driver.switchTo().alert();
        System.out.println(alert.getText());
        alert.accept();
        driver.navigate().back();

        // Digest Authentication
        driver.findElement(By.linkText("Digest Authentication")).click();
        System.out.println("Digest Authentication completed");
        driver.navigate().back();

        // Disappearing Elements
        driver.findElement(By.linkText("Disappearing Elements")).click();
        driver.navigate().back();

        // Drag and Drop
        driver.findElement(By.linkText("Drag and Drop")).click();
        driver.navigate().back();

        driver.close();
    }
}
