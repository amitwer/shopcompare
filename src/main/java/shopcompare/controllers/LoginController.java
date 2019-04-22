package shopcompare.controllers;

import org.slf4j.Logger;
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
public class LoginController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(LoginController.class);
    private final CityService cityService;

    @Autowired
    public LoginController(CityService cityService) {
        this.cityService = cityService;
    }

    @SuppressWarnings("SameReturnValue")
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        log.info("got login request for user <{}>", username);
        if (username.equalsIgnoreCase("Amit") && password.equals("wertheimer")) {
            model.addAttribute("cities", cityService.getCities());
            return "MainApplicationPage";
        }
        throw new AccessForbiddenException();
    }

}
