package shopcompare;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import shopcompare.exceptions.AccessForbiddenException;
import shopcompare.services.CityService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Tags({@Tag("Short"),@Tag("All")})
class ShopCompareControllerTest {



    @Test
    void mainPageIsLogin(){
        ShopCompareController shopCompareController = getShopCompareController();
        assertThat(shopCompareController.mainPage()).isEqualTo("loginPage");
    }

    @Test
    void loginSuccessful(){
        assertThat(getShopCompareController().login("amit", "wertheimer", new ExtendedModelMap())).isEqualTo("MainApplicationPage");
    }

    @Test
    void loginFailed(){
        ShopCompareController shopCompareController = getShopCompareController();
        assertThatThrownBy(() -> shopCompareController.login("notAmit", "notWertheimer", new ExtendedModelMap()))
                .as("Invalid credentials")
                .isInstanceOf(AccessForbiddenException.class);
    }

    @Test
    void searchProductName(){
        ShopCompareController shopCompareController = getShopCompareController();
        assertThat(shopCompareController.searchProductName("name")).isEqualTo("productSearchResults");
    }
    private ShopCompareController getShopCompareController() {
        return new ShopCompareController(new CityService());
    }



}