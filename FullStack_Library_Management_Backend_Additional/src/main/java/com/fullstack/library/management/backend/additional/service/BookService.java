package com.fullstack.library.management.backend.additional.service;

import com.fullstack.library.management.backend.additional.entity.Book;
import com.fullstack.library.management.backend.additional.response.ResponseMessages;
import org.springframework.data.domain.Page;
import java.util.List;

public interface BookService {
    ResponseMessages<List<Book>> getAllBooks();

    ResponseMessages<Page<Book>> getAllBooksPageWise(int page, int size);

    ResponseMessages<Book> getBookByBookId(Long bookId);

    ResponseMessages<Page<Book>> getBookTitleContainingPageWise(String bookTitle, int page, int size);

    ResponseMessages<Page<Book>> getBookCategoryContainingPageWise(String bookCategory, int page, int size);
}
