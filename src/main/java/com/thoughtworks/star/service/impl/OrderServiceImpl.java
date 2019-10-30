package com.thoughtworks.star.service.impl;

import com.thoughtworks.star.entity.Item;
import com.thoughtworks.star.entity.Order;
import com.thoughtworks.star.repository.ItemRepository;
import com.thoughtworks.star.repository.OrderRepository;
import com.thoughtworks.star.service.OrderService;
import com.thoughtworks.star.service.UserService;
import com.thoughtworks.star.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public Order create(List<String> itemIds) {
        // TODO students implement
        List<Item> items = itemRepository.findAll(itemIds);
        double totalPrice = items.stream().mapToDouble(Item::getPrice).sum();
        Order order = Order.builder().id(StringUtils.randomUUID()).price(totalPrice)
                .userId(userService.findPrincipal().getId()).build();
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findCurrentUserOrder() {
        return orderRepository.findByUserId(userService.findPrincipal().getId());
    }

    @Override
    public List<Order> searchOrders(String keyword) {
        Optional<List<Order>> optional = Optional.ofNullable(orderRepository.findAll());
        return optional.orElse(new ArrayList<Order>())
                .stream()
                .filter(order -> order.getDescription().contains(keyword))
                .sorted(Comparator.comparingDouble(Order::getPrice))
                .collect(Collectors.toList());
    }
}