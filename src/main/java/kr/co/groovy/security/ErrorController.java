package kr.co.groovy.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/security")
@Controller
public class ErrorController {
    @GetMapping("/401")
    public String error401() {
        return "security/401";
    }

    @GetMapping("/403")
    public String error403() {
        return "security/403";
    }

    @GetMapping("/404")
    public String error404() {
        return "security/404";
    }

    @GetMapping("/500")
    public String error500() {
        return "security/500";
    }
}
