package com.alelo.utils;

import com.alelo.resources.dto.CategoryDTO;
import com.alelo.resources.dto.ItemDTO;

public class BaseDataTestUtils {

    public static ItemDTO createNewItem(String itemDescription, int quantity, Long categoryId) {
        ItemDTO item = new ItemDTO();
        item.setDescription(itemDescription);
        item.setQuantity(quantity);
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(categoryId);
        item.setCategory(categoryDTO);
        return item;
    }

}
