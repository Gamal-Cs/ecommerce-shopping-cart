package com.shaltout.dreamshops.controller;

import com.shaltout.dreamshops.dto.OrderDto;
import com.shaltout.dreamshops.exceptions.ResourceNotFoundException;
import com.shaltout.dreamshops.model.Order;
import com.shaltout.dreamshops.response.ApiResponse;
import com.shaltout.dreamshops.service.order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final IOrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId) {
        try {
            Order order = orderService.placeOrder(userId);
            OrderDto orderDto = orderService.convertToDto(order);
            return buildResponse("Order Placed Successfully!", orderDto, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            return buildResponse("Order Placement Failed!", e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return buildResponse("Invalid Request!", e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return buildResponse("An Unexpected Error Occurred!", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId) {
        try {
            OrderDto order = orderService.getOrder(orderId);
            return buildResponse("Order Found!", order, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return buildResponse("Order Not Found!", e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId) {
        try {
            List<OrderDto> orders = orderService.getUserOrders(userId);
            return buildResponse("User Orders Found!", orders, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return buildResponse("No Orders Found for User!", e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    private ResponseEntity<ApiResponse> buildResponse(String message, Object data, HttpStatus status) {
        return ResponseEntity.status(status).body(new ApiResponse(message, data));
    }
}
