package shopcompare.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.ui.ExtendedModelMap;
import shopcompare.datacontainers.PriceResult;
import shopcompare.services.PricesService;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Tags({@Tag("Short"), @Tag("All"), @Tag("Search-Products")})
class SearchPricesControllerTest {

    private ExtendedModelMap model;

    private static Stream<List<PriceResult>> pricesToTest() {
        return Stream.of(
                Arrays.asList(new PriceResult("1", "2", "3", "4"), new PriceResult("5", "6", "7", "8")),
                Arrays.asList(new PriceResult("a", "b", "c", "d"),
                        new PriceResult("e", "f", "g", "h"),
                        new PriceResult("i", "j", "k", "l"))
        );
    }


    @BeforeEach
    void init() {
        model = new ExtendedModelMap();
    }

    @Test
    void searchPriceReturnsCorrectPage() {
        SearchPricesController searchPricesController = getSearchPricesController(null);
        String pageName = searchPricesController.getPrices(null, model);
        assertThat(pageName).isEqualTo("pricesTable");
    }

    @ParameterizedTest(name = "[{index}] Search prices is dynamic: {arguments}")
    @MethodSource("pricesToTest")
    void searchPriceIsDynamic(List<PriceResult> expectedPrices) {
        PricesService pricesService = Mockito.mock(PricesService.class);
        SearchPricesController searchPricesController = getSearchPricesController(pricesService);
        when(pricesService.getPrices()).thenReturn(expectedPrices);
        searchPricesController.getPrices(null, model);
        Object prices = model.get("prices");
        assertThat(prices).isNotNull().isInstanceOf(List.class);
        assertThat(prices).isEqualTo(expectedPrices);
        verify(pricesService, times(1)).getPrices();
    }

    private SearchPricesController getSearchPricesController(PricesService pricesService) {
        if (Objects.isNull(pricesService)) {
            return new SearchPricesController(new PricesService());
        }
        return new SearchPricesController(pricesService);
    }
}
