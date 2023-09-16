package vn.edu.iuh.fit.models;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class IsGrantConverter implements AttributeConverter<IsGrant, String> {
    @Override
    public IsGrant convertToEntityAttribute(String s) {
        return IsGrant.getStatusNumber(s);
    }

    @Override
    public String convertToDatabaseColumn(IsGrant isGrant) {
        return isGrant.getGrant();
    }
}
