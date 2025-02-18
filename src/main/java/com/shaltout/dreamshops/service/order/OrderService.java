package com.shaltout.dreamshops.service.order;

import com.shaltout.dreamshops.dto.OrderDto;
import com.shaltout.dreamshops.enums.OrderStatus;
import com.shaltout.dreamshops.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshops.model.*;
import com.shaltout.dreamshops.model.Cart;
import com.shaltout.dreamshops.model.Order;
import com.shaltout.dreamshops.model.OrderItem;
import com.shaltout.dreamshops.model.Product;
import com.shaltout.dreamshops.repository.OrderRepository;
import com.shaltout.dreamshops.repository.ProductRepository;
import com.shaltout.dreamshops.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = buildOrder(cart);
        Set<OrderItem> orderItems = buildOrderItems(order, cart);

        order.setOrderItems(orderItems);
        order.setTotalAmount(calculateTotal(orderItems));

        orderRepository.save(order);
        cartService.clearCart(cart.getId());
        return order;
    }

    private Order buildOrder(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private Set<OrderItem> buildOrderItems(Order order, Cart cart) {
        return cart.getItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepository.save(product);

            return new OrderItem(order, product, cartItem.getQuantity(), cartItem.getUnitPrice());
        }).collect(Collectors.toSet());
    }

    private BigDecimal calculateTotal(Set<OrderItem> orderItems) {
        return orderItems.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public OrderDto convertToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }
}
