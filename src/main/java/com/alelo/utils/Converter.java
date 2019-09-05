package com.alelo.utils;

import java.util.ArrayList;
import java.util.List;

public interface Converter<S, T> {

    S entityFromDto(T dto) throws Exception;

    default List<S> entitiesFromDtos(List<T> dtos) throws Exception {
        List<S> entities = new ArrayList<>();
        for (T dto : dtos) {
            entities.add(this.entityFromDto(dto));
        }
        return entities;
    }

    T dtoFromEntity(S entity) throws Exception;

    default List<T> dtosFromEntities(List<S> entities) throws Exception {
        List<T> dtos = new ArrayList<>();
        for (S entity : entities) {
            dtos.add(this.dtoFromEntity(entity));
        }
        return dtos;
    }

}
