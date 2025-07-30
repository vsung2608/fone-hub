package com.example.fone_hub.controller.admin;

import com.example.fone_hub.dto.request.BrandRequest;
import com.example.fone_hub.dto.response.BrandResponse;
import com.example.fone_hub.service.BrandService;
import com.example.fone_hub.service.ImageProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/brand")
public class BrandManagementController {
    private final BrandService brandService;
    private final ImageProductService imageProductService;

    // View all brand
    @GetMapping
    public ModelAndView getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String name

    ) {

        List<BrandResponse> brands = brandService.getAllBrands();
        ModelAndView mav = new ModelAndView("/admin/brand/manage");

        mav.addObject("brands", brands);

        mav.addObject("sortField", sortField);
        mav.addObject("sortDir", sortDir);
        mav.addObject("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        mav.addObject("newBrand",  new BrandRequest());
        mav.addObject("updateBrand",  new BrandRequest());

        return mav;
    }

    @PostMapping("/create")
    public ModelAndView createBrand(
            @ModelAttribute("newBrand") BrandRequest request,
            @RequestParam("file") MultipartFile file
            ,Model model
    ){
        try {
            BrandResponse brand = brandService.createBrand(request, file);
            model.addAttribute("notification", "Success");
        } catch (Exception e) {
            model.addAttribute("notification", "Fail");
            model.addAttribute("message", e.getMessage());
        }
        return new ModelAndView("/admin/notification");
    }

    @PostMapping("/update/{brandId}")
    public ModelAndView updateBrand(
            @PathVariable Long brandId,
            @ModelAttribute("updateBrand") BrandRequest request,
            @RequestParam("updateFile") MultipartFile file,
            Model model
    ){
        try{
            brandService.updateBrand(brandId, request, file);
            model.addAttribute("notification", "Success");
        } catch (Exception e) {
            model.addAttribute("notification", "Fail");
            model.addAttribute("message", e.getMessage());
        }
        return new ModelAndView("/admin/notification");
    }

    @PostMapping("/restore/{brandId}")
    public ModelAndView restoreBrand(@PathVariable Long brandId, Model model){
        try{
            brandService.restoreBrand(brandId);
            model.addAttribute("notification", "Success");
        } catch (Exception e) {
            model.addAttribute("notification", "Fail");
            model.addAttribute("message", e.getMessage());
        }
        return new ModelAndView("/admin/notification");
    }

    @PutMapping("/delete/{brandId}")
    public ModelAndView softDeleteBrand(@PathVariable Long brandId, Model model){
        try{
            brandService.softDeleteBrand(brandId);
            model.addAttribute("notification", "Success");
        } catch (Exception e) {
            model.addAttribute("notification", "Fail");
            model.addAttribute("message", e.getMessage());
        }
        return new ModelAndView("/admin/notification");
    }

    @DeleteMapping("/delete/{brandId}")
    public ModelAndView deleteBrand(@PathVariable Long brandId, Model model){
        try{
            brandService.deleteBrand(brandId);
            model.addAttribute("notification", "Success");
        } catch (Exception e) {
            model.addAttribute("notification", "Fail");
            model.addAttribute("message", e.getMessage());
        }
        return new ModelAndView("/admin/notification");
    }

    @PostMapping("/add-image/{brandId}")
    public String addImage(@PathVariable Long brandId,
                           @RequestParam("file") MultipartFile file){
        if(file.isEmpty()){
            String imageLink = imageProductService.createImage(file);
            brandService.updateImage(brandId, imageLink);
            return "redirect:/admin/brand/" + brandId;
        }
        return "redirect:/admin/admin";
    }

    @DeleteMapping("/image/{brandId}")
    public String deleteImageBrand(@PathVariable Long brandId) {
        brandService.deleteImage(brandId);
        return "redirect:/admin/brand/" + brandId;
    }
}
