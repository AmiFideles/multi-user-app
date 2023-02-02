package com.itmo.programming.mapper.todto;


import com.itmo.programming.dto.ColorDTO;
import com.itmo.programming.model.Color;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
@Mapper
public interface ColorMapper {
    ColorMapper INSTANCE = Mappers.getMapper(ColorMapper.class);

    ColorDTO toDTO(Color color);
}
