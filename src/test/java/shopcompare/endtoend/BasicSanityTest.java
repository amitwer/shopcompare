package shopcompare.endtoend;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shopcompare.endtoend.businessflows.SearchProductsFlows;
import shopcompare.endtoend.pageobjects.ProductSearchResultPage;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@Tags({@Tag("Long"),@Tag("All")})
public class BasicSanityTest {

    private ChromeDriver driver;
    @LocalServerPort
    int port;
    @Autowired
    SearchProductsFlows searchProductsFlows;

    @BeforeAll
    static void setupBrowser() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
    }

    @AfterEach
    void teardown(){
        driver.quit();
    }

    @Test
    void testFullSearchFlow() {
        ProductSearchResultPage searchResultPage = searchProductsFlows.searchForProduct(driver, "במבה", port);
        Assertions.assertThat(searchResultPage.isInPage()).as("In product search results page").isTrue();

    }

}
