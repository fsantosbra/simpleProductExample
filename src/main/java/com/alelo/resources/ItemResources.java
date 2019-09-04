package com.alelo.resources;

import java.util.List;

import javax.activation.UnsupportedDataTypeException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.alelo.resources.adapter.ItemService;
import com.alelo.resources.dto.ItemDTO;

@Controller
@RequestMapping("/api/v2")
public class ItemResources {

    @Autowired
    private ItemService itemService;

    @PostMapping(path = "/save")
    public ResponseEntity<ItemDTO> saveItem(@RequestBody ItemDTO itemDto, HttpServletResponse response) {
        ItemDTO updatedItem = this.itemService.saveItem(itemDto);
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);

    }

    @GetMapping(path = "/")
    public ResponseEntity<List<ItemDTO>> getItems() {
        return new ResponseEntity<>(this.itemService.getListItems(), HttpStatus.OK);
    }

    @PutMapping(path = "/save")
    public ResponseEntity<String> saveNewItem(@RequestBody String screenFilterDto) {
        return new ResponseEntity<>("success", HttpStatus.OK);

    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable String id) {
        try {
            this.itemService.deleteItem(id);
        }
        catch (UnsupportedDataTypeException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}
