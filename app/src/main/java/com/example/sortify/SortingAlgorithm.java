package com.example.sortify;


public class SortingAlgorithm {
    private String name;
    private String description;
    private String code;

    public SortingAlgorithm(String name, String description, String code) {
        this.name = name;
        this.description = description;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }
}
