package com.alelo.resources.adapter;

import java.util.List;

import javax.activation.UnsupportedDataTypeException;

import com.alelo.resources.dto.ItemDTO;

public interface ItemService {

    List<ItemDTO> getListItems();

    ItemDTO saveItem(ItemDTO item);

    void deleteItem(Long id) throws UnsupportedDataTypeException;

    ItemDTO getItemById(Long id) throws Exception;

}
