package shopcompare.endtoend.pageobjects;

import org.apache.commons.collections4.CollectionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TableWithPricesPage extends BasePage {
    public TableWithPricesPage(WebDriver driver) {
        super(driver);
    }

    public boolean isInPage() {
        return CollectionUtils.isNotEmpty(driver.findElements(By.id("price_table_meta_id")));
    }
}
