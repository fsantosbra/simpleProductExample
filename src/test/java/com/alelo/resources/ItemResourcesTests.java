package com.alelo.resources;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.alelo.Application;
import com.alelo.resources.dto.ItemDTO;
import com.alelo.utils.BaseDataTestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ItemResourcesTests {

    @Autowired
    private ItemResources itemResources;

    @Test
    public void getListItemsTest() throws ClientProtocolException, IOException {
        ResponseEntity<List<ItemDTO>> itemsResponse = this.itemResources.getItems();
        Assert.assertEquals(HttpStatus.OK, itemsResponse.getStatusCode());
        List<ItemDTO> listItems = itemsResponse.getBody();
        Assert.assertEquals(3, listItems.size());
    }

    @Test
    public void saveNewItemTest() throws ClientProtocolException, IOException {
        ItemDTO item = BaseDataTestUtils.createNewItem("Item for resources", 10, 1L);
        ResponseEntity<ItemDTO> itemResponse = this.itemResources.saveNewItem(item);
        Assert.assertEquals(HttpStatus.CREATED, itemResponse.getStatusCode());
        ItemDTO itemUpdated = itemResponse.getBody();
        Assert.assertEquals(Integer.valueOf(10), itemUpdated.getQuantity());
        Assert.assertNotNull(itemUpdated.getId());
        // removing item inserted
        this.itemResources.deleteItem(itemUpdated.getId());
    }

    @Test
    public void saveItemTest() throws ClientProtocolException, IOException {
        ItemDTO item = this.itemResources.getItemById(1L).getBody();
        item.setQuantity(90);
        ResponseEntity<ItemDTO> itemResponse = this.itemResources.saveItem(item);
        Assert.assertEquals(HttpStatus.OK, itemResponse.getStatusCode());
        ItemDTO itemUpdated = itemResponse.getBody();
        Assert.assertEquals(Integer.valueOf(90), itemUpdated.getQuantity());
    }

}
