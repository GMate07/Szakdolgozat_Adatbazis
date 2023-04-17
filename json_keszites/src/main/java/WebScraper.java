import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class WebScraper {
    WebDriver driver;
    /**
     * Átalakítja a megadott Stringet UTF-8 kódolású Stringre
     * @param str Az átalakítani kívánt String
     * @return Az átalakított String.
     * **/
    private String convertStringToUtf8(String str) {
        return new String(str.getBytes(StandardCharsets.UTF_8));
    }

    public WebScraper() {}
    /**
     * A WebScraper példány előkészítése
     * @return Az aktuális példány
     * **/
    public WebScraper initialize() {
        // Chromedriver.exe elérési útjának beállítása és használatának előkészítése
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\gazdi\\Desktop\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized", "--disable-blink-features=AutomationControlled");

        this.driver = new ChromeDriver(options);

        return this;
    }
    /**
     * Az aktuális oldal HTML forráskódjának betöltése a driverbe
     * @param url A weboldal URL-je String típusként
     */
    public void getPageHTML(String url) {
        driver.get(url);

        driver.getPageSource();
    }
    /**
     * Legfeljebb egy értékkel rendelkező blokk szövegének kinyerése
     * @param xPathType Az adott HTML blokk azonosítása
     * @return A keresett szöveg vagy "NULL", ha a keresett elem nem található az oldalon
     */
    public String fetchSingleProductInfo(XPathType xPathType) {
        String info;

        try {
            // A HTML tag megkeresése és szövegének UTF-8 kódolásúvá alakítása
            info = convertStringToUtf8(driver.findElement(By.xpath(xPathType.path)).getText()) + '\n';
        } catch (NoSuchElementException e) {
            // "NULL" érték visszaküldése, amennyiben a keresett elem valamilyen oknál fogva nem lett megadva az oldalon

            info = "NULL";
        }

        return info;
    }
    /**
     * A böngésző ablak bezárása
     */
    /**
     * Akár több értékkel is rendelkező HTML blokk teljes feldolgozása
     * @param xPathType Az adott HTML blokk azonosítása
     * @param sb StringBuilder példány a már meglévő adatok gyarapításához
     * @param containsDescription Boolean érték attól függően, hogy az adott HTML blokk tartalmazhat-e
     *                            class="description" attribútumú HTML taget
     * @return A StringBuilder példány, amelyet egy növényvédő szer leírásához használunk
     */
    public StringBuilder fetchAllProductInfo(XPathType xPathType, StringBuilder sb, Boolean containsDescription) {
        try {
            // A lehetséges értékeket tartalmazó szülő objektum megkeresése
            WebElement contentContainer = driver.findElement(By.xpath(xPathType.path));
            // A szülőhöz tartozó minden kulcs-érték pár (kulcs = label, érték = value és/vagy description) tárolása
            List<WebElement> children = contentContainer.findElements(By.cssSelector("span.label,span.description,span.value"));
            // Ha a szülő pontosan 1 értéket tartalmaz, akkor annak visszaadása a fetchSingleProductInfo metódushoz hasonlóan
            if (children.size() == 1) {
                return sb.append(convertStringToUtf8(children.get(0).getAttribute("textContent"))).append('\n');
            }
            // Több érték esetén minden elem feldolgozása
            for (WebElement child : children) {
                /*
                    Ha kulcsról van szó, akkor sortörés nélkül hagyjuk a szöveges értéket
                    Minden esetben eltávolítjuk a HTML forráskódban a szöveg elején és végén található whitespaceket trim() metódussal
                    Minden esetben eltávolítjuk a HTML forráskódban található felesleges sortöréseket replaceAll() metódussal
                 */
                if (child.getAttribute("class").equals("label")) {
                    sb.append(convertStringToUtf8(child.getAttribute("textContent")).trim().replaceAll("\n","")).append(": ");
                } else {
                    sb.append(convertStringToUtf8(child.getAttribute("textContent")).trim().replaceAll("\n",""));
                    // A description és a value szövegek közé egy szóköz, hogy ne folyjon egybe a szöveg
                    if (child.getAttribute("class").equals("description")) {
                        sb.append(' ');
                    }
                    // Az érték után (tehát a következő lehetséges kulcs előtt) mindig egy sortörés beszúrása
                    if (!containsDescription || child.getAttribute("class").equals("value")) {
                        sb.append('\n');
                    }
                }
            }
        } catch (NoSuchElementException e) {
            return sb.append("NULL");
        }

        return sb;
    }

    public void destroy() {
        this.driver.quit();
    }
}
