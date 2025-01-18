package com.sparesparts.controller.customer;

import com.sparesparts.controller.customer.CustomerOrderItemController;
import com.sparesparts.entity.OrderItem;
import com.sparesparts.service.OrderItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CustomerOrderItemControllerTest {

    @Mock
    private OrderItemService orderItemService;

    @InjectMocks
    private CustomerOrderItemController customerOrderItemController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetOrderItemsByOrderId() {
        Long orderId = 1L;
        List<OrderItem> mockOrderItems = Arrays.asList(new OrderItem(), new OrderItem());
        when(orderItemService.getOrderItemsByOrderId(orderId)).thenReturn(mockOrderItems);

        ResponseEntity<List<OrderItem>> response = customerOrderItemController.getOrderItemsByOrderId(orderId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockOrderItems, response.getBody());
        verify(orderItemService, times(1)).getOrderItemsByOrderId(orderId);
    }

    @Test
    void testGetOrderItemById() {
        Long itemId = 1L;
        OrderItem mockOrderItem = new OrderItem();
        when(orderItemService.getOrderItemById(itemId)).thenReturn(mockOrderItem);

        ResponseEntity<OrderItem> response = customerOrderItemController.getOrderItemById(itemId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockOrderItem, response.getBody());
        verify(orderItemService, times(1)).getOrderItemById(itemId);
    }
}