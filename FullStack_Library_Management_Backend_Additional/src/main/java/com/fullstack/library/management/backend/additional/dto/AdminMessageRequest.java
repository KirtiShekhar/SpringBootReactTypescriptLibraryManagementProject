package com.fullstack.library.management.backend.additional.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminMessageRequest
{
    private Long questionId;

    private String messageResponse;
}
