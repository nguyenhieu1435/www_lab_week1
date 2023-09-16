package vn.edu.iuh.fit.models;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, Integer>{
    @Override
    public Integer convertToDatabaseColumn(Status status) {
        return status.getStatusNumber();
    }

    @Override
    public Status convertToEntityAttribute(Integer integer) {
        return Status.fromStatusNumber(integer);
    }
}
