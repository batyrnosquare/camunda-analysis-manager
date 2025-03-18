package com.batyrnosquare.demo.constants;

public enum Gender {
    MALE(130),
    FEMALE(120);

    public final int hemoLimit;

    Gender(int hemoLimit) {
        this.hemoLimit = hemoLimit;
    }

    public int getHemoLimit() {
        return hemoLimit;
    }

    public boolean isAnemic(int hemoglobin) {
        return hemoglobin < hemoLimit;
    }
}

