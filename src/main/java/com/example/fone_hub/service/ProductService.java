package com.example.fone_hub.service;

import com.example.fone_hub.dto.request.FilterRequest;
import com.example.fone_hub.dto.request.ProductRequest;
import com.example.fone_hub.dto.response.ProductResponse;
import com.example.fone_hub.enums.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ProductService {
    long countProduct();

    Page<ProductResponse> getAllProducts(
            int page, int size,
            String sortField, String sortDir
    );

    Page<ProductResponse> getAllProductsPaginated(
            int page, int size,
            String sortField, String sortDir,
            String name, List<ProductStatus> status
    );

    ProductResponse getProductById(Long productId);

    ProductResponse createProduct(ProductRequest productRequest);

    ProductResponse updateProduct(Long productId, ProductRequest productRequest);

    void softDeleteProduct(Long productId);

    void restoreProduct(Long productId);

    void deleteProduct(Long productId);

    Page<ProductResponse> searchProduct(FilterRequest request, int page, int size);

    List<ProductResponse> getProductSale(int page, int size);

    List<ProductResponse> getProductNewest(int page, int size);

    void importProductsFromExcel(MultipartFile file);
}
