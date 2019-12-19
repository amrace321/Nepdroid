package com.nepdroid.pro1.admin.controller;

import com.nepdroid.pro1.admin.entity.Category;
import com.nepdroid.pro1.admin.repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController  {
    @Autowired
    private CategoryRepo categoryRepo;

   @GetMapping("")

    public ModelAndView goAdminpage(){
        List<Category> listOfCategory= categoryRepo.findAll();
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("admin/admin-dashboard");
modelAndView.addObject("role","ROLE_ADMIN");
        modelAndView.addObject("category",new Category());
        modelAndView.addObject("categorylist",listOfCategory);
        categoryRepo.findAll();
        return modelAndView;


    }

    @GetMapping(value="/delete/{categoryId}")

    public String  processDeletePost(@PathVariable int categoryId){
        categoryRepo.deleteById(categoryId);
        return "redirect:/admin";

    }



    @GetMapping("/add-category")
    public  ModelAndView goAddcategory(){
        List<Category> listOfCategory= categoryRepo.findAll();

        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("role","ROLE_ADMIN");
        modelAndView.setViewName("admin/add-category");
        modelAndView.addObject("category", new Category());
        modelAndView.addObject("categorylist",listOfCategory);

        return modelAndView;

    }


    @PostMapping(value = "/add-category/save")
    public String processPostForm(@ModelAttribute Category category){
        categoryRepo.save(category);
        return  "redirect:/admin";
    }



}
