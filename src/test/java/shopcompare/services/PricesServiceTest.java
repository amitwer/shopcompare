package shopcompare.services;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import shopcompare.datacontainers.Price;
import shopcompare.datacontainers.PriceResult;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.compress.utils.Sets.newHashSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class PricesServiceTest {

    private SuperGetApi superGetApi;
    private PricesService pricesService;

    private static Stream<Set<String>> nullAndEmptySet() {
        return Stream.of(new HashSet<>(), null);
    }

    //invokes API with correct method

    @BeforeEach
    private void setup() {
        superGetApi = mock(SuperGetApi.class);
        pricesService = new PricesService(superGetApi);
    }

    @Test
    void getPricesCallsCorrectApiFunction() {
        when(superGetApi.getPrices(any(), any())).thenReturn(Collections.singletonList(dummyPrice("dummy")));
        pricesService.getPrices(newHashSet("StoreId"), newHashSet("product1"));
        verify(superGetApi, times(1)).getPrices(anyString(), anySet());
    }

    @Test
    void getPricesReturnsApiValues() {
        ArrayList<Price> expectedValue = Lists.newArrayList(dummyPrice("dummy"));
        when(superGetApi.getPrices(any(), any())).thenReturn(expectedValue);
        List<PriceResult> prices = pricesService.getPrices(newHashSet("id"), newHashSet("product"));
        assertThat(prices).containsExactlyElementsOf(expectedValue.stream().map(this::priceToPriceResult).collect(Collectors.toSet()));
    }

    @ParameterizedTest
    @MethodSource("nullAndEmptySet")
    void getPricesThrowsExceptionIfStoreIdsIsEmpty(HashSet<String> storeIds) {
        assertThatThrownBy(() -> pricesService.getPrices(storeIds, newHashSet("product"))).hasNoCause().isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @Disabled
    void getPricesInvokesApiOncePerStore() {
        pricesService.getPrices(newHashSet("store1", "store2"), newHashSet("product1", "product2"));
        verify(superGetApi, times(1)).getPrices(eq("store2"), any());
        verify(superGetApi, times(1)).getPrices(eq("store1"), any());
        verify(superGetApi, times(2)).getPrices(any(), any());
    }

    @Test
    void getPricesCombinesResultsOfAllApiCalls() {
        List<Price> store1Result = Lists.newArrayList(dummyPrice("1"), dummyPrice("2"));
        when(superGetApi.getPrices(eq("store1"), any())).thenReturn(store1Result);
        List<Price> store2Result = Lists.newArrayList(dummyPrice("3"), dummyPrice("4"));
        when(superGetApi.getPrices(eq("store2"), any())).thenReturn(store2Result);
        List<PriceResult> prices = pricesService.getPrices(newHashSet("store1", "store2"), newHashSet("product1", "product2"));
        verify(superGetApi, times(2)).getPrices(any(), any());
        List<PriceResult> expectedResults = new LinkedList<>();
        expectedResults.addAll(store1Result.stream().map(this::priceToPriceResult).collect(Collectors.toSet()));
        expectedResults.addAll(store2Result.stream().map(this::priceToPriceResult).collect(Collectors.toSet()));
        assertThat(prices).containsExactlyInAnyOrderElementsOf(expectedResults);
    }

    @Test
    @Disabled
    void getPricesCanDealWithNullValues() {
        when(superGetApi.getPrices(any(), any())).thenReturn(null);
        List<PriceResult> prices = pricesService.getPrices(newHashSet("store1"), newHashSet("product1", "product2"));
        assertThat(prices).isEmpty();
    }

    @Test
    @Disabled
    void getPricesCanDealWithException() {
        when(superGetApi.getPrices(any(), any())).thenThrow(new RuntimeException("If you got this, we've failed the test"));
        List<PriceResult> prices = pricesService.getPrices(newHashSet("store1"), newHashSet("product1", "product2"));
        assertThat(prices).isEmpty();
    }

    @Test
    void getPricesCanDealWithExceptionButCollectOther() {
        when(superGetApi.getPrices(eq("store2"), any())).thenThrow(new RuntimeException("If you got this, we've failed the test"));
        Price store1Result = dummyPrice("1");
        when(superGetApi.getPrices(eq("store1"), any())).thenReturn(Lists.newArrayList(store1Result));
        Price store3Result = dummyPrice("3");
        when(superGetApi.getPrices(eq("store3"), any())).thenReturn(Lists.newArrayList(store3Result));
        List<PriceResult> prices = pricesService.getPrices(newHashSet("store1", "store2", "store3"), newHashSet("product1", "product2"));
        assertThat(prices).containsExactlyInAnyOrder(priceToPriceResult(store1Result), priceToPriceResult(store3Result));
    }

    private Price dummyPrice(String constantValue) {
        Price price = new Price();
        price.setProductId(constantValue);
        price.setProductBarcode(constantValue);
        price.setStoreName(constantValue);
        price.setStoreProductPrice(constantValue);
        return price;
    }

    private PriceResult priceToPriceResult(Price price) {
        return new PriceResult(price.getProductName(), price.getProductBarcode(), price.getStoreName(), price.getStoreProductPrice());
    }
}