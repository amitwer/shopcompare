package shopcompare.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import shopcompare.datacontainers.Product;

import java.util.Collections;

@Slf4j
@Controller
public class SearchProductsByNameController {
    @SuppressWarnings("SameReturnValue")
    @PostMapping("/searchProductName")
    public String searchProductName(String productName, ExtendedModelMap model) {
        model.addAttribute("products", Collections.singletonList(new Product()));
        return "productSearchResults";
    }

}
