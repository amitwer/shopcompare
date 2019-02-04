package shopcompare.services;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import shopcompare.datacontainers.Price;
import shopcompare.datacontainers.PriceResult;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class PricesService {
    private final SuperGetApi superGetApi;

    @Autowired
    public PricesService(SuperGetApi superGetApi) {
        this.superGetApi = superGetApi;
    }

    public List<PriceResult> getPrices(Set<String> storeIds, Set<String> productIds) {
        if (CollectionUtils.isNotEmpty(storeIds)) {
            List<PriceResult> prices = new LinkedList<>();
            storeIds.stream().map(storeId -> getPricesOrEmpty(productIds, storeId)).filter(Objects::nonNull).forEach(prices::addAll);
            return prices;
        }
        throw new IllegalArgumentException("Store IDs cannot be empty");
    }

    /*
    Calls the API, deals with exceptions and return null instead
     */
    private List<PriceResult> getPricesOrEmpty(Set<String> productIds, String storeId) {
        try {
            log.debug("calling getPrices for storeId:{}, products:{}", storeId, productIds);
            return superGetApi.getPrices(storeId, productIds).stream().filter(Objects::nonNull).map(this::translateToResult).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Got exception when calling getPrices", e);
            return Collections.emptyList();
        }
    }

    private PriceResult translateToResult(@NonNull Price price) {
        return new PriceResult(price.getProductName(), price.getProductBarcode(), price.getStoreName(), price.getStoreProductPrice());
    }


}
