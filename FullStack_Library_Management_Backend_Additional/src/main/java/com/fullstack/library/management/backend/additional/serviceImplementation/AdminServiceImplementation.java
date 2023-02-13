package com.fullstack.library.management.backend.additional.serviceImplementation;

import com.fullstack.library.management.backend.additional.dto.AddBookRequest;
import com.fullstack.library.management.backend.additional.entity.Book;
import com.fullstack.library.management.backend.additional.exceptions.AdminRolePageException;
import com.fullstack.library.management.backend.additional.exceptions.BookNotFoundException;
import com.fullstack.library.management.backend.additional.repository.BookRepository;
import com.fullstack.library.management.backend.additional.repository.CheckOutRepository;
import com.fullstack.library.management.backend.additional.repository.ReviewRepository;
import com.fullstack.library.management.backend.additional.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AdminServiceImplementation implements AdminService
{
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CheckOutRepository checkOutRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public void postBook(AddBookRequest addBookRequest)
    {
        Book book = new Book();
        book.setBookTitle(addBookRequest.getBookTitle());
        book.setBookAuthor(addBookRequest.getBookAuthor());
        book.setBookDescription(addBookRequest.getBookDescription());
        book.setCopies(addBookRequest.getCopies());
        book.setCopiesAvailable(addBookRequest.getCopies());
        book.setBookCategory(addBookRequest.getBookCategory());
        book.setBookImage(addBookRequest.getBookImage());
        bookRepository.save(book);
    }

    @Override
    public void increaseBookQuantity(Long bookId) throws AdminRolePageException, BookNotFoundException
    {
        Optional<Book> book = bookRepository.findById(bookId);

        if(!book.isPresent())
        {
            throw new BookNotFoundException("Book not found");
        }

        book.get().setCopiesAvailable(book.get().getCopiesAvailable() + 1);
        book.get().setCopies(book.get().getCopies() + 1);

        bookRepository.save(book.get());
    }

    @Override
    public void decreaseBookQuantity(Long bookId) throws AdminRolePageException, BookNotFoundException
    {
        Optional<Book> book = bookRepository.findById(bookId);

        if(!book.isPresent())
        {
            throw new BookNotFoundException("Book not found");
        }

        book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);
        book.get().setCopies(book.get().getCopies() - 1);

        bookRepository.save(book.get());
    }

    @Override
    public void deleteBook(Long bookId) throws AdminRolePageException, BookNotFoundException
    {
        Optional<Book> book = bookRepository.findById(bookId);

        if(!book.isPresent())
        {
            throw new BookNotFoundException("Book not found");
        }

        bookRepository.delete(book.get());
        checkOutRepository.deleteAllByBookId(bookId);
        reviewRepository.deleteAllByBookId(bookId);
    }
}
