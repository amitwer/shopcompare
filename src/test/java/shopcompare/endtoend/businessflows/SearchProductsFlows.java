package shopcompare.endtoend.businessflows;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;
import shopcompare.Reporter;
import shopcompare.endtoend.pageobjects.LoginPage;
import shopcompare.endtoend.pageobjects.ProductSearchResultPage;
import shopcompare.endtoend.pageobjects.SearchPage;

@Service
public class SearchProductsFlows {


    @Step("Search for {productName}")
    public ProductSearchResultPage searchForProduct(WebDriver driver, String productName, int port){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open(port);
        Reporter.addScreenShot(driver,"Login page");
        loginPage.login("amit","wertheimer");
        Reporter.addScreenShot(driver,"After login");
        SearchPage searchPage = new SearchPage(driver);
        searchPage.searchProductNameAndCity(productName,"ירושלים" );
        driver.switchTo().frame("products");
        Reporter.addScreenShot(driver, "Search results");
        return new ProductSearchResultPage(driver);


    }
}
