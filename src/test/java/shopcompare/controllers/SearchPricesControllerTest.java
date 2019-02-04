package shopcompare.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.ui.ExtendedModelMap;
import shopcompare.beans.DirectSearchParams;
import shopcompare.datacontainers.PriceResult;
import shopcompare.services.PricesService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Tags({@Tag("Short"), @Tag("All"), @Tag("Search-Products")})
class SearchPricesControllerTest {

    private ExtendedModelMap model;
    private PricesService pricesService;

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
        pricesService = Mockito.mock(PricesService.class);
    }

    @Test
    void searchPriceReturnsCorrectPage() {
        SearchPricesController searchPricesController = getSearchPricesController(null, null, null);
        String pageName = searchPricesController.getPrices(null, model);
        assertThat(pageName).isEqualTo("pricesTable");
    }

    @ParameterizedTest(name = "[{index}] Search prices is dynamic: {arguments}")
    @MethodSource("pricesToTest")
    void searchPriceIsDynamic(List<PriceResult> expectedPrices) {
        SearchPricesController searchPricesController = getSearchPricesController(pricesService, null, null);
        when(pricesService.getPrices(any(), any())).thenReturn(expectedPrices);
        searchPricesController.getPrices(null, model);
        Object prices = model.get("prices");
        assertThat(prices).isNotNull().isInstanceOf(List.class);
        assertThat(prices).isEqualTo(expectedPrices);
        verify(pricesService, times(1)).getPrices(any(), any());
    }

    @Test
    void testDirectAccessReturnsPricesTable() {
        SearchPricesController searchPricesController = getSearchPricesController(pricesService, null, null);
        String page = searchPricesController.directHardCodedSearch(model);
        assertThat(page).isEqualTo("pricesTable");
    }

    @Test
    void testDirectAccessWithPredefinedStoresAndProducts() {
        Set<String> storeIds = Stream.of("986", "111", "123").collect(Collectors.toSet());
        Set<String> productIds = Stream.of("777", "123").collect(Collectors.toSet());
        when(pricesService.getPrices(anySet(), anySet())).thenReturn(new LinkedList<>());
        SearchPricesController searchPricesController = getSearchPricesController(pricesService, storeIds, productIds);
        searchPricesController.directHardCodedSearch(model);
        verify(pricesService, times(1)).getPrices(eq(storeIds), eq(productIds));
    }

    @ParameterizedTest(name = "[{index}] Direct search is storing results in model: {arguments}")
    @MethodSource("pricesToTest")
    void directAccessStoresResultsInModel(List<PriceResult> expectedPrices) {
        SearchPricesController searchPricesController = getSearchPricesController(pricesService, null, null);
        when(pricesService.getPrices(any(), any())).thenReturn(expectedPrices);
        searchPricesController.directHardCodedSearch(model);
        Object prices = model.get("prices");
        assertThat(prices).isNotNull().isInstanceOf(List.class);
        assertThat(prices).isEqualTo(expectedPrices);
        verify(pricesService, times(1)).getPrices(any(), any());
    }


    private SearchPricesController getSearchPricesController(PricesService pricesService, Set<String> storeIds, Set<String> productIds) {
        if (Objects.isNull(pricesService)) {
            pricesService = mock(PricesService.class);
        }
        DirectSearchParams directSearchParams = new DirectSearchParams();
        directSearchParams.setProducts(productIds);
        directSearchParams.setStores(storeIds);
        return new SearchPricesController(pricesService, directSearchParams);
    }
}
