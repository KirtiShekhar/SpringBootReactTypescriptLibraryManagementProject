package com.fullstack.library.management.backend.additional.exceptions;

public class BookNotExistOrCheckOutException extends RuntimeException
{
    public static final long serialVersionId = 1L;

    public BookNotExistOrCheckOutException(String message)
    {
        super(message);
    }
}