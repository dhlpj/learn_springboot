package com.thoughtworks.star.api;

import com.thoughtworks.star.entity.Item;
import com.thoughtworks.star.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    // TODO students implement
    @Autowired
    private ItemService itemService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Item item) {
        itemService.create(item);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // TODO students implement
    @GetMapping
    public ResponseEntity<List<Item>> list() {
        return ResponseEntity.ok(itemService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> findById(@PathVariable("id") String id){
        return ResponseEntity.ok(itemService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateItem(@PathVariable("id") String id,@RequestBody Item item){
        Item itemInDB = itemService.findById(id);
        if (itemInDB==null){
            return ResponseEntity.notFound().build();
        }
        itemInDB.setName(item.getName());
        itemInDB.setPrice(item.getPrice());
        itemService.updateItem(itemInDB);
        return ResponseEntity.ok(null);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePartOfItem(@PathVariable("id") String id,@RequestBody Item item){
        Item itemInDB = itemService.findById(id);
        if (itemInDB==null){
            return ResponseEntity.notFound().build();
        }
        if (item.getPrice()!=0){
            itemInDB.setPrice(item.getPrice());
        }
        if (item.getName()!=null){
            itemInDB.setName(item.getName());
        }
        itemService.updateItem(itemInDB);
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable("id") String id){
        itemService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
