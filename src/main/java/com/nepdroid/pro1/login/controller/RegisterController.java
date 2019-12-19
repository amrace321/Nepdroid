package com.nepdroid.pro1.login.controller;

import com.nepdroid.pro1.login.entity.AppUser;
import com.nepdroid.pro1.login.userRepo.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


@Autowired
private AppUserRepo appUserRepo;
    @GetMapping("/user")
    public ModelAndView processUsers(ModelAndView modelAndView){
modelAndView.setViewName("register");
modelAndView.addObject("appUser", new AppUser());
modelAndView.addObject("role","ROLE_USER");
return  modelAndView;
    }
    @GetMapping("/seller")
    public ModelAndView processSeller(ModelAndView modelAndView){
        modelAndView.setViewName("register");
        modelAndView.addObject("appUser", new AppUser());
        modelAndView.addObject("role","ROLE_SELLER");
        return  modelAndView;

    }


    @PostMapping(value = "")
    public String processPostForm(@ModelAttribute AppUser appUser, HttpServletRequest request){
        String role=request.getParameter("role_user");
        String clearPassword=request.getParameter("password");
        String hasPassword=bCryptPasswordEncoder.encode(clearPassword);
        appUser.setPassword(hasPassword);
        appUser.setEnabled(true);
        appUser.setRole(role);
        appUserRepo.save(appUser);
        return  "redirect:/";
    }



}
