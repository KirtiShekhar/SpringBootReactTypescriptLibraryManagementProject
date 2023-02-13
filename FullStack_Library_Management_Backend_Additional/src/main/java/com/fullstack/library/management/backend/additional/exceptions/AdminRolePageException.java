package com.fullstack.library.management.backend.additional.exceptions;

public class AdminRolePageException extends RuntimeException
{
    public static final long serialVersionId = 1L;

    public AdminRolePageException(String message)
    {
        super(message);
    }
}