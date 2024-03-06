package com.ferps.todo.enums.converter;

import com.ferps.todo.enums.Type;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class TypeConverter implements AttributeConverter<Type, String> {
    @Override
    public String convertToDatabaseColumn(Type type) {
       return type.toString();
    }

    @Override
    public Type convertToEntityAttribute(String s) {
        return Type.valueOf(s);
    }
}
