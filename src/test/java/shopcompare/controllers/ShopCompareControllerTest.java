package shopcompare.controllers;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import shopcompare.exceptions.AccessForbiddenException;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Tags({@Tag("Short"), @Tag("All")})
class ShopCompareControllerTest {


    @Test
    void mainPageIsLogin() {
        ShopCompareController shopCompareController = getShopCompareController();
        assertThat(shopCompareController.mainPage()).isEqualTo("loginPage");
    }


    @Test
    void testUnmappedRequest() {
        ShopCompareController shopCompareController = getShopCompareController();
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getRequestURI()).thenReturn("Just a uri");
        assertThatThrownBy(() -> shopCompareController.catchAll(request))
                .as("Unmapped request")
                .isInstanceOf(AccessForbiddenException.class);
    }


    private ShopCompareController getShopCompareController() {
        return new ShopCompareController();
    }
}