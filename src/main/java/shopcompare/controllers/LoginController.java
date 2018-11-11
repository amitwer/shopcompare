package shopcompare.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shopcompare.exceptions.AccessForbiddenException;
import shopcompare.services.CityService;

/**
 * Handles Login requests to the application.
 * Successful login should lead to the main application page
 */
@Controller
@Slf4j
public class LoginController {

    private final CityService cityService;

    @Autowired
    public LoginController(CityService cityService) {
        this.cityService = cityService;
    }

    @SuppressWarnings("SameReturnValue")
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        if (username.equalsIgnoreCase("Amit") && password.equals("wertheimer")) {
            model.addAttribute("cities", cityService.getCities());
            return "MainApplicationPage";
        }
        throw new AccessForbiddenException();
    }

}
