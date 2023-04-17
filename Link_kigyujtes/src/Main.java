import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        Random r = new Random();
        WebDriver driver = null;
        StringBuilder sb = new StringBuilder();

        try {
            for (int i = 1; i <= 145; i++) {
                driver = new ChromeDriver();
                driver.get(MessageFormat.format("https://agromedium.com/hu-hu/novenyvedo-szerek/?pn={0}", i));

                driver.getPageSource();
                TimeUnit.MILLISECONDS.sleep(100);
                List<WebElement> productsPerPage = driver.findElements(By.cssSelector("[href*=\"https://agromedium.com/hu-hu/novenyvedo-szer/\"]"));

                for (WebElement productLink : productsPerPage) {
                    sb.append(productLink.getAttribute("href")).append('\n');
                    System.out.println(productLink.getAttribute("href"));
                }

                driver.quit();
                TimeUnit.MILLISECONDS.sleep(3000 + r.nextInt(3000));
            }

            Files.write(Path.of("./src/novenyvedolinkek.txt"), sb.toString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}