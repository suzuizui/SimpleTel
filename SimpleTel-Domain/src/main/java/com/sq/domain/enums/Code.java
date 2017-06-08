//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sq.domain.enums;

public enum Code {
    SUCCESS(200),
    FAIL(300);

    private int value;

    private Code(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
