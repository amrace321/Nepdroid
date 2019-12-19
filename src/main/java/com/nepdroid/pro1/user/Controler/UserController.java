package com.nepdroid.pro1.user.Controler;

import com.nepdroid.pro1.admin.entity.Category;
import com.nepdroid.pro1.admin.repo.CategoryRepo;
import com.nepdroid.pro1.item.entity.Item;
import com.nepdroid.pro1.item.projection.ItemDetailsProjection;
import com.nepdroid.pro1.item.repo.ItemRepository;
import com.nepdroid.pro1.login.entity.AppUser;
import com.nepdroid.pro1.login.userRepo.AppUserRepo;
import com.nepdroid.pro1.user.entity.BidItem;
import com.nepdroid.pro1.user.repo.BidItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private AppUserRepo appUserRepo;

    @Autowired
    private BidItemRepo bidItemRepo;

    @GetMapping("")
    public ModelAndView goUser() {
        ModelAndView modelAndView = new ModelAndView();
        List<Item> listOfItem = itemRepository.findAll();
        List<Category> listOfCategory = categoryRepo.findAll();
        modelAndView.setViewName("user/user-dashboard");
        modelAndView.addObject("itemList", listOfCategory);
        modelAndView.addObject("role", "ROLE_USER");
        modelAndView.addObject("categoryList", listOfCategory);
        modelAndView.addObject("listOfItem", listOfItem);
        modelAndView.addObject("listSize", listOfItem.size());
        return modelAndView;
    }

    @GetMapping(value = "/bid-item/{itemId}")
    public ModelAndView getBidItem(@PathVariable int itemId) {
        List<Category> categoryList = categoryRepo.findAll();
       ItemDetailsProjection fetchItem = itemRepository.selectItemDetailsWithUserName(itemId);
        /*Item fetchItem = itemRepository.getOne(itemId);*/
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/bid-item");
        modelAndView.addObject("item", fetchItem);
        modelAndView.addObject("categoryList", categoryList);
        modelAndView.addObject("bid", new BidItem());
        modelAndView.addObject("itemId", itemId);
        return modelAndView;
    }

    @PostMapping("/bid-item")
    public String processBidAmount(@ModelAttribute BidItem bidItem, HttpServletRequest request, Principal principal) {
        String appUserEmail = principal.getName();
        AppUser appUser = appUserRepo.findByEmail(appUserEmail);
        int itemId = Integer.parseInt(request.getParameter("itemId"));
        bidItem.setItemId(itemId);
        bidItem.setAppUserId(appUser.getAppuserId());
        bidItemRepo.save(bidItem);
        return "redirect:/user";
    }
}
