package com.fullstack.library.management.backend.additional.service;

import com.fullstack.library.management.backend.additional.dto.ShelfCurrentLoansResponseDto;
import com.fullstack.library.management.backend.additional.entity.Book;
import com.fullstack.library.management.backend.additional.entity.CheckOut;
import com.fullstack.library.management.backend.additional.exceptions.BookNotExistOrCheckOutException;
import com.fullstack.library.management.backend.additional.exceptions.BookNotExistOrNotCheckOutException;
import com.fullstack.library.management.backend.additional.response.ResponseMessages;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.List;

public interface CheckOutService {
    ResponseMessages<List<CheckOut>> getAllCheckOutDetails();

    ResponseMessages<Page<CheckOut>> getAllCheckoutDetailsPageWise(int page, int size);

    ResponseMessages<CheckOut> getCheckOutDetailsByCheckOutId(Long checkoutId);

    ResponseMessages<Book> checkOutBook(String userEmail, Long bookId) throws BookNotExistOrCheckOutException;

    Boolean isBookCheckedOutByUser(String userEmail, Long bookId);

    int currentLoansCount(String userEmail);

    ResponseMessages<List<ShelfCurrentLoansResponseDto>> currentLoans(String userEmail) throws ParseException;

    void returnCheckedOutBook(String userEmail, Long bookId) throws BookNotExistOrNotCheckOutException;

    void renewLoan(String userEmail, Long bookId) throws BookNotExistOrNotCheckOutException, ParseException;
}
