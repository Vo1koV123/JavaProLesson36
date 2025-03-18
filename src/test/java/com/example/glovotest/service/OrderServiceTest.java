package com.example.glovotest.service;

import com.example.glovodb.model.Order;
import com.example.glovodb.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class OrderServiceTest {

    private static OrderRepository orderRepository;

    private static OrderService orderService;

    @BeforeEach
    void setUp() {
        orderRepository = Mockito.mock(OrderRepository.class);
        orderService = new OrderService(orderRepository);

    }

    @Test
    void getOrderByIdTest(){
        int orderId = 123;
        Order order = Order.builder()
                .id(orderId)
                .date("2023-07-12")
                .build();
        Mockito.when(orderRepository.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.of(order));
        int expected = 123;
        int actual = orderService.getOrderById(123).get().getId();
        assertEquals(expected, actual);
    }

    @Test
    void getOrderByIdNotFoundTest() {
        int orderId = 1;
        Mockito.when(orderRepository.findById(ArgumentMatchers.anyInt()))
                .thenThrow(ResponseStatusException.class);
        assertThrows(ResponseStatusException.class, () -> {
            orderService.getOrderById(orderId);
        });
    }

    @Test
    void getAllOrdersTest() {
        List<Order> orders = List.of(
                Order.builder().id(1).date("2023-07-12").build(),
                Order.builder().id(2).date("2023-07-12").build(),
                Order.builder().id(3).date("2023-07-12").build()
        );
        Mockito.when(orderRepository.findAll()).thenReturn(orders);
        List<Order> all = orderService.getAllOrders();
        int expected = 3;
        int actual = all.size();
        assertEquals(expected, actual);
        assertTrue(all.contains(
                Order.builder()
                        .id(2)
                        .date("2023-07-12")
                        .build())
        );
    }

    @Test
    void addOrderTest() {
        Order order = Order.builder()
                .id(1)
                .date("2023-07-12")
                .build();
        Mockito.when(orderRepository.save(ArgumentMatchers.any())).thenReturn(order);
        Order savedOrder = orderService.addOrder(order);
        assertEquals(order, savedOrder);
    }
}
