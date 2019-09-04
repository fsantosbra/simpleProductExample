package com.alelo.adapter.impl;

import java.util.List;

import javax.activation.UnsupportedDataTypeException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alelo.Application;
import com.alelo.data.Category;
import com.alelo.data.Item;
import com.alelo.data.mapper.ItemMapper;
import com.alelo.resources.adapter.ItemService;
import com.alelo.resources.dto.ItemDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ItemServiceImplTests {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemMapper itemMapper;

    @Test
    public void getListItemsTest() throws Exception {
        List<ItemDTO> items = this.itemService.getListItems();
        Assert.assertEquals(3, items.size());
        Item newItem = new Item();
        newItem.setDescription("Items description for id 6");
        newItem.setQuantity(10);
        Category category = new Category();
        category.setCategoryId(1L);
        newItem.setCategory(category);
        this.itemService.saveItem(itemMapper.dtoFromEntity(newItem));
        items = this.itemService.getListItems();
        Assert.assertEquals(4, items.size());
    }

    @Test
    public void insertItemTest() throws Exception {
        Item newItem = new Item();
        newItem.setDescription("Items description for id 6");
        newItem.setQuantity(10);
        Category category = new Category();
        category.setCategoryId(1L);
        newItem.setCategory(category);
        Item updatedItem = this.itemMapper.entityFromDto(this.itemService.saveItem(itemMapper.dtoFromEntity(newItem)));
        Assert.assertEquals(newItem.getDescription(), updatedItem.getDescription());
        Assert.assertEquals(newItem.getQuantity(), updatedItem.getQuantity());
        Item foundItem = this.itemMapper.entityFromDto(this.itemService.getItemById(updatedItem.getId()));
        Assert.assertEquals(updatedItem.getId(), foundItem.getId());
    }

    @Test
    public void updateItemTest() throws Exception {
        Item foundItem = this.itemMapper.entityFromDto(this.itemService.getItemById(1L));
        foundItem.setQuantity(5);
        Item updatedItem = this.itemMapper.entityFromDto(this.itemService.saveItem(itemMapper.dtoFromEntity(foundItem)));
        Assert.assertEquals(Integer.valueOf(5), updatedItem.getQuantity());
    }

    @Test
    public void deleteItem() {
        try {
            this.itemService.deleteItem("1");
        }
        catch (UnsupportedDataTypeException e) {
            e.printStackTrace();
        }
    }

}
