package com.shaltout.dreamshops.service.cart;

import com.shaltout.dreamshops.exceptions.ResourceNotFoundException;
import com.shaltout.dreamshops.model.Cart;
import com.shaltout.dreamshops.model.CartItem;
import com.shaltout.dreamshops.model.Product;
import com.shaltout.dreamshops.repository.CartItemRepository;
import com.shaltout.dreamshops.repository.CartRepository;
import com.shaltout.dreamshops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemService  implements ICartItemService{
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final IProductService productService;
    private final ICartService cartService;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);

        CartItem cartItem = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setCart(cart);
                    newItem.setProduct(product);
                    newItem.setUnitPrice(product.getPrice());
                    cart.addItem(newItem); // ✅ Ensure new item is added to cart
                    return newItem;
                });

        // ✅ Increase quantity if item already exists
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cart.addItem(cartItem);
        cartRepository.save(cart); // No need to save cartItem separately
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        CartItem itemToRemove = getCartItem(cartId, productId);
        cart.removeItem(itemToRemove);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);

        cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    cart.setTotalAmount(item.getTotalPrice());
                });
        cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        return cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));
    }
}
