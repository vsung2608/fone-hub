package com.example.fone_hub.service.impl;

import com.example.fone_hub.dto.request.FilterRequest;
import com.example.fone_hub.dto.request.ProductRequest;
import com.example.fone_hub.dto.response.ProductResponse;
import com.example.fone_hub.entity.Brand;
import com.example.fone_hub.entity.Category;
import com.example.fone_hub.entity.Product;
import com.example.fone_hub.enums.LinkedStatus;
import com.example.fone_hub.enums.ProductStatus;
import com.example.fone_hub.exception.AppException;
import com.example.fone_hub.exception.ErrorCode;
import com.example.fone_hub.mapper.ProductMapper;
import com.example.fone_hub.repository.BrandRepository;
import com.example.fone_hub.repository.CategoryRepository;
import com.example.fone_hub.repository.ProductRepository;
import com.example.fone_hub.service.ProductService;
import com.example.fone_hub.utils.ExcelProductHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public long countProduct(){
        return productRepository.countByStatusIn(List.of(ProductStatus.ACTIVE, ProductStatus.COMING_SOON));
    }

    @Override
    public Page<ProductResponse> getAllProducts(
            int page, int size,
            String sortField, String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return productRepository.findAll(pageable)
                .map(productMapper::productToProductResponse);
    }

    @Override
    public Page<ProductResponse> getAllProductsPaginated(
            int page, int size,
            String sortField, String sortDir,
            String name, List<ProductStatus> status
    ) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> entities;

        if (name != null && !name.trim().isEmpty()) {
            entities = productRepository.findByNameContainingIgnoreCaseAndStatusIn(name, status, pageable);
        } else {
            entities = productRepository.findByStatusIn(status, pageable);
        }
        return entities.map(productMapper::productToProductResponse);
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        var product = productRepository.findByIdAndStatus(productId, ProductStatus.ACTIVE)
                .orElseThrow(() -> new AppException(ErrorCode.ENTITY_NOT_EXIST));
        return productMapper.productToProductResponse(product);
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        if(productRepository.existsByNameAndStatus(productRequest.getName(), ProductStatus.ACTIVE)){
            throw new AppException(ErrorCode.ENTITY_EXIST);
        }

        Product newProduct = productMapper.productRequestToProduct(productRequest);
        newProduct.setCreateDate(LocalDate.now());

        var brand = brandRepository.findByIdAndStatus(productRequest.getBrandId(), LinkedStatus.LINKED)
                .orElseThrow(() -> new AppException(ErrorCode.ENTITY_NOT_EXIST));
        newProduct.setBrand(brand);

        var category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.ENTITY_NOT_EXIST));
        newProduct.setCategory(category);

        return productMapper.productToProductResponse(productRepository.save(newProduct));
    }

    @Override
    public ProductResponse updateProduct(Long productId, ProductRequest request) {
        if(productRepository.existsByNameAndStatus(request.getName(), ProductStatus.ACTIVE)){
            throw new  AppException(ErrorCode.ENTITY_NOT_EXIST);
        }

        Product updatedProduct = productRepository.findByIdAndStatus(productId, ProductStatus.ACTIVE)
                .orElseThrow(() -> new AppException(ErrorCode.ENTITY_NOT_EXIST));

        if(request.getName() != null && !request.getName().isEmpty()){
            updatedProduct.setName(request.getName());
        }
        if(request.getPrice() != null){
            updatedProduct.setPrice(request.getPrice());
        }
        if(request.getDiscount() != null){
            updatedProduct.setDiscount(request.getDiscount());
        }
        if(request.getConnectivity() != null && !request.getColor().isEmpty()){
            updatedProduct.setColor(request.getColor());
        }
        if(request.getQuantity() != null){
            updatedProduct.setQuantity(request.getQuantity());
        }

        updatedProduct.setUpdateDate(LocalDate.now());

        productRepository.save(updatedProduct);

        return productMapper.productToProductResponse(updatedProduct);
    }

    @Override
    public void restoreProduct(Long productId) {
        Product product = productRepository.findByIdAndStatus(productId, ProductStatus.INACTIVE)
                .orElseThrow(() -> new AppException(ErrorCode.ENTITY_NOT_EXIST));

        product.setStatus(ProductStatus.ACTIVE);

        productRepository.save(product);
    }

    @Override
    public void softDeleteProduct(Long productId) {
        Product product = productRepository.findByIdAndStatus(productId, ProductStatus.ACTIVE)
                .orElseThrow(() -> new AppException(ErrorCode.ENTITY_NOT_EXIST));

        product.setStatus(ProductStatus.INACTIVE);

        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long productId) {
    }

    @Override
    public Page<ProductResponse> searchProduct(FilterRequest request, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.filter(request, pageable);
        return products.map(productMapper::productToProductResponse);
    }

    @Override
    public List<ProductResponse> getProductSale(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return productRepository.findByStatusIn(List.of(ProductStatus.ACTIVE), pageable).stream()
                .map(productMapper::productToProductResponse)
                .sorted(Comparator.comparing(ProductResponse::discount).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getProductNewest(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return productRepository.findByStatusIn(List.of(ProductStatus.ACTIVE), pageable).stream()
                .map(productMapper::productToProductResponse)
                .sorted(Comparator.comparing(ProductResponse::createDate).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    @Override
    public void importProductsFromExcel(MultipartFile file) {
        try {
            List<Product> products = ExcelProductHelper.excelToProducts(file.getInputStream());

            for (Product product : products) {
                // Xử lý danh mục
                Category category = categoryRepository.findByName(product.getCategory().getName())
                        .orElseGet(() -> {
                            Category newCategory = new Category();
                            newCategory.setName(product.getCategory().getName());
                            return categoryRepository.save(newCategory);
                        });
                product.setCategory(category);

                // Xử lý thương hiệu
                Brand brand = brandRepository.findByName(product.getBrand().getName())
                        .orElseGet(() -> {
                            Brand newBrand = new Brand();
                            newBrand.setName(product.getBrand().getName());
                            return brandRepository.save(newBrand);
                        });
                product.setBrand(brand);
            }

            productRepository.saveAll(products);
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi lưu dữ liệu từ Excel: " + e.getMessage());
        }
    }
}

