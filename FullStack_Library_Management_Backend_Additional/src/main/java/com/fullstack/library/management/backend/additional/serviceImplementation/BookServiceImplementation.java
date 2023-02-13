package com.fullstack.library.management.backend.additional.serviceImplementation;

import com.fullstack.library.management.backend.additional.entity.Book;
import com.fullstack.library.management.backend.additional.repository.BookRepository;
import com.fullstack.library.management.backend.additional.response.ResponseMessages;
import com.fullstack.library.management.backend.additional.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
public class BookServiceImplementation implements BookService
{
    @Autowired
    private BookRepository bookRepository;

    @Override
    public ResponseMessages<List<Book>> getAllBooks()
    {
        ResponseMessages<List<Book>> bookListResponseMessage = new ResponseMessages<>();
        bookListResponseMessage.setData(bookRepository.findAll());
        return bookListResponseMessage;
    }

    @Override
    public ResponseMessages<Page<Book>> getAllBooksPageWise(int page, int size)
    {
        ResponseMessages<Page<Book>> bookPageWiseResponseMessage = new ResponseMessages<>();
        Pageable pageable = PageRequest.of(page, size);
        bookPageWiseResponseMessage.setData(bookRepository.findAll(pageable));
        return bookPageWiseResponseMessage;
    }

    @Override
    public ResponseMessages<Book> getBookByBookId(Long bookId)
    {
        ResponseMessages<Book> bookResponseMessage = new ResponseMessages<>();
        bookResponseMessage.setData(bookRepository.findById(bookId).get());
        return bookResponseMessage;
    }

    @Override
    public ResponseMessages<Page<Book>> getBookTitleContainingPageWise(String bookTitle,int page,int size)
    {
        ResponseMessages<Page<Book>> bookPageWiseResponseMessage = new ResponseMessages<>();
        Pageable pageable = PageRequest.of(page, size);
        bookPageWiseResponseMessage.setData(bookRepository.findByBookTitleContaining(bookTitle,pageable));
        return bookPageWiseResponseMessage;
    }

    @Override
    public ResponseMessages<Page<Book>> getBookCategoryContainingPageWise(String bookCategory,int page,int size)
    {
        ResponseMessages<Page<Book>> bookPageWiseResponseMessage = new ResponseMessages<>();
        Pageable pageable = PageRequest.of(page, size);
        bookPageWiseResponseMessage.setData(bookRepository.findByBookCategoryContaining(bookCategory,pageable));
        return bookPageWiseResponseMessage;
    }
}
