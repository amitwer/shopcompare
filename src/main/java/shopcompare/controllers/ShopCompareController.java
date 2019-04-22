package shopcompare.controllers;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import shopcompare.exceptions.AccessForbiddenException;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("SameReturnValue")
@Controller
public class ShopCompareController {


    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ShopCompareController.class);

    @RequestMapping("/")
    public String mainPage() {
        return "loginPage";
    }

    @RequestMapping("**")
    public String catchAll(HttpServletRequest request) {
        log.error("Got invalid <{}>, request  <{}>" , request.getRequestURI() ,request.getMethod() );
        throw new AccessForbiddenException();
    }
}
