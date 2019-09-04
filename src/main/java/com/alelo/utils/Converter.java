package com.alelo.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Converter interface to convert entities and DTOs back and forth.
 *
 * @param <S> Entity
 * @param <T> DTO
 * @author Meenu Juneja
 */
public interface Converter<S, T> {

    /**
     * Maps a DTO to an Entity.
     *
     * @param dto DTO
     * @return Entity
     * @throws Exception If a problem occurs
     */
    S entityFromDto(T dto) throws Exception;

    /**
     * Maps a List of DTOs to a List of Entities.
     *
     * @param dtos DTOs
     * @return Entities
     * @throws Exception If a problem occurs
     */
    default List<S> entitiesFromDtos(List<T> dtos) throws Exception {
        List<S> entities = new ArrayList<>();
        for (T dto : dtos) {
            entities.add(this.entityFromDto(dto));
        }
        return entities;
    }

    /**
     * Maps an Entity to a DTO.
     *
     * @param entity Entity
     * @return DTO
     * @throws Exception If a problem occurs
     */
    T dtoFromEntity(S entity) throws Exception;

    /**
     * Mapx a List of Entities to a List of DTOs.
     *
     * @param entities Entities
     * @return DTOs
     * @throws Exception If a problem occurs
     */
    default List<T> dtosFromEntities(List<S> entities) throws Exception {
        List<T> dtos = new ArrayList<>();
        for (S entity : entities) {
            dtos.add(this.dtoFromEntity(entity));
        }
        return dtos;
    }

}
