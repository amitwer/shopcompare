package shopcompare.endtoend.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class LoginPage extends BasePage {

    @FindBy(how = How.ID, id = "username")
    private WebElement username;
    @FindBy(how = How.ID, id = "password")
    private WebElement password;
    @FindBy(how = How.ID, id = "loginBtn")
    private WebElement loginBtn;


    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String username, String password) {
        this.username.sendKeys(username);
        this.password.sendKeys(password);
        this.loginBtn.click();

    }

    public void open(int port) {
        driver.get("http://localhost:" + port + "/");
    }
}
