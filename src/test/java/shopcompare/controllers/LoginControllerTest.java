package shopcompare.controllers;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ExtendedModelMap;
import shopcompare.exceptions.AccessForbiddenException;
import shopcompare.services.CityService;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Tags({@Tag("Short"), @Tag("All"), @Tag("Login")})
class LoginControllerTest {

    @Test
    void loginSuccessful() {
        LoginController controller = getLoginController(null);
        String loginResult = controller.login("amit", "wertheimer", new ExtendedModelMap());
        assertThat(loginResult).isEqualTo("MainApplicationPage");
    }

    @Test
    void loginFailed() {
        LoginController shopCompareController = getLoginController(null);
        assertThatThrownBy(() -> shopCompareController.login("notAmit", "notWertheimer", new ExtendedModelMap()))
                .as("Invalid credentials")
                .isInstanceOf(AccessForbiddenException.class);
    }

    @Test
    void testCitiesAreInMainPageModel() {
        ExtendedModelMap model = new ExtendedModelMap();
        CityService cityService = Mockito.mock(CityService.class);
        List<String> expectedCities = Arrays.asList("City1", "city2", "city3");
        Mockito.when(cityService.getCities()).thenReturn(expectedCities);
        getLoginController(cityService).login("amit", "wertheimer", model);
        assertThat(model.get("cities")).isEqualTo(expectedCities);
    }


    private LoginController getLoginController(CityService cityService) {
        if (Objects.isNull(cityService))
            return new LoginController(new CityService());
        return new LoginController(cityService);
    }

}