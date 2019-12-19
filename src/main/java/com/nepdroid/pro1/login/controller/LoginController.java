package com.nepdroid.pro1.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/login")
public class LoginController {

@GetMapping(value="")
    public ModelAndView getLoginPage(ModelAndView modelAndView){
    modelAndView.setViewName("login");
    return modelAndView;
}

}
