package com.alelo.adapter.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;

import com.alelo.Application;
import com.alelo.data.Category;
import com.alelo.data.Item;
import com.alelo.data.mapper.ItemMapper;
import com.alelo.resources.adapter.ItemService;
import com.alelo.resources.dto.ItemDTO;
import com.alelo.utils.BaseDataTestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ItemServiceImplTests {

    private static final String ITEMS_DESCRIPTION = "Items description for new id";

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemMapper itemMapper;

    @Test
    @Order(1)
    public void getListItemsTest() throws Exception {
        List<ItemDTO> items = this.itemService.getListItems();
        Assert.assertEquals(4, items.size());
        Item newItem = givenNewItem();
        ItemDTO itemCreated = this.itemService.saveItem(itemMapper.dtoFromEntity(newItem));
        items = this.itemService.getListItems();
        Assert.assertEquals(5, items.size());
        // delete data created from memory
        this.itemService.deleteItem(itemCreated.getId());
    }

    @Test
    @Order(2)
    public void insertItemTest() throws Exception {
        Item newItem = new Item();
        newItem.setDescription(ITEMS_DESCRIPTION);
        newItem.setQuantity(10);
        Category category = new Category();
        category.setCategoryId(1L);
        newItem.setCategory(category);
        Item updatedItem = this.itemMapper.entityFromDto(this.itemService.saveItem(itemMapper.dtoFromEntity(newItem)));
        Assert.assertEquals(newItem.getDescription(), updatedItem.getDescription());
        Assert.assertEquals(newItem.getQuantity(), updatedItem.getQuantity());
        Item foundItem = this.itemMapper.entityFromDto(this.itemService.getItemById(updatedItem.getId()));
        Assert.assertEquals(updatedItem.getId(), foundItem.getId());
        // delete the data created
        this.itemService.deleteItem(updatedItem.getId());
    }

    @Test
    @Order(3)
    public void updateItemTest() throws Exception {
        Item foundItem = this.itemMapper.entityFromDto(this.itemService.getItemById(2L));
        foundItem.setQuantity(5);
        Item updatedItem = this.itemMapper.entityFromDto(this.itemService.saveItem(itemMapper.dtoFromEntity(foundItem)));
        Assert.assertEquals(Integer.valueOf(5), updatedItem.getQuantity());
    }

    @Test
    @Order(4)
    public void deleteItem() throws Exception {
        List<ItemDTO> items = this.itemService.getListItems();
        Assert.assertEquals(4, items.size());
        this.itemService.deleteItem(1L);
        items = this.itemService.getListItems();
        Assert.assertEquals(3, items.size());
        ItemDTO notFound = this.itemService.getItemById(1L);
        Assert.assertNull(notFound);
        this.itemService.saveItem(BaseDataTestUtils.createNewItem("Replaced Item", 10, 1L));
    }

    private Item givenNewItem() {
        Item newItem = new Item();
        newItem.setDescription(ITEMS_DESCRIPTION);
        newItem.setQuantity(10);
        Category category = new Category();
        category.setCategoryId(1L);
        newItem.setCategory(category);
        return newItem;
    }

}
