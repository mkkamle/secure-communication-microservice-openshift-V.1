package com.cg.product.exception;

public class InsufficientInventoryException
        extends RuntimeException {

    public InsufficientInventoryException(
            Long productId,
            Integer available,
            Integer requested) {

        super(
            "Insufficient inventory for product "
            + productId
            + ". Available: "
            + available
            + ", Requested: "
            + requested);
    }
}