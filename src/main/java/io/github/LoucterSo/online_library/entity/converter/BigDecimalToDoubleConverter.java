package io.github.LoucterSo.online_library.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.math.BigDecimal;

@Converter(autoApply = true)
public class BigDecimalToDoubleConverter implements AttributeConverter<Double, BigDecimal> {
    @Override
    public BigDecimal convertToDatabaseColumn(Double attribute) {
        return attribute != null ? BigDecimal.valueOf(attribute) : null;
    }

    @Override
    public Double convertToEntityAttribute(BigDecimal dbData) {
        return dbData != null ? dbData.doubleValue() : null;
    }
}