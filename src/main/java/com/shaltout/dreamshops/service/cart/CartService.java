package com.shaltout.dreamshops.service.cart;

import com.shaltout.dreamshops.exceptions.ResourceNotFoundException;
import com.shaltout.dreamshops.model.Cart;
import com.shaltout.dreamshops.model.User;
import com.shaltout.dreamshops.repository.CartItemRepository;
import com.shaltout.dreamshops.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public Cart getCart(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
    }

    @Transactional
    @Override
    public void clearCart(Long id) {
        cartRepository.findById(id).ifPresentOrElse(cartRepository::delete,
                () -> {
                    throw new ResourceNotFoundException("Cart not found!");
                });
    }

    @Override
    public BigDecimal getCartTotal(Long id) {
        return getCart(id).getTotalAmount();
    }

    @Override
    public Cart initializeNewCart(User user) {
        return Optional.ofNullable(getCartByUserId(user.getId()))
                .orElseGet( () -> {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                        });
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
    }
}
