package com.fullstack.library.management.backend.additional.exceptions;

public class ReviewExistException extends RuntimeException
{
    public static final long serialVersionId = 1L;

    public ReviewExistException(String message)
    {
        super(message);
    }
}