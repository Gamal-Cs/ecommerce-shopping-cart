package com.shaltout.dreamshops.service.order;

import com.shaltout.dreamshops.dto.OrderDto;
import com.shaltout.dreamshops.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);
    List<OrderDto> getUserOrders(Long userId);

    OrderDto convertToDto(Order order);
}
