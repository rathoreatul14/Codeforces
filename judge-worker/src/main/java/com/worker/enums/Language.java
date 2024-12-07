package com.worker.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Language {

    CPP("cpp"),
    JAVA("java"),
    PYTHON("py");

    private final String value;

    Language(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @JsonCreator
    public static Language fromValue(String value) {
        for (Language language : Language.values()) {
            if (language.value.equalsIgnoreCase(value)) {
                return language;
            }
        }
        throw new IllegalArgumentException("Unknown language: " + value);
    }
}
