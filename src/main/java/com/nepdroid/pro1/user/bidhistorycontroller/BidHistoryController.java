package com.nepdroid.pro1.user.bidhistorycontroller;

import com.nepdroid.pro1.user.repo.BidItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("/bid-history")
public class BidHistoryController {

    @Autowired
    private BidItemRepo bidItemRepo;

    @GetMapping("")
    public ModelAndView getbidHistory(ModelAndView modelAndView){
        modelAndView.addObject("listOfBidItem", bidItemRepo.findAll() );
        modelAndView.setViewName("user/bid-history");
        return modelAndView;
    }

    @GetMapping("/delete/{bidId}")
    public String deleteBidHistory(@PathVariable int bidId, Principal principal){
        String name = principal.getName();
        bidItemRepo.deleteById(bidId);
        return "redirect:/bid-history";
    }
}
