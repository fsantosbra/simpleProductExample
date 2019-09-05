package com.alelo.adapter.impl;

import java.util.ArrayList;
import java.util.List;

import javax.activation.UnsupportedDataTypeException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.alelo.Application;
import com.alelo.data.Category;
import com.alelo.data.Item;
import com.alelo.data.mapper.ItemMapper;
import com.alelo.resources.adapter.ItemService;
import com.alelo.resources.dto.ItemDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ItemServiceImplTests {

    private static final String ITEMS_DESCRIPTION = "Items description for new id";

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemMapper itemMapper;

    @Test
    @Transactional
    public void getListItemsTest() throws Exception {
        List<ItemDTO> items = this.itemService.getListItems();
        Assert.assertEquals(4, items.size());
        Item newItem = givenNewItem();
        this.itemService.saveItem(itemMapper.dtoFromEntity(newItem));
        items = this.itemService.getListItems();
        Assert.assertEquals(5, items.size());
    }

    @Test
    @Transactional
    public void insertItemTest() throws Exception {
        Item newItem = new Item();
        newItem.setDescription(ITEMS_DESCRIPTION);
        newItem.setQuantity(10);
        Category category = new Category();
        category.setId(1L);
        newItem.setCategory(category);
        Item updatedItem = this.itemMapper.entityFromDto(this.itemService.saveItem(itemMapper.dtoFromEntity(newItem)));
        Assert.assertEquals(newItem.getDescription(), updatedItem.getDescription());
        Assert.assertEquals(newItem.getQuantity(), updatedItem.getQuantity());
        Item foundItem = this.itemMapper.entityFromDto(this.itemService.getItemById(updatedItem.getId()));
        Assert.assertEquals(updatedItem.getId(), foundItem.getId());
    }

    @Test
    @Transactional
    public void insertListItemTest() throws Exception {
        Item newItem1 = givenNewItem();
        Item newItem2 = givenNewItem();
        List<Item> items = new ArrayList<Item>();
        items.add(newItem1);
        items.add(newItem2);
        List<Item> updatedItems = this.itemMapper.entitiesFromDtos(this.itemService.saveListItem(itemMapper.dtosFromEntities(items)));
        for (Item updatedItem : updatedItems) {
            Assert.assertEquals(updatedItem.getDescription(), updatedItem.getDescription());
            Assert.assertEquals(updatedItem.getQuantity(), updatedItem.getQuantity());
            Assert.assertNotNull(updatedItem.getId());
        }
    }

    @Test
    @Transactional
    public void updateItemTest() throws Exception {
        Item foundItem = this.itemMapper.entityFromDto(this.itemService.getItemById(2L));
        foundItem.setQuantity(5);
        Item updatedItem = this.itemMapper.entityFromDto(this.itemService.saveItem(itemMapper.dtoFromEntity(foundItem)));
        Assert.assertEquals(Integer.valueOf(5), updatedItem.getQuantity());
    }

    @Test
    @Transactional
    public void deleteItem() throws Exception {
        List<ItemDTO> items = this.itemService.getListItems();
        Assert.assertEquals(4, items.size());
        this.itemService.deleteItem(1L);
        items = this.itemService.getListItems();
        Assert.assertEquals(3, items.size());
        ItemDTO notFound = this.itemService.getItemById(1L);
        Assert.assertNull(notFound);
    }

    @Test
    @Transactional
    public void getItemsByCategoryId() throws Exception {
        List<ItemDTO> items = this.itemService.getItemsByCategoryId(1L);
        Assert.assertEquals(3, items.size());
        Item newItem = givenNewItem();
        this.itemService.saveItem(itemMapper.dtoFromEntity(newItem));
        items = this.itemService.getItemsByCategoryId(1L);
        Assert.assertEquals(4, items.size());
    }

    @Test(expected = UnsupportedDataTypeException.class)
    public void deleteItemException() throws UnsupportedDataTypeException {
        this.itemService.deleteItem(null);
    }

    @Test(expected = UnsupportedDataTypeException.class)
    public void getItemByIdException() throws Exception {
        this.itemService.getItemById(null);
    }

    @Test
    @Transactional
    public void getItemByIdNotFound() throws Exception {
        ItemDTO item = this.itemService.getItemById(99L);
        Assert.assertNull(item);
    }

    private Item givenNewItem() {
        Item newItem = new Item();
        newItem.setDescription(ITEMS_DESCRIPTION);
        newItem.setQuantity(10);
        Category category = new Category();
        category.setId(1L);
        newItem.setCategory(category);
        return newItem;
    }

}
