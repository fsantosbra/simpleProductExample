package com.alelo.resources.adapter.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.activation.UnsupportedDataTypeException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alelo.data.Item;
import com.alelo.data.mapper.ItemMapper;
import com.alelo.data.repository.ItemRespository;
import com.alelo.resources.adapter.ItemService;
import com.alelo.resources.dto.ItemDTO;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRespository repository;

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public List<ItemDTO> getListItems() throws Exception {
        return this.itemMapper.dtosFromEntities(this.repository.findAll());
    }

    @Override
    public ItemDTO saveItem(ItemDTO itemDto) throws Exception {
        Item item = this.itemMapper.entityFromDto(itemDto);
        return this.itemMapper.dtoFromEntity(this.repository.saveAndFlush(item));
    }

    @Override
    public List<ItemDTO> saveListItem(List<ItemDTO> itemsDto) throws Exception {
        List<Item> items = this.itemMapper.entitiesFromDtos(itemsDto);
        return this.itemMapper.dtosFromEntities(this.repository.saveAll(items));
    }

    @Override
    public void deleteItem(Long id) throws UnsupportedDataTypeException {
        if (Objects.isNull(id)) {
            throw new UnsupportedDataTypeException("The id must be a valid numeric value");
        }
        Long idValue = Long.valueOf(id);
        this.repository.deleteById(idValue);
    }

    @Override
    public ItemDTO getItemById(Long id) throws Exception {
        if (Objects.isNull(id)) {
            throw new UnsupportedDataTypeException("The id must be a valid numeric value");
        }
        Optional<Item> itemFound = this.repository.findById(Long.valueOf(id));
        if (itemFound.isPresent()) {
            return this.itemMapper.dtoFromEntity(itemFound.get());
        }
        else {
            return null;
        }
    }

    @Override
    public List<ItemDTO> getItemsByCategoryId(Long id) throws Exception {
        return this.itemMapper.dtosFromEntities(this.repository.findByCategoryId(id));
    }

}
