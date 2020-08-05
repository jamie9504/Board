package com.github.jamie9504.board.user.exception;

public class AlreadyExistsEntityException extends IllegalArgumentException {

    public AlreadyExistsEntityException(String message) {
        super(message);
    }
}
