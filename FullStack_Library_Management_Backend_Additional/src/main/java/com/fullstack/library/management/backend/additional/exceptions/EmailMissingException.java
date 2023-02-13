package com.fullstack.library.management.backend.additional.exceptions;

public class EmailMissingException extends RuntimeException
{
    public static final long serialVersionId = 1L;

    public EmailMissingException(String message)
    {
        super(message);
    }
}
