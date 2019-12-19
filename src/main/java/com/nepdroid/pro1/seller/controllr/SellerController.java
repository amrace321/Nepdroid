package com.nepdroid.pro1.seller.controllr;

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

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/seller")
public class SellerController {


    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private AppUserRepo appUserRepo;

    @Autowired

    @GetMapping("")
    public ModelAndView goAdminpage() {
        // 1. Inject Principal Object in method
        // 2. get Currently logged in user email
        // 3. find details of currently logged in user by appuserrepo
        // 4. pass user id

        List<Item> listOfItem = itemRepository.findByUserId(8);
        List<Category> listOfCategory = categoryRepo.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("seller/seller-dashboard");
        modelAndView.addObject("itemList", listOfCategory);
        modelAndView.addObject("role", "ROLE_SELLER");
        modelAndView.addObject("categorylist", listOfCategory);
        modelAndView.addObject("listofItem", listOfItem);
        modelAndView.addObject("listSize", listOfItem.size());
        categoryRepo.findAll();
        return modelAndView;


    }

    @GetMapping("item/add/{catId}")
    public ModelAndView goAddItem(@PathVariable int catId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("categoryId", catId);
        modelAndView.addObject("item", new Item());
        modelAndView.setViewName("seller/add-new-item");

        return modelAndView;
    }

    @PostMapping(value = "/add-entity/save")
    public String processPostForm(@ModelAttribute Item item, HttpServletRequest request, Principal principal) {
        String email = principal.getName();
        AppUser appUser = appUserRepo.findByEmail(email);

        String imageUrl = request.getParameter("imagePath");
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));

        item.setCategoryId(categoryId);
        item.setImageUrl(imageUrl);
        item.setUserId(appUser.getAppuserId());
        itemRepository.save(item);
        return "redirect:/seller";
    }

 @GetMapping(value="/edit-item/{itemId}")
    public ModelAndView getEditItem(@PathVariable int itemId ){
        Item fetchedItem=itemRepository.getOne(itemId);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("seller/edit-item");

        modelAndView.addObject("item",fetchedItem);

        return modelAndView;
    }
    @PostMapping(value="/edit-item/{itemId}")
    public String  processEdititem(@ModelAttribute Item updateItem,HttpServletRequest request,@PathVariable int itemId){

        Item item = itemRepository.getOne(itemId);
        item.setItemName(updateItem.getItemName());
        item.setDescription(updateItem.getDescription());
        item.setPrice(updateItem.getPrice());
        item.setNegotiable(updateItem.getNegotiable());
        item.setImageUrl(request.getParameter("imagePath"));

        itemRepository.save(item);

        return "redirect:/seller";
    }




    @GetMapping(value="/delete-item/{itemId}")
    public String deleteStudent(@PathVariable int  itemId){

        itemRepository.deleteById(itemId);
        return "redirect:/seller";

    }
}
