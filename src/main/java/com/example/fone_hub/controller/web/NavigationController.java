package com.example.fone_hub.controller.web;

import com.example.fone_hub.dto.request.FilterRequest;
import com.example.fone_hub.dto.response.ProductResponse;
import com.example.fone_hub.entity.User;
import com.example.fone_hub.enums.ProductStatus;
import com.example.fone_hub.enums.Sort;
import com.example.fone_hub.service.BrandService;
import com.example.fone_hub.service.CategoryService;
import com.example.fone_hub.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/web")
@RequiredArgsConstructor
public class NavigationController {
    private final ProductService productService;
    private final BrandService brandService;
    private final CategoryService categoryService;

    @GetMapping()
    public ModelAndView homePage() {
        ModelAndView mav = new ModelAndView("web/home-page");

        mav.addObject("brands", brandService.getAllBrands());
        mav.addObject("products", productService.getAllProductsPaginated(0,10, "id", "asc", null, List.of(ProductStatus.ACTIVE)));
        mav.addObject("productSales", productService.getProductSale(1, 10));
        mav.addObject("productDates", productService.getProductNewest(1, 10));
        mav.addObject("quantityProduct", productService.getProductNewest(1, 10).size());

        User user = new User();
        mav.addObject("user", user);

        return mav;
    }

    @GetMapping("/products/{productId}")
    public ModelAndView getProductDetails(@PathVariable("productId") Long productId) {
        return new ModelAndView("web/product-detail")
                .addObject("product", productService.getProductById(productId));
    }

    @GetMapping("/filter")
    public ModelAndView searchProduct() {
        return new ModelAndView("/web/filter-product")
                .addObject("newSearch", new FilterRequest())
                .addObject("products", productService.getAllProductsPaginated(0,8, "id", "asc",null, List.of(ProductStatus.ACTIVE)))
                .addObject("categories", categoryService.getAllCategories())
                .addObject("brands", brandService.getAllBrands())
                .addObject("sorts", Sort.values());
    }

    @PostMapping("/filter")
    public ModelAndView search(@ModelAttribute("newSearch") FilterRequest request,
                               @RequestParam(defaultValue = "0") int page) {
        int size = 8;
        ModelAndView mav = new ModelAndView("/web/filter-product");
        if (request.getCategories().isEmpty()) request.setCategories(null);
        Page<ProductResponse> productPage = productService.searchProduct(request, page, size);

        mav.addObject("products", productPage.getContent());
        mav.addObject("productPage", productPage);
        mav.addObject("newSearch", request);
        mav.addObject("categories", categoryService.getAllCategories());
        mav.addObject("brands", brandService.getAllBrands());
        mav.addObject("sorts", Sort.values());
        return mav;
    }

    @PostMapping("/search-name")
    public ModelAndView searchProductByName(@RequestParam(required = false) String name) {
        FilterRequest newSearch = new FilterRequest();
        newSearch.setName(name);
        newSearch.setTypeSort(Sort.NOT_SORT);

        Page<ProductResponse> productPage = productService.searchProduct(newSearch, 0, 8);

        return new ModelAndView("/web/filter-product")
                .addObject("newSearch", new FilterRequest())
                .addObject("productPage", productPage)
                .addObject("products", productPage.getContent())
                .addObject("categories", categoryService.getAllCategories())
                .addObject("brands", brandService.getAllBrands())
                .addObject("sorts", Sort.values());
    }

    @GetMapping("/filter/{brandId}")
    public ModelAndView searchProduct(@PathVariable("brandId") Long brandId) {
        FilterRequest newSearch = FilterRequest.builder()
                .brands(List.of(brandId))
                .typeSort(Sort.NOT_SORT)
                .categories(null)
                .build();

        Page<ProductResponse> productPage = productService.searchProduct(newSearch, 0, 8);

        return new ModelAndView("/web/filter-product")
                .addObject("newSearch", new FilterRequest())
                .addObject("productPage", productPage)
                .addObject("products", productPage.getContent())
                .addObject("categories", categoryService.getAllCategories())
                .addObject("brands", brandService.getAllBrands())
                .addObject("sorts", Sort.values());
    }
}
