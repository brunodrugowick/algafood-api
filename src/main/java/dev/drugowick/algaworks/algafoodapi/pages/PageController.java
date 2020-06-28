package dev.drugowick.algaworks.algafoodapi.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping({ "/requests", "requests.html" })
    public String requestsPage() { return "requests"; }
}
