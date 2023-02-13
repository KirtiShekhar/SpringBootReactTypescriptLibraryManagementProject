package com.fullstack.library.management.backend.additional.dto;

import com.fullstack.library.management.backend.additional.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShelfCurrentLoansResponseDto
{
    private Book book;
    private int daysLeft;
}
