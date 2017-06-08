
package com.sq.domain.enums;

public enum Result {
    SUCCESS(1),
    FAILED(0);

    private int value;

    private Result(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static boolean isSuccess(Result result) {
        return result == SUCCESS;
    }
}
