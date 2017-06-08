//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sq.domain.enums;

public enum Code {
    HeartBeat(200),
    COOMME(300);

    private int value;

    private Code(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
