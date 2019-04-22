package shopcompare.controllers;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shopcompare.beans.DirectSearchParams;
import shopcompare.datacontainers.PriceResult;
import shopcompare.datacontainers.PriceResultByStore;
import shopcompare.services.PricesService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This Class handles requests for prices of specific products (with ID's)
 */
@Controller
public class SearchPricesController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SearchPricesController.class);
    private final PricesService pricesService;
    private final Set<String> directStoreIds;
    private final Set<String> directProductIds;

    @Autowired
    public SearchPricesController(PricesService pricesService, DirectSearchParams directSearchParams) {
        this.pricesService = pricesService;
        this.directStoreIds = directSearchParams.getStores();
        this.directProductIds = directSearchParams.getProducts();
    }

    @SuppressWarnings("SameReturnValue")
    @PostMapping("/getPrices")
    public String getPrices(@RequestParam String[] selectedProducts, ExtendedModelMap model) {
        Optional.ofNullable(selectedProducts).
                map(arr -> String.join(",", arr))
                .ifPresent(s -> log.info("Got prices for {}", s));
        HashSet<String> storeIds = new HashSet<>();
        storeIds.add("aaa");
        List<PriceResult> prices = pricesService.getPrices(storeIds, null);
        List<PriceResultByStore> priceResultByStores = sortPricesPerStore(prices);
        model.addAttribute("prices", priceResultByStores);
        if (CollectionUtils.isNotEmpty(priceResultByStores)) {
            List<String> products = priceResultByStores.get(0).getPriceList().stream().map(PriceResult::getProductName).collect(Collectors.toList());
            model.put("products", products);
        }
        return "pricesTable";
    }

    private List<PriceResultByStore> sortPricesPerStore(List<PriceResult> prices) {
        List<PriceResultByStore> allStores = prices.stream().map(PriceResult::getStore).distinct().map(PriceResultByStore::new).collect(Collectors.toList());
        Set<String> allBarcodes = prices.stream().map(PriceResult::getBarcode).collect(Collectors.toSet());
        for (String barcode : allBarcodes) {
            List<PriceResult> pricesForBarcode = prices.stream().filter(price -> price.getBarcode().equals(barcode)).collect(Collectors.toList());
            String productName = pricesForBarcode.get(0).getProductName();
            allStores.forEach(store -> store.add(pricesForBarcode.stream().filter(price -> price.getStore().equals(store.getStoreName())).findAny().orElse(new PriceResult(productName, barcode, store.getStoreName(), null))));
        }
        return allStores;
    }

    @SuppressWarnings("SameReturnValue")
    @GetMapping("/directAccess")
    public String directHardCodedSearch(ExtendedModelMap model) {
        model.addAttribute("prices", pricesService.getPrices(directStoreIds, directProductIds));
        return "pricesTable";
    }

}
