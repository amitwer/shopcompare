package shopcompare.controllers;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import shopcompare.datacontainers.Product;

import java.util.Collections;

@Controller
public class SearchProductsByNameController {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SearchProductsByNameController.class);

    @SuppressWarnings("SameReturnValue")
    @PostMapping("/searchProductName")
    public String searchProductName(String productName, ExtendedModelMap model) {
        log.info("Search for product <{}>",productName);
        model.addAttribute("products", Collections.singletonList(new Product()));
        return "productSearchResults";
    }

}
