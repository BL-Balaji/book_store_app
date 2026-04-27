package com.bookstore.common.exception;

/**
 * Exception thrown for bad requests
 * 
 * @author BL Balaji
 */
public class BadRequestException extends RuntimeException {
    
    public BadRequestException(String message) {
        super(message);
    }
}
