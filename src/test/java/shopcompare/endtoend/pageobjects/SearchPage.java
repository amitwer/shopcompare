package shopcompare.endtoend.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SearchPage extends BasePage{
    public SearchPage(WebDriver driver) {
        super(driver);
    }
    @FindBy(how = How.ID,id = "product-name")
    WebElement productSearch;
    @FindBy(how = How.ID,id = "searchProductBtn")
    WebElement searchProductBtn;



    public void searchProductName(String productName) {
        productSearch.sendKeys(productName);
        searchProductBtn.click();



    }
}
