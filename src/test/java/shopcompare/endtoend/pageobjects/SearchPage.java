package shopcompare.endtoend.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

public class SearchPage extends BasePage {
    @FindBy(how = How.ID, id = "product-name")
    private WebElement productSearch;
    @FindBy(how = How.ID, id = "city")
    private WebElement city;
    @FindBy(how = How.ID, id = "searchProductBtn")
    private WebElement searchProductBtn;

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public void searchProductNameAndCity(String productName, String city) {
        productSearch.sendKeys(productName);
        new Select(this.city).selectByVisibleText(city);
        searchProductBtn.click();


    }
}
