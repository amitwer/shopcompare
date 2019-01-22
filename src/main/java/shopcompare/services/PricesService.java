package shopcompare.services;

import org.springframework.stereotype.Component;
import shopcompare.datacontainers.PriceResult;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Component
public class PricesService {
    public List<PriceResult> getPrices(Set<String> storeIds, Set<String> productIds) {
        return Arrays.asList(new PriceResult("במבה", "123456789", "רמי לוי תלפיות", "11.20"),
                new PriceResult("ביסלי", "98765431", "שופרסל דיל", "01.11")
        );
    }


}
