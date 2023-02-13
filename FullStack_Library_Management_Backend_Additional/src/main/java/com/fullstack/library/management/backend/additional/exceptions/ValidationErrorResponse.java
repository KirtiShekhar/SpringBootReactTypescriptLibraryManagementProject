package com.fullstack.library.management.backend.additional.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Map;
import java.util.HashMap;

@Setter
@Getter
@NoArgsConstructor
public class ValidationErrorResponse extends ErrorResponse
{
    private Map<String,String> errors = new HashMap<String,String>();
}
