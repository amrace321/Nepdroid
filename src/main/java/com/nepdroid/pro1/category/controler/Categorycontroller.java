package com.nepdroid.pro1.category.controler;

import com.nepdroid.pro1.admin.entity.Category;
import com.nepdroid.pro1.admin.repo.CategoryRepo;
import com.nepdroid.pro1.item.entity.Item;
import com.nepdroid.pro1.item.repo.ItemRepository;
import com.nepdroid.pro1.login.entity.AppUser;
import com.nepdroid.pro1.login.userRepo.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/category")
public class Categorycontroller {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private AppUserRepo appUserRepo;

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/{categoryId}")
    public ModelAndView goAdminpage(@PathVariable int categoryId, Principal principal) {
        String appUserEmail = principal.getName();
        AppUser appUser = appUserRepo.findByEmail(appUserEmail);
        Category category = categoryRepo.getOne(categoryId);
        List<Item> items = itemRepository.findByCategoryId(categoryId);

        List<Category> listOfCategory = categoryRepo.findAll();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("category-based-item");
        modelAndView.addObject("category", category.getCategoryName());
        modelAndView.addObject("categoryId", category.getCategoryId());
        modelAndView.addObject("role", appUser.getRole());
        modelAndView.addObject("categorylist", listOfCategory);
        modelAndView.addObject("itemList", items);
        modelAndView.addObject("listSize", items.size());
        //TODO fetch all items in this category

        return modelAndView;


    }


}