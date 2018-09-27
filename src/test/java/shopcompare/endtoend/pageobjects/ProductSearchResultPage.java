package shopcompare.endtoend.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductSearchResultPage extends BasePage {
    public ProductSearchResultPage(WebDriver driver) {
        super(driver);
    }
    public boolean isInPage(){
        return driver.findElements(By.id("product_search_results")).size()>0;
    }
}
