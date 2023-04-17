import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        // Fájl beolvasás előkészítése
        File links = new File("./src/novenyvedolinkek.txt");
        // Véletlenszerű ideig történő várakozás előkészítése
        List<Integer> wait = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        Random r = new Random();

        try {
            // Scanner és WebScraper inicializálása
            WebScraper webScraper;
            try (Scanner sc = new Scanner(links)) {
                webScraper = new WebScraper().initialize();

                int i = 495;
                // A következő 20 URL beolvasása a fájlból (20 a cloudflare miatt)
                while (sc.hasNextLine() && i < 510) {
                    String url = sc.nextLine();
                    // StringBuilder létrehozása az egyszerűbb fájlba írás miatt
                    StringBuilder sb = new StringBuilder();
                    // A megnyitott URL HTML forráskódjának kinyerése
                    webScraper.getPageHTML(url);
                    // Egy szer tulajdonságainak felépítése utility metódusokkal
                    sb.append("Név: ").append(webScraper.fetchSingleProductInfo(XPathType.PRODUCTNAME));
                    sb.append("Rendeltetés: ").append(webScraper.fetchSingleProductInfo(XPathType.DESIGNATION));
                    sb.append("Hatóanyag összetétel: ").append(webScraper.fetchSingleProductInfo(XPathType.INGREDIENT));
                    sb.append("Felhasználásra vonatkozó előírások:\n");
                    sb = webScraper.fetchAllProductInfo(XPathType.INSTRUCTIONS, sb, false);
                    sb.append("Engedélyokirat:\n");
                    sb = webScraper.fetchAllProductInfo(XPathType.LICENSE, sb, false);
                    sb.append("Altalános adatok:\n");
                    sb = webScraper.fetchAllProductInfo(XPathType.GENERALINFO, sb, false);
                    sb.append("Agrár-környezet gazdálkodásban engedélyezve:\n");
                    sb = webScraper.fetchAllProductInfo(XPathType.ALLOWEDINAGRICULTURE, sb, true);
                    sb.append("Veszélyességre, biztonságra vonatkozó előírások:\n");
                    sb = webScraper.fetchAllProductInfo(XPathType.DANGERANDSAFETY, sb, true);
                    // A teljes String kiírása .txt fájlba (1 fájl / 1 szer)
                    Files.write(Path.of(MessageFormat.format("./src/data/{0}.txt", i)), sb.toString().getBytes());

                    i++;
                    // Véletlenszerű várakozás a cloudflare érzékelés finomítása érdekében
                    TimeUnit.SECONDS.sleep(5 + wait.get(r.nextInt(7)));
                }
            }
// Böngésző ablak bezárása, amint a program teljesen végzett
            webScraper.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
