package shopcompare.endtoend.businessflows;

import org.openqa.selenium.WebDriver;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.stereotype.Service;
import shopcompare.endtoend.pageobjects.LoginPage;
import shopcompare.endtoend.pageobjects.SearchPage;

@Service
public class SearchProductsFlows {


    public void searchForProduct(WebDriver driver,String productName,int port){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open(port);
        loginPage.login("amit","wertheimer");
        SearchPage searchPage = new SearchPage(driver);
        searchPage.searchProductName(productName);

    }
}
