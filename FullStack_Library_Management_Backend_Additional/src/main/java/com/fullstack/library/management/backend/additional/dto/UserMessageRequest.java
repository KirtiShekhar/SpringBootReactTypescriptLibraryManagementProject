package com.fullstack.library.management.backend.additional.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMessageRequest
{
    private String title;
    private String question;
}
