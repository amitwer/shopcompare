package shopcompare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shopcompare.exceptions.AccessForbiddenException;
import shopcompare.services.CityService;

@SuppressWarnings("SameReturnValue")
@Controller
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
            return "searchPage";
        }
        throw new AccessForbiddenException();
    }

    @PostMapping("/searchProductName")
    public String searchProductName(String productName) {
        return "productSearchResults";
    }


    @RequestMapping("**")
    public String catchAll() {
        throw new AccessForbiddenException();
    }
}
