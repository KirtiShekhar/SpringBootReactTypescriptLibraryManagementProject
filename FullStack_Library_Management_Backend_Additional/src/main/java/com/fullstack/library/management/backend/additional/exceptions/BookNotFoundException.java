package com.fullstack.library.management.backend.additional.exceptions;

public class BookNotFoundException extends RuntimeException
{
    public static final long serialVersionId = 1L;

    public BookNotFoundException(String message)
    {
        super(message);
    }
}
