package com.fredywise.freshfruit;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequestMapping("/freshfruit")
public class MainController {

    @Autowired
    private IMainService mainService;

    @GetMapping("/")
    public String showData(Model model) {
        List<Buah> listBuah = mainService.fetchAllBuah();
        model.addAttribute("listBuah", listBuah);
        return "main/home";
    }

    @PostMapping("/upsert/{id}")
    public String upsertData(@PathVariable("id") int itemId, @ModelAttribute Buah buah) {
        if (itemId == 0) {
            mainService.saveBuah(buah);
        } else {
            mainService.updateBuah(itemId, buah);
        }
        return "redirect:/freshfruit/";
    }

    @GetMapping("/table/{id}")
    public String deleteData(@PathVariable("id") int itemId) {
        mainService.deleteBuah(itemId);
        return "redirect:/freshfruit/";
    }

    @GetMapping("/upsert/{id}")
    public String getBuahById(@PathVariable("id") int itemId, Model model) {
        if (itemId != 0) {
            Buah buah = mainService.fetchBuahById(itemId);
            model.addAttribute("buah", buah);
        } else {
            Buah buah = new Buah(0, "", 0);
            model.addAttribute("buah", buah);
        }
        return "main/input";
    }

    @GetMapping("/search")
    public String searchBuah(@RequestParam("searchTerm") String searchTerm, Model model) {
        List<Buah> searchResults = mainService.searchBuah(searchTerm);
        model.addAttribute("listBuah", searchResults);
        model.addAttribute("searchTerm", searchTerm);
        return "main/home";
    }
}
