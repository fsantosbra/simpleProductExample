package com.alelo.resources.adapter.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.activation.UnsupportedDataTypeException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alelo.data.Item;
import com.alelo.data.mapper.ItemMapper;
import com.alelo.data.repository.ItemRespository;
import com.alelo.resources.adapter.ItemService;
import com.alelo.resources.dto.CategoryDTO;
import com.alelo.resources.dto.ItemDTO;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRespository repository;

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public List<ItemDTO> getListItems() {
        try {
            return this.itemMapper.dtosFromEntities(this.repository.findAll());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ItemDTO saveItem(ItemDTO itemDto) {
        try {
            Item item = this.itemMapper.entityFromDto(itemDto);
            return this.itemMapper.dtoFromEntity(this.repository.saveAndFlush(item));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteItem(String id) throws UnsupportedDataTypeException {
        if (Objects.isNull(id)) {
            throw new UnsupportedDataTypeException("The id must be a valid numeric value");
        }
        Long idValue = Long.valueOf(id);
        this.repository.deleteById(idValue);
    }

    @Override
    public ItemDTO getItemById(Long id) throws Exception {
        Optional<Item> itemFound = this.repository.findById(id);
        return this.itemMapper.dtoFromEntity(itemFound.get());
    }

    public void sellItem(CategoryDTO itemDto) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity("localhost:8080/anyUrlToTest", CategoryDTO.class);
    }

}
