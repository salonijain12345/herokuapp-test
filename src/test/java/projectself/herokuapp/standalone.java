package projectself.herokuapp;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class standalone {

	public static void main(String[] args) {
		System.out.println("hello");
		WebDriverManager.chromedriver().setup();
		WebDriver driver =(WebDriver) new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
        driver.get("https://the-internet.herokuapp.com/");
	}

}
