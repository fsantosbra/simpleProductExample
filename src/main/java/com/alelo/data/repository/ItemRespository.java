package com.alelo.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alelo.data.Item;

@Repository
public interface ItemRespository extends JpaRepository<Item, Long> {

    List<Item> findByCategoryId(Long categoryId);

}
