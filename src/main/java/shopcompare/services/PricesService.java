package shopcompare.services;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import shopcompare.datacontainers.Price;
import shopcompare.datacontainers.PriceResult;
import shopcompare.exceptions.NoResultsFoundException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class is meant to provide the application with product prices.
 * Results are cached for a day per store
 */
@Service
public class PricesService {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(PricesService.class);
    private final SuperGetApi superGetApi;
    private final CacheService cacheService;


    @Autowired
    public PricesService(SuperGetApi superGetApi, CacheService cacheService) {
        this.superGetApi = superGetApi;
        this.cacheService = cacheService;
    }

    /**
     * get a list of prices from cache or from API if no results are cached
     *
     * @param storeIds   - a list of store IDs of the store to look for
     * @param productIds -  A list of product ids to look for in each store
     * @return a list of {@link PriceResult} for the relevant IDs
     */
    public List<PriceResult> getPrices(Set<String> storeIds, Set<String> productIds) {
        if (CollectionUtils.isNotEmpty(storeIds)) {
            List<PriceResult> prices = new LinkedList<>();
            storeIds.stream().map(storeId -> getPricesOrEmpty(productIds, storeId)).filter(Objects::nonNull).forEach(prices::addAll);
            if (noResultsFound(prices)) {
                throw new NoResultsFoundException("did not find results for and of the requested items in any store");
            }
            return prices;
        }
        throw new IllegalArgumentException("Store IDs cannot be empty");
    }

    private boolean noResultsFound(List<PriceResult> prices) {
        return prices.stream().map(PriceResult::getPrice).noneMatch(StringUtils::isNotEmpty);
    }

    /*
    Calls the API, deals with exceptions and return null instead
     */
    private List<PriceResult> getPricesOrEmpty(Set<String> productIds, String storeId) {
        if (CollectionUtils.isNotEmpty(productIds)) {
            try {
                List<Price> rawPrices;
                Set<String> nonCachedProductIds = getNonCachedProducts(storeId, productIds);
                if (nonCachedProductIds.isEmpty()) {
                    log.debug("calling getPrices for storeId:{}, products:{}", storeId, productIds);
                    rawPrices = superGetApi.getPrices(storeId, nonCachedProductIds);
                } else {
                    rawPrices = cacheService.getPricesFromCache(storeId, productIds);
                }
                return rawPrices.stream().map(this::translateToResult).collect(Collectors.toList());
            } catch (Exception e) {
                log.error("Got exception when calling getPrices", e);
            }
        }
        return Collections.emptyList();
    }

    private Set<String> getNonCachedProducts(String storeId, Set<String> productIds) {
        return productIds.stream().filter(id -> isNotInCache(storeId, id)).collect(Collectors.toSet());
    }

    private boolean isNotInCache(String storeId, String productId) {
        return cacheService.isPriceCached(storeId, productId);
    }

    private PriceResult translateToResult(@NonNull Price price) {
        return new PriceResult(price.getProductName(), price.getProductBarcode(), price.getStoreName(), price.getStoreProductPrice());
    }


}
