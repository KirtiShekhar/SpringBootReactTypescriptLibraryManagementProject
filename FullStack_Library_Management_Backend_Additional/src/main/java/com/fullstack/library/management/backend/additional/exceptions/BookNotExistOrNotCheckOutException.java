package com.fullstack.library.management.backend.additional.exceptions;

public class BookNotExistOrNotCheckOutException extends RuntimeException
{
    public static final long serialVersionId = 1L;

    public BookNotExistOrNotCheckOutException(String message)
    {
        super(message);
    }
}