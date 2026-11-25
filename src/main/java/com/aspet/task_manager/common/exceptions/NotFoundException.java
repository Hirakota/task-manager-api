package com.aspet.task_manager.common.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String name, Long id) {
        super(name + " not found: " + id);
    }
}
