package com.fullstack.library.management.backend.additional.exceptions;

public class MessagesNotFoundException extends RuntimeException
{
    public static final long serialVersionId = 1L;

    public MessagesNotFoundException(String message)
    {
        super(message);
    }
}