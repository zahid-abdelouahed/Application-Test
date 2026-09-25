package org.polytech.spring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping ("/hello")
    public String Hello(){
        return "Bonjour";
    }
}
