package shopcompare;

import org.junit.jupiter.api.Test;
import shopcompare.exceptions.AccessForbiddenException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class ShopCompareControllerTest {


    @Test
    public void mainPageIsLogin(){
        ShopCompareController shopCompareController = getShopCompareController();
        assertThat(shopCompareController.mainPage()).isEqualTo("loginPage");
    }

    @Test
    public void loginSuccessful(){
        assertThat(getShopCompareController().login("amit","wertheimer")).isEqualTo("searchPage");
    }

    @Test
    public void loginFailed(){
        ShopCompareController shopCompareController = getShopCompareController();
        assertThatThrownBy(()->shopCompareController.login("notAmit","notWertheimer")).as("Invalid credentials").isInstanceOf(AccessForbiddenException.class);
    }

    @Test
    public void searchProductName(){
        ShopCompareController shopCompareController = getShopCompareController();
        assertThat(shopCompareController.searchProductName("name")).isEqualTo("productSearchResults");
    }
    private ShopCompareController getShopCompareController() {
        return new ShopCompareController();
    }



}