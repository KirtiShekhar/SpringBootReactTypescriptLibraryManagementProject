package com.fullstack.library.management.backend.additional.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
public class ErrorResponse
{
    private String message;
    private int statusCode;
    private LocalDateTime dateTime;
}
