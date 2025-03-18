package com.vkr.models.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Locale;

public interface ValuableEnum<T extends ValuableEnum<T>> {
    String MULTIPLE_WHITE_SPACE = "\\s+";

    String getValue();

    @JsonValue
    default String getJsonValue() {
        return getValue();
    }

    default T defaultParse(String value) {
        if (value != null) {
            var updatedValue = value.toLowerCase(Locale.ROOT).replaceAll(MULTIPLE_WHITE_SPACE, " ").strip();
            for (T variant : getValues()) {
                if (variant.getValue().toLowerCase(Locale.ROOT).equals(updatedValue)) {
                    return variant;
                }
            }
        }
        return null;
    }

    T[] getValues();
}