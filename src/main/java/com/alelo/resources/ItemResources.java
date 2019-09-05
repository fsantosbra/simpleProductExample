package com.alelo.resources;

import java.net.URI;
import java.util.List;

import javax.activation.UnsupportedDataTypeException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.alelo.resources.adapter.ItemService;
import com.alelo.resources.dto.ItemDTO;

@Controller
@RequestMapping("/api/v2/items")
public class ItemResources {

    private final ItemService itemService;

    @Autowired
    public ItemResources(ItemService itemService) {
        this.itemService = itemService;
    }

    @PutMapping(path = "/save")
    public ResponseEntity<ItemDTO> saveItem(@RequestBody ItemDTO itemDto) {
        ItemDTO updatedItem;
        try {
            updatedItem = this.itemService.saveItem(itemDto);
            return new ResponseEntity<>(updatedItem, HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(path = "/")
    public ResponseEntity<List<ItemDTO>> getItems() {
        try {
            return new ResponseEntity<>(this.itemService.getListItems(), HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/save")
    public ResponseEntity<ItemDTO> saveNewItem(@RequestBody ItemDTO itemDto) {
        try {
            ItemDTO newItem = this.itemService.saveItem(itemDto);
            URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v2/items/{id}").buildAndExpand(newItem.getId()).toUri();
            return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, location.toString()).body(newItem);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(this.itemService.getItemById(id), HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long id) {
        try {
            this.itemService.deleteItem(id);
        }
        catch (UnsupportedDataTypeException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping(path = "/list/category/{id}")
    public ResponseEntity<List<ItemDTO>> getItemsByCategoryId(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(this.itemService.getItemsByCategoryId(id), HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
