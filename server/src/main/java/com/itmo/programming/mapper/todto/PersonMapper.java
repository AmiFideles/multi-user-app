package com.itmo.programming.mapper.todto;


import com.itmo.programming.dto.PersonDTO;
import com.itmo.programming.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
@Mapper(uses = {ColorMapper.class, CoordinatesMapper.class, LocationMapper.class, UserMapper.class})
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Mapping(source = "person.coordinates", target = "coordinatesDTO")
    @Mapping(source = "person.location", target = "locationDTO")
    @Mapping(source = "person.color", target = "colorDTO")
    @Mapping(source = "person.user", target = "userDTO")
    PersonDTO toDTO(Person person);
}
