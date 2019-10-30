package com.thoughtworks.star.api;

import com.thoughtworks.star.configuration.security.APISecureRolePrivilege;
import com.thoughtworks.star.entity.Order;
import com.thoughtworks.star.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(@RequestBody List<String> itemIds) {
        return orderService.create(itemIds);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Secured(APISecureRolePrivilege.RETRIEVE_ORDER)
    public List<Order> findOrders() {
        return orderService.findCurrentUserOrder();
    }

    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<List<Order>> searchOrders(@RequestParam(defaultValue = "") String keyword) {
        return ResponseEntity.ok(orderService.searchOrders(keyword));
    }

}