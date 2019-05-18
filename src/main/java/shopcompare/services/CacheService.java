package shopcompare.services;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;
import shopcompare.datacontainers.Price;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class CacheService {
    public boolean isPriceCached(String storeId, String productId) {
        throw new NotImplementedException("Still writing this one....");
    }

    public List<Price> getPricesFromCache(String storeId, Set<String> productIds) {
        throw new NotImplementedException("Not having a cache yet...");
    }

    public void cacheResults(Collection<Price> resultsToCache) {
        throw new NotImplementedException("Not having a cache yet...");
    }
}
