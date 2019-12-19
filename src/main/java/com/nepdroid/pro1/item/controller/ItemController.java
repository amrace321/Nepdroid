package com.nepdroid.pro1.item.controller;

import com.nepdroid.pro1.item.entity.Item;
import com.nepdroid.pro1.item.repo.ItemRepository;
import com.nepdroid.pro1.login.entity.AppUser;
import com.nepdroid.pro1.login.userRepo.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private AppUserRepo appUserRepo;

    @GetMapping("/search")
    public ModelAndView searchItems(@RequestParam("searchQuery") String itemName, Principal principal, ModelAndView modelAndView){
        String email = principal.getName();
        AppUser appUser = appUserRepo.findByEmail(email);
        List<Item> itemList = null;

        if(appUser.getRole().equals("ROLE_SELLER")){
            itemList = itemRepository.searchItemsByItemNameAndUserId(itemName+"%", appUser.getAppuserId());
            modelAndView.setViewName("seller/seller-dashboard");
        } else {
            itemList = itemRepository.searchItemsByItemName(itemName+"%");
            modelAndView.setViewName("user/user-dashboard");
        }

        modelAndView.addObject("listofItem", itemList);
        return modelAndView;
    }
}
