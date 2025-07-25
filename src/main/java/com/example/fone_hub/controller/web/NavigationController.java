package com.example.fone_hub.controller.web;

import com.example.fone_hub.entity.User;
import com.example.fone_hub.enums.ProductStatus;
import com.example.fone_hub.service.BrandService;
import com.example.fone_hub.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/web")
@RequiredArgsConstructor
public class NavigationController {
    private final ProductService productService;
    private final BrandService brandService;

    @GetMapping()
    public ModelAndView homePage() {
        ModelAndView mav = new ModelAndView("web/home-page");

        mav.addObject("brands", brandService.getAllBrands());
        mav.addObject("products", productService.getAllProductsPaginated(0,10, "id", "asc", null, ProductStatus.ACTIVE));
        mav.addObject("productSales", productService.getProductSale(1, 10));
        mav.addObject("productDates", productService.getProductNewest(1, 10));
        mav.addObject("quantityProduct", productService.getProductNewest(1, 10).size());

        User user = new User();
        mav.addObject("user", user);

        return mav;
    }

    @GetMapping("/products/{productId}")
    public ModelAndView getProductDetails(@PathVariable("productId") Long productId) {
        ModelAndView mav = new ModelAndView("web/product-detail")
                .addObject("product", productService.getProductById(productId));
        return mav;
    }
}
