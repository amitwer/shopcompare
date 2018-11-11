package shopcompare.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import shopcompare.exceptions.AccessForbiddenException;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("SameReturnValue")
@Controller
@Slf4j
public class ShopCompareController {


    @RequestMapping("/")
    public String mainPage() {
        return "loginPage";
    }

    @RequestMapping("**")
    public String catchAll(HttpServletRequest request) {
        log.error("Got invalid <" + request.getMethod() + "> request  <" + request.getRequestURI() + ">");
        throw new AccessForbiddenException();
    }
}
