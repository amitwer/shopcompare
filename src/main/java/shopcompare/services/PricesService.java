package shopcompare.services;

import lombok.NonNull;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import shopcompare.datacontainers.Price;
import shopcompare.datacontainers.PriceResult;
import shopcompare.exceptions.NoResultsFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class PricesService {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(PricesService.class);
    private final SuperGetApi superGetApi;


    @Autowired
    public PricesService(SuperGetApi superGetApi) {
        this.superGetApi = superGetApi;
    }

    /**
     * get a list of prices from SuperGet
     *
     * @param storeIds   - a list of store IDs of the store to look for
     * @param productIds -  A list of product ids to look for in each store
     * @return
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
                log.debug("calling getPrices for storeId:{}, products:{}", storeId, productIds);
                return superGetApi.getPrices(storeId, productIds).stream().filter(Objects::nonNull).map(this::translateToResult).collect(Collectors.toList());
            } catch (Exception e) {
                log.error("Got exception when calling getPrices", e);
            }
        }
        return Collections.emptyList();
    }

    private PriceResult translateToResult(@NonNull Price price) {
        return new PriceResult(price.getProductName(), price.getProductBarcode(), price.getStoreName(), price.getStoreProductPrice());
    }


}
