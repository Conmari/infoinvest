package scari.corp.infoinvest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @GetMapping("/s")
    public String index() {
        return "index";
    }
    @GetMapping("/1")
    public String index1() {
        return "index1";
    }
}
