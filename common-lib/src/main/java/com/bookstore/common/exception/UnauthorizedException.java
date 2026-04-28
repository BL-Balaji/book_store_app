package com.bookstore.common.exception;

/**
 * Exception thrown for unauthorized access
 * 
 * @author BL Balaji
 */
public class UnauthorizedException extends RuntimeException {
    
    public UnauthorizedException(String message) {
        super(message);
    }
}
