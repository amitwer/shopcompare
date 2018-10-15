package shopcompare.endtoend.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

public class ProductSearchResultPage extends BasePage {

    @FindBy(how = How.ID, id = "matching_products_list")
    private WebElement matchingProductsList;
    @FindBy(how = How.ID, id = "get_price_btn")
    private WebElement getPricesBtn;

    public ProductSearchResultPage(WebDriver driver) {
        super(driver);
    }
    public boolean isInPage(){
        return driver.findElements(By.id("product_search_results")).size()>0;
    }

    public TableWithPricesPage chooseProductByIndex(int index) {
        new Select(matchingProductsList).selectByIndex(index);
        getPricesBtn.click();
        return new TableWithPricesPage(driver);
    }
}
