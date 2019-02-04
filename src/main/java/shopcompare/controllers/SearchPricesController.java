package shopcompare.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shopcompare.beans.DirectSearchParams;
import shopcompare.services.PricesService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * This Class handles requests for prices of specific products (with ID's)
 */
@Slf4j
@Controller
public class SearchPricesController {

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
                .ifPresent(s -> log.info("Got prices for <" + s + ">"));
        HashSet<String> storeIds = new HashSet<>();
        storeIds.add("aaa");
        model.addAttribute("prices", pricesService.getPrices(storeIds, null));
        return "pricesTable";
    }

    @SuppressWarnings("SameReturnValue")
    @GetMapping("/directAccess")
    public String directHardCodedSearch(ExtendedModelMap model) {
        model.addAttribute("prices", pricesService.getPrices(directStoreIds, directProductIds));
        return "pricesTable";
    }

}
