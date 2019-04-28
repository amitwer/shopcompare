package shopcompare.endtoend;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shopcompare.datacontainers.Price;
import shopcompare.endtoend.businessflows.SearchProductsFlows;
import shopcompare.endtoend.pageobjects.TableWithPricesPage;
import shopcompare.services.SuperGetApi;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@Tags({@Tag("Long"), @Tag("All")})
public class BasicSanityTest {

    @LocalServerPort
    private
    int port;
    @Autowired
    private
    SearchProductsFlows searchProductsFlows;
    @MockBean
    private SuperGetApi superGetApi;
    private ChromeDriver driver;


    @BeforeAll
    static void setupBrowser() {
        WebDriverManager.chromedriver().version("73.0.3683.68").setup();
    }

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        when(superGetApi.getPrices(anyString(), any())).thenReturn(createMockPrice());
    }

    private ArrayList<Price> createMockPrice() {
        Price price = new Price();
        price.setStoreName("Dummy store");
        price.setStoreProductPrice("1.00");
        price.setProductBarcode("dummy barcode");
        price.setStoreId("dummy store id");

        return Lists.list(price);
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void testFullSearchFlow() {
        TableWithPricesPage tableWithPricesPage = searchProductsFlows.searchForProduct(driver, "במבה", port);
        Assertions.assertThat(tableWithPricesPage.isInPage()).as("In Final price results page").isTrue();

    }

}
