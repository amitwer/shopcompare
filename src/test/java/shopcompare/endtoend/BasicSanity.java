package shopcompare.endtoend;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shopcompare.endtoend.businessflows.SearchProductsFlows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BasicSanity {

    private ChromeDriver driver;
    @LocalServerPort
    int port;
    @Autowired
    SearchProductsFlows searchProductsFlows;

    @BeforeAll
    public static void setupBrowser() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
    }

    @AfterEach
    public void teardown(){
        driver.quit();
    }

    @Test
    public void testFullSearchFlow() {
        searchProductsFlows.searchForProduct(driver, "במבה", port);

    }

}
