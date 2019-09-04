package com.alelo.data.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alelo.data.Item;
import com.alelo.resources.dto.ItemDTO;
import com.alelo.utils.Converter;

@Component
public class ItemMapper implements Converter<Item, ItemDTO> {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Item entityFromDto(ItemDTO dto) throws Exception {
        Item entity = new Item();
        entity.setId(dto.getId());
        entity.setDescription(dto.getDescription());
        entity.setQuantity(dto.getQuantity());
        entity.setCategory(this.categoryMapper.entityFromDto(dto.getCategory()));
        return entity;
    }

    @Override
    public ItemDTO dtoFromEntity(Item entity) throws Exception {
        ItemDTO itemDto = new ItemDTO();
        itemDto.setId(entity.getId());
        itemDto.setDescription(entity.getDescription());
        itemDto.setQuantity(entity.getQuantity());
        itemDto.setCategory(this.categoryMapper.dtoFromEntity(entity.getCategory()));
        return itemDto;
    }

}
