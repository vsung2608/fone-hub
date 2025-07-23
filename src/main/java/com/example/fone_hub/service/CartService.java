package com.example.fone_hub.service;

import com.example.fone_hub.entity.Cart;

import java.util.List;

public interface CartService {
    // Get All Cart
    List<Cart> getCart(Long userId);

    // Find Count By UserId
    Long getCountByUserId(Long userId);

    // Add Product to Cart
    boolean addCart(Long userId, Long productId);

    // Remove Product
    void removeProductToCart(Long userId, Long productId);

    // Delete Cart When CheckOut
    void removeCartByUserId(Long userId);

    // Replace quantity product
    boolean replaceQuantityProduct(Long userId, Long productId, Long quantityReplace);
}
