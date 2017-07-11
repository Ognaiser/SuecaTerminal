package org.academia.rollete;

public enum RoletteColors {
    RED("\u001B[31m"),
    BLACK("\u001B[0m"),
    GREEN("\u001B[32m");

    private String code;

    RoletteColors(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
