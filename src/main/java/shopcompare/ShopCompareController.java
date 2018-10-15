package shopcompare;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shopcompare.exceptions.AccessForbiddenException;
import shopcompare.services.CityService;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("SameReturnValue")
@Controller
@Slf4j
public class ShopCompareController {

    private final CityService cityService;

    @Autowired
    public ShopCompareController(CityService cityService) {
        this.cityService = cityService;
    }

    @RequestMapping("/")
    public String mainPage() {
        return "loginPage";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        if (username.equalsIgnoreCase("Amit") && password.equals("wertheimer")) {
            model.addAttribute("cities", cityService.getCities());
            return "MainApplicationPage";
        }
        throw new AccessForbiddenException();
    }

    @PostMapping("/searchProductName")
    public String searchProductName(String productName) {
        return "productSearchResults";
    }


    @PostMapping("/getPrices")
    public String getPrices(@RequestParam String[] selectedProducts) {
        log.info("Got prices request for <" + String.join(",", selectedProducts) + ">");
        return "pricesTable";
    }

    @RequestMapping("**")
    public String catchAll(HttpServletRequest request) {
        log.error("Got invalid <" + request.getMethod() + "> request  <" + request.getRequestURI() + ">");
        throw new AccessForbiddenException();
    }
}
