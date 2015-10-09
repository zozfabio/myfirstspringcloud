package br.com.springcloudsamples.hystrixserver.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zozfabio on 23/09/15.
 */
@Controller
@RequestMapping("/")
class DashboardController {

    @RequestMapping
    public String home() {
        return "forward:/hystrix/index.html";
    }
}
