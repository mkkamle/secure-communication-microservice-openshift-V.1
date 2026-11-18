package com.cg.order.exception;

public class DuplicateRequestException
        extends RuntimeException {

    public DuplicateRequestException(
            String key) {

        super(
                "Duplicate request detected for key: "
                        + key);
    }
}