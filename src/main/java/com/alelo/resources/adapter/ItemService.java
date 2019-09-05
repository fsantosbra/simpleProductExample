package com.alelo.resources.adapter;

import java.util.List;

import javax.activation.UnsupportedDataTypeException;

import com.alelo.resources.dto.ItemDTO;

public interface ItemService {

    List<ItemDTO> getListItems() throws Exception;

    ItemDTO saveItem(ItemDTO item) throws Exception;

    void deleteItem(Long id) throws UnsupportedDataTypeException;

    ItemDTO getItemById(Long id) throws Exception;

    List<ItemDTO> getItemsByCategoryId(Long id) throws Exception;

    List<ItemDTO> saveListItem(List<ItemDTO> itemsDto) throws Exception;

}
