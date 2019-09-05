package com.alelo.resources;

import java.util.ArrayList;

import javax.activation.UnsupportedDataTypeException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.alelo.Application;
import com.alelo.resources.adapter.ItemService;
import com.alelo.resources.dto.CategoryDTO;
import com.alelo.resources.dto.ItemDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ItemResourcesTests {

    @Mock
    private ItemService mockItemService;

    private ItemResources itemResources;
    private ObjectMapper objectMapper;
    private MockMvc mvc;

    @Before
    public void setup() {
        itemResources = new ItemResources(mockItemService);
        objectMapper = new ObjectMapper();
        mvc = MockMvcBuilders.standaloneSetup(itemResources).build();
    }

    @Test
    public void getListItemsTest() throws Exception {
        String path = "/api/v2/items/";
        Mockito.when(mockItemService.getListItems()).thenReturn(new ArrayList<ItemDTO>());
        mvc.perform(MockMvcRequestBuilders.get(path).secure(false).accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(mockItemService, Mockito.times(1)).getListItems();
    }

    @Test
    @Transactional
    public void saveNewItemTest() throws Exception {
        ItemDTO item = createNewItem("Item for resources", 10, 1L);
        String json = objectMapper.writeValueAsString(item);
        String path = "/api/v2/items/save";
        Mockito.when(mockItemService.saveItem(Mockito.any(ItemDTO.class))).thenReturn(item);
        mvc.perform(MockMvcRequestBuilders.post(path).secure(false).content(json).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(mockItemService, Mockito.times(1)).saveItem(Mockito.any(ItemDTO.class));
    }

    @Test
    @Transactional
    public void updateAndSaveItemTest() throws Exception {
        ItemDTO item = createNewItem("Item for resources", 10, 1L);
        String path = "/api/v2/items/save";
        Mockito.when(mockItemService.saveItem(Mockito.any(ItemDTO.class))).thenReturn(item);
        String json = objectMapper.writeValueAsString(item);
        mvc.perform(MockMvcRequestBuilders.put(path).secure(false).content(json).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(mockItemService, Mockito.times(1)).saveItem(Mockito.any(ItemDTO.class));
    }

    @Test
    public void getItemsByIdTest() throws Exception {
        String path = "/api/v2/items/" + 1;
        Mockito.when(mockItemService.getItemById(1L)).thenReturn(new ItemDTO());
        mvc.perform(MockMvcRequestBuilders.get(path).secure(false).accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(mockItemService, Mockito.times(1)).getItemById(Mockito.any(Long.class));
    }

    @Test
    public void getItemsByCategoryIdTest() throws Exception {
        String path = "/api/v2/items/list/category/" + 1;
        Mockito.when(mockItemService.getItemsByCategoryId(1L)).thenReturn(new ArrayList<ItemDTO>());
        mvc.perform(MockMvcRequestBuilders.get(path).secure(false).accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(mockItemService, Mockito.times(1)).getItemsByCategoryId(Mockito.any(Long.class));
    }

    @Test
    public void deleteItemByIdTest() throws Exception {
        String path = "/api/v2/items/" + 1;
        mvc.perform(MockMvcRequestBuilders.delete(path).secure(false).accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(mockItemService, Mockito.times(1)).deleteItem(Mockito.any(Long.class));
    }

    @Test
    public void deleteItemByIdExceptionTest() throws Exception {
        String path = "/api/v2/items/" + 1;
        Mockito.doThrow(new UnsupportedDataTypeException()).when(mockItemService).deleteItem(Mockito.anyLong());
        mvc.perform(MockMvcRequestBuilders.delete(path).secure(false).accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    public void getItemByIdExceptionTest() throws Exception {
        String path = "/api/v2/items/" + 1;
        Mockito.doThrow(new UnsupportedDataTypeException()).when(mockItemService).getItemById(Mockito.anyLong());
        mvc.perform(MockMvcRequestBuilders.get(path).secure(false).accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    public void getItemByCategoryIdExceptionTest() throws Exception {
        String path = "/api/v2/items/list/category/" + 1;
        Mockito.doThrow(new UnsupportedDataTypeException()).when(mockItemService).getItemsByCategoryId(Mockito.anyLong());
        mvc.perform(MockMvcRequestBuilders.get(path).secure(false).accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    public ItemDTO createNewItem(String itemDescription, int quantity, Long categoryId) {
        ItemDTO item = new ItemDTO();
        item.setDescription(itemDescription);
        item.setQuantity(quantity);
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(categoryId);
        item.setCategory(categoryDTO);
        return item;
    }

}
