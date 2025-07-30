package com.example.fone_hub.controller.admin;

import com.example.fone_hub.dto.request.ImageRequest;
import com.example.fone_hub.dto.request.ProductRequest;
import com.example.fone_hub.dto.response.ProductResponse;
import com.example.fone_hub.enums.ProductStatus;
import com.example.fone_hub.service.BrandService;
import com.example.fone_hub.service.CategoryService;
import com.example.fone_hub.service.ImageProductService;
import com.example.fone_hub.service.ProductService;
import com.example.fone_hub.utils.ExcelProductHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class ProductManagementController {
    private final ProductService productService;
    private final ImageProductService imageProductService;
    private final CategoryService categoryService;
    private final BrandService brandService;

    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody ProductRequest product) {
        productService.createProduct(product);
        return ResponseEntity.ok("Product created");
    }

    @GetMapping
    public ModelAndView getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String name

    ) {

        Page<ProductResponse> allProduct = productService.getAllProducts(page, size, sortField, sortDir);
        Page<ProductResponse> inactiveProduct = productService
                .getAllProductsPaginated(page, size, sortField, sortDir, name, List.of(ProductStatus.INACTIVE, ProductStatus.DISCONTINUED));
        Page<ProductResponse> deletedProduct = productService.getAllProductsPaginated(page, size, sortField, sortDir, name, List.of(ProductStatus.DELETED));
        ModelAndView mav = new ModelAndView("/admin/product/manage");

        mav.addObject("productPage", allProduct);
        mav.addObject("products", allProduct.getContent());
        mav.addObject("productInactivePage", inactiveProduct);
        mav.addObject("inactiveProducts", inactiveProduct.getContent());
        mav.addObject("productDeletedPage", deletedProduct);
        mav.addObject("deletedProducts", deletedProduct.getContent());

        mav.addObject("sortField", sortField);
        mav.addObject("sortDir", sortDir);
        mav.addObject("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        mav.addObject("newProduct", new ProductRequest());
        mav.addObject("categories", categoryService.getAllCategories());
        mav.addObject("brands", brandService.getAllBrands());

        return mav;
    }

    // View product by ID
    @GetMapping("/{productId}")
    public ModelAndView getProduct(@PathVariable Long productId){
        ProductResponse product = productService.getProductById(productId);
        return new ModelAndView("/admin/product/view")
                .addObject("product", product)
                .addObject("images", imageProductService.getImageByProductId(productId))
                .addObject("brand", product.brand())
                .addObject("category", product.category())
                .addObject("newImage", new ImageRequest());
    }

    @PostMapping("/create")
    public ModelAndView createProduct(@ModelAttribute("newProduct") ProductRequest request,
                                      @RequestParam("files") List<MultipartFile> files
            ,Model model){
        try {
            ProductResponse product = productService.createProduct(request);
            imageProductService.createImage(files, product.id());
            model.addAttribute("notification", "Success");
        } catch (Exception e) {
            model.addAttribute("notification", "Fail");
            model.addAttribute("message", e.getMessage());
        }
        return new ModelAndView("/admin/notification");
    }

    @GetMapping("/update/{productId}")
    public ModelAndView updateProduct(@PathVariable Long productId){
        return new ModelAndView("/admin/product/update")
                .addObject("product", productService.getProductById(productId))
                .addObject("updateProduct", new ProductRequest())
                .addObject("categories", categoryService.getAllCategories())
                .addObject("brands", brandService.getAllBrands());
    }

    @PostMapping("/update/{productId}")
    public ModelAndView updateProduct(@PathVariable Long productId, @ModelAttribute("updateProduct") ProductRequest request, Model model){
        try{
            productService.updateProduct(productId, request);
            model.addAttribute("notification", "Success");
        } catch (Exception e) {
            model.addAttribute("notification", "Fail");
            model.addAttribute("message", e.getMessage());
        }
        return new ModelAndView("/admin/notification");
    }

    @PostMapping("/restore/{productId}")
    public ModelAndView restoreProduct(@PathVariable Long productId, Model model){
        try{
            productService.restoreProduct(productId);
            model.addAttribute("notification", "Success");
        } catch (Exception e) {
            model.addAttribute("notification", "Fail");
            model.addAttribute("message", e.getMessage());
        }
        return new ModelAndView("/admin/notification");
    }

    @PutMapping("/delete/{productId}")
    public ModelAndView softDeleteProduct(@PathVariable Long productId, Model model){
        try{
            productService.softDeleteProduct(productId);
            model.addAttribute("notification", "Success");
        } catch (Exception e) {
            model.addAttribute("notification", "Fail");
            model.addAttribute("message", e.getMessage());
        }
        return new ModelAndView("/admin/notification");
    }

    @DeleteMapping("/delete/{productId}")
    public ModelAndView deleteProduct(@PathVariable Long productId, Model model){
        try{
            productService.deleteProduct(productId);
            model.addAttribute("notification", "Success");
        } catch (Exception e) {
            model.addAttribute("notification", "Fail");
            model.addAttribute("message", e.getMessage());
        }
        return new ModelAndView("/admin/notification");
    }

    @PostMapping("/add-image")
    public void addProductImage(@RequestParam("image") MultipartFile image, @RequestParam("id") Long productId) {
        imageProductService.createImage(image, productId);
    }

    @PostMapping("/add/upload")
    public ResponseEntity<?> uploadExcelFile(@RequestParam("file") MultipartFile file) {
        if (!ExcelProductHelper.hasExcelFormat(file)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vui lòng tải lên file Excel hợp lệ (.xlsx)");
        }

        try {
            productService.importProductsFromExcel(file);
            return ResponseEntity.ok("Tải lên thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Tải lên thất bại: " + e.getMessage());
        }
    }
}
