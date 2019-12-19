package com.nepdroid.pro1.login.controller;

import com.nepdroid.pro1.login.entity.AppUser;
import com.nepdroid.pro1.login.userRepo.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mobile")
public class RegisterRestController {

    @Autowired
    private AppUserRepo appUserRepo;

    @PostMapping("/register")
    public AppUser processMobileUserRegistration(@RequestBody AppUser appUser){
        AppUser appUser1 = appUserRepo.save(appUser);
        return appUser1;
    }

    // Calling registered users
    @GetMapping(value = "", produces = "application/json")
    public List<AppUser> getAll(){
        return appUserRepo.findAll();
    }

}
