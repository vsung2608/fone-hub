package com.example.fone_hub.controller.admin;

import com.example.fone_hub.dto.request.CategoryRequest;
import com.example.fone_hub.dto.response.CategoryResponse;
import com.example.fone_hub.service.CategoryService;
import com.example.fone_hub.service.ImageProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/category")
public class CategoryManagementController {
    private final CategoryService categoryService;
    private final ImageProductService imageProductService;

    @GetMapping
    public ModelAndView getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String name

    ) {

        List<CategoryResponse> categories = categoryService.getAllCategories();
        ModelAndView mav = new ModelAndView("/admin/category/manage");

        mav.addObject("categories", categories);

        mav.addObject("sortField", sortField);
        mav.addObject("sortDir", sortDir);
        mav.addObject("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        mav.addObject("newCategory",  new CategoryRequest());
        mav.addObject("updateCategory",  new CategoryRequest());

        return mav;
    }

    @PostMapping("/create")
    public ModelAndView createCategory(@ModelAttribute("newCategory") CategoryRequest request
            ,Model model){
        try {
            CategoryResponse category = categoryService.createCategory(request);
            model.addAttribute("notification", "Success");
        } catch (Exception e) {
            model.addAttribute("notification", "Fail");
            model.addAttribute("message", e.getMessage());
        }
        return new ModelAndView("/admin/notification");
    }

    @PostMapping("/update/{categoryId}")
    public ModelAndView updateCategory(
            @PathVariable Long categoryId,
            @ModelAttribute("updateCategory") CategoryRequest request,
            Model model){
        try{
            categoryService.updateCategory(categoryId, request);
            model.addAttribute("notification", "Success");
        } catch (Exception e) {
            model.addAttribute("notification", "Fail");
            model.addAttribute("message", e.getMessage());
        }
        return new ModelAndView("/admin/notification");
    }

    @DeleteMapping("/delete/{categoryId}")
    public ModelAndView deleteCategory(@PathVariable Long categoryId, Model model){
        try{
            categoryService.deleteCategory(categoryId);
            model.addAttribute("notification", "Success");
        } catch (Exception e) {
            model.addAttribute("notification", "Fail");
            model.addAttribute("message", e.getMessage());
        }
        return new ModelAndView("/admin/notification");
    }
}
