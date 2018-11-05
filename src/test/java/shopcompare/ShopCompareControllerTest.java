package shopcompare;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ExtendedModelMap;
import shopcompare.exceptions.AccessForbiddenException;
import shopcompare.services.CityService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Tags({@Tag("Short"), @Tag("All")})
class ShopCompareControllerTest {


    @Test
    void mainPageIsLogin() {
        ShopCompareController shopCompareController = getShopCompareController(null);
        assertThat(shopCompareController.mainPage()).isEqualTo("loginPage");
    }

    @Test
    void loginSuccessful() {
        ShopCompareController controller = getShopCompareController(null);
        String loginResult = controller.login("amit", "wertheimer", new ExtendedModelMap());
        assertThat(loginResult).isEqualTo("MainApplicationPage");
    }

    @Test
    void loginFailed() {
        ShopCompareController shopCompareController = getShopCompareController(null);
        assertThatThrownBy(() -> shopCompareController.login("notAmit", "notWertheimer", new ExtendedModelMap()))
                .as("Invalid credentials")
                .isInstanceOf(AccessForbiddenException.class);
    }

    @Test
    void testUnmappedRequest() {
        ShopCompareController shopCompareController = getShopCompareController(null);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getRequestURI()).thenReturn("Just a uri");
        assertThatThrownBy(() -> shopCompareController.catchAll(request))
                .as("Unmapped request")
                .isInstanceOf(AccessForbiddenException.class);
    }

    @Test
    void searchProductName() {
        ShopCompareController shopCompareController = getShopCompareController(null);
        assertThat(shopCompareController.searchProductName("name")).isEqualTo("productSearchResults");
    }

    @Test
    void testCitiesAreInMainPageModel() {
        ExtendedModelMap model = new ExtendedModelMap();
        CityService cityService = Mockito.mock(CityService.class);
        List<String> expectedCities = Arrays.asList("City1", "city2", "city3");
        Mockito.when(cityService.getCities()).thenReturn(expectedCities);
        getShopCompareController(cityService).login("amit", "wertheimer", model);
        assertThat(model.get("cities")).isEqualTo(expectedCities);
    }

    private ShopCompareController getShopCompareController(CityService cityService) {
        if (Objects.isNull(cityService))
            return new ShopCompareController(new CityService());
        return new ShopCompareController(cityService);
    }


}