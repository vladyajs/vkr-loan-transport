package com.vkr.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ApplicationStatus implements ValuableEnum<ApplicationStatus> {
    NEW("Новая"),
    IN_PROGRESS("В обработке"),
    APPROVED("Одобрена"),
    REJECTED("Отклонена");

    private final String value;

    @JsonCreator
    public static ApplicationStatus parse(String value) {
        return NEW.defaultParse(value);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public ApplicationStatus[] getValues() {
        return ApplicationStatus.values();
    }

}
