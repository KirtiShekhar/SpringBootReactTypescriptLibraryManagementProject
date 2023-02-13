package com.fullstack.library.management.backend.additional.service;

import com.fullstack.library.management.backend.additional.dto.AddBookRequest;
import com.fullstack.library.management.backend.additional.exceptions.AdminRolePageException;
import com.fullstack.library.management.backend.additional.exceptions.BookNotFoundException;

public interface AdminService {
    void postBook(AddBookRequest addBookRequest);

    void increaseBookQuantity(Long bookId) throws AdminRolePageException, BookNotFoundException;

    void decreaseBookQuantity(Long bookId) throws AdminRolePageException, BookNotFoundException;

    void deleteBook(Long bookId) throws AdminRolePageException, BookNotFoundException;
}
