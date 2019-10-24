package com.thoughtworks.star.service;

import com.thoughtworks.star.entity.Item;

import java.util.List;

public interface ItemService {
    Item create(Item item);

    List<Item> findAll();

    Item findById(String id);

    void updateItem(Item item);

    void deleteById(String id);
}
