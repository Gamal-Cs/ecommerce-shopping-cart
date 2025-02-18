package com.shaltout.dreamshops.service.cart;

import com.shaltout.dreamshops.model.Cart;
import com.shaltout.dreamshops.model.User;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);

    void clearCart(Long id);

    BigDecimal getCartTotal(Long id);

    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);
}
