package shopcompare;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.MethodNotAllowedException;
import shopcompare.exceptions.AccessForbiddenException;

@Controller
public class ShopCompareController {


    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @RequestMapping("/")
    public String mainPage() {
        return "loginPage";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        if (username.equalsIgnoreCase("amit") && password.equals("wertheimer")) {
            return "searchPage";
        }
        throw new AccessForbiddenException();
    }

    @PostMapping("/searchProductName")
    public String searchProductName(String productName) {
        return "productSearchResults";
    }
    @RequestMapping("**")
    public String catchAll(){
        return "Catch-All";
    }
}
