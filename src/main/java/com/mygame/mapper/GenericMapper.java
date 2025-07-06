package com.mygame.mapper;

import org.mapstruct.MappingTarget;

import java.util.List;

public interface GenericMapper<E, I, O> {

    E requestToEntity(I request); //requestDTO -> Entity

    O entityToResponse(E entity); //Entity -> responseDTO

    List<E> requestToEntity(List<I> requests);

    List<O> entityToResponse(List<E> entities);

    E partialUpdate(@MappingTarget E entity, I request); //update các trường cho entity

}
