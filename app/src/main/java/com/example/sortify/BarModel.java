package com.example.sortify;

public class BarModel {
    private int value;
    private int height;
    private int color;

    public BarModel(int value, int height, int color) {
        this.value = value;
        this.height = height;
        this.color = color;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}