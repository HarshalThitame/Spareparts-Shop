package com.sparesparts.controller.customer;

import com.sparesparts.config.mail.EmailData;
import com.sparesparts.config.mail.EmailService;
import com.sparesparts.controller.customer.CustomerOrderController;
import com.sparesparts.entity.Order;
import com.sparesparts.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerOrderControllerTest {

    @Mock
    private OrderService orderService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private CustomerOrderController customerOrderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetOrdersByUserId() {
        Long userId = 1L;
        List<Order> mockOrders = Arrays.asList(new Order(), new Order());
        when(orderService.getOrdersByUserId(userId)).thenReturn(mockOrders);

        ResponseEntity<List<Order>> response = customerOrderController.getOrdersByUserId(userId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockOrders, response.getBody());
        verify(orderService, times(1)).getOrdersByUserId(userId);
    }

    @Test
    void testGetAllCustomerOrders() {
        List<Order> mockOrders = Arrays.asList(new Order(), new Order());
        when(orderService.getAllCustomerOrders()).thenReturn(mockOrders);

        ResponseEntity<List<Order>> response = customerOrderController.getAllCustomerOrders();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockOrders, response.getBody());
        verify(orderService, times(1)).getAllCustomerOrders();
    }

    @Test
    void testGetCustomerOrderById() {
        Long orderId = 1L;
        Order mockOrder = new Order();
        when(orderService.getCustomerOrderById(orderId)).thenReturn(mockOrder);

        ResponseEntity<Order> response = customerOrderController.getCustomerOrderById(orderId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockOrder, response.getBody());
        verify(orderService, times(1)).getCustomerOrderById(orderId);
    }

    @Test
    void testPlaceOrder() {
        Order order = new Order();
        Order placedOrder = new Order();
        when(orderService.createOrder(order)).thenReturn(placedOrder);

        ResponseEntity<Order> response = customerOrderController.placeOrder(order);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(placedOrder, response.getBody());
        verify(orderService, times(1)).createOrder(order);
    }

    @Test
    void testCancelOrder() {
        Long orderId = 1L;

        ResponseEntity<?> response = customerOrderController.cancelOrder(orderId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Order canceled successfully", response.getBody());
        verify(orderService, times(1)).cancelOrder(orderId);
    }
}