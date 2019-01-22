package shopcompare.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shopcompare.services.PricesService;

import java.util.Optional;
import java.util.Set;

/**
 * This Class handles requests for prices of specific products (with ID's)
 */
@Slf4j
@Controller
public class SearchPricesController {

    private final PricesService pricesService;
    private Set<String> directStoreIds;
    private Set<String> directProdcutIds;

    @Autowired
    public SearchPricesController(PricesService pricesService, @Value("${direct.stores}") Set<String> directStoreIds, @Value("${direct.products}") Set<String> directProdcutIds) {
        this.pricesService = pricesService;
        this.directStoreIds = directStoreIds;
        this.directProdcutIds = directProdcutIds;
    }

    @SuppressWarnings("SameReturnValue")
    @PostMapping("/getPrices")
    public String getPrices(@RequestParam String[] selectedProducts, ExtendedModelMap model) {
        Optional.ofNullable(selectedProducts).
                map(arr -> String.join(",", arr))
                .ifPresent(s -> log.info("Got prices for <" + s + ">"));
        model.addAttribute("prices", pricesService.getPrices(null, null));
        return "pricesTable";
    }

    @GetMapping("/directAccess")
    public String directHardCodedSearch() {
        return "pricesTable";
    }

}
