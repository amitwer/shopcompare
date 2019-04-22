package shopcompare.controllers;

import com.google.common.collect.Lists;
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
import shopcompare.datacontainers.PriceResultByStore;
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

    @Test
    void getPricesReturnsPricesPerSuper() {
        SearchPricesController searchPricesController = getSearchPricesController(pricesService, null, null);
        PriceResult price11 = new PriceResult("name1", "1", "store1", "price11");
        PriceResult price12 = new PriceResult("name2", "2", "store1", "price12");
        PriceResult price13 = new PriceResult("name3", "3", "store1", null);
        PriceResult price21 = new PriceResult("name1", "1", "store2", "price21");
        PriceResult price22 = new PriceResult("name2", "2", "store2", null);
        PriceResult price23 = new PriceResult("name3", "3", "store2", "price23");
        when(pricesService.getPrices(any(), any())).thenReturn(Lists.newArrayList(price11, price12, price21, price23));
        searchPricesController.getPrices(null, model);
        Object prices = model.get("prices");
        assertThat(prices).isNotNull().isInstanceOf(List.class);
        List pricesList = (List) prices;
        assertThat(pricesList).hasSize(2);
        assertThat(pricesList).hasOnlyElementsOfType(PriceResultByStore.class);
        assertThat(pricesList).containsExactlyInAnyOrder(new PriceResultByStore("store1", price11, price12, price13),
                new PriceResultByStore("store2", price21, price22, price23));


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
        assertThat((List) prices).hasOnlyElementsOfType(PriceResultByStore.class);
        assertThat((List<PriceResultByStore>) prices).containsExactlyInAnyOrderElementsOf(sortPricesByStore(expectedPrices));
        verify(pricesService, times(1)).getPrices(any(), any());
    }

    @ParameterizedTest(name = "[{index}] Search prices is dynamic: {arguments}")
    @MethodSource("pricesToTest")
    void searchPricesSetsProductsInModel(List<PriceResult> expectedPrices) {
        SearchPricesController searchPricesController = getSearchPricesController(pricesService, null, null);
        when(pricesService.getPrices(any(), any())).thenReturn(expectedPrices);
        searchPricesController.getPrices(null, model);
        Object products = model.get("products");
        assertThat(products).isNotNull().isInstanceOf(List.class);
        assertThat((List) products).hasOnlyElementsOfType(String.class);
        assertThat((List<String>) products).containsExactlyInAnyOrderElementsOf(expectedPrices.stream().map(PriceResult::getProductName).distinct().collect(Collectors.toList()));
    }

    @ParameterizedTest(name = "[{index}] Search prices is dynamic: {arguments}")
    @MethodSource("pricesToTest")
    void productTitlesAreInSameOrderAsInnerResults(List<PriceResult> expectedPrices) {
        SearchPricesController searchPricesController = getSearchPricesController(pricesService, null, null);
        when(pricesService.getPrices(any(), any())).thenReturn(expectedPrices);
        searchPricesController.getPrices(null, model);
        List<String> products = (List<String>) model.get("products");
        List<PriceResultByStore> prices = (List<PriceResultByStore>) model.get("prices");
        for (PriceResultByStore price : prices) {
            assertThat(products).isEqualTo(price.getPriceList().stream().map(PriceResult::getProductName).collect(Collectors.toList()));
        }
    }

    private Collection<PriceResultByStore> sortPricesByStore(List<PriceResult> priceResults) {
        List<PriceResultByStore> priceResultByStores = new LinkedList<>();
        priceResults.stream().map(p -> p.getStore()).distinct().forEach(storeName -> priceResultByStores.add(new PriceResultByStore(storeName)));
        Set<String> barcodes = priceResults.stream().map(price -> price.getBarcode()).collect(Collectors.toSet());
        for (String barcode : barcodes) {
            Map<String, PriceResult> pricesByStore = priceResults.stream().filter(price -> price.getBarcode().equals(barcode)).collect(Collectors.toMap(p -> p.getStore(), p -> p));
            String productName = priceResults.stream().filter(price -> price.getBarcode().equals(barcode))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Cannot find product for barcode <" + barcode + "> really did not expect to be here"))
                    .getProductName();
            priceResultByStores.forEach(pr -> pr.add(pricesByStore.getOrDefault(pr.getStoreName(), new PriceResult(productName, barcode, pr.getStoreName(), null))));
        }
        return priceResultByStores;
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
