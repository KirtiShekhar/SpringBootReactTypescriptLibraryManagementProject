package com.fullstack.library.management.backend.additional.serviceImplementation;

import com.fullstack.library.management.backend.additional.dto.ShelfCurrentLoansResponseDto;
import com.fullstack.library.management.backend.additional.entity.Book;
import com.fullstack.library.management.backend.additional.entity.CheckOut;
import com.fullstack.library.management.backend.additional.entity.History;
import com.fullstack.library.management.backend.additional.exceptions.BookNotExistOrCheckOutException;
import com.fullstack.library.management.backend.additional.exceptions.BookNotExistOrNotCheckOutException;
import com.fullstack.library.management.backend.additional.repository.BookRepository;
import com.fullstack.library.management.backend.additional.repository.CheckOutRepository;
import com.fullstack.library.management.backend.additional.repository.HistoryRepository;
import com.fullstack.library.management.backend.additional.response.ResponseMessages;
import com.fullstack.library.management.backend.additional.service.CheckOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class CheckOutServiceImplementation implements CheckOutService
{
    @Autowired
    private CheckOutRepository checkOutRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Override
    public ResponseMessages<List<CheckOut>> getAllCheckOutDetails()
    {
        ResponseMessages<List<CheckOut>> checkOutListResponseMessages = new ResponseMessages<>();
        checkOutListResponseMessages.setData(checkOutRepository.findAll());
        return checkOutListResponseMessages;
    }

    @Override
    public ResponseMessages<Page<CheckOut>> getAllCheckoutDetailsPageWise(int page,int size)
    {
        ResponseMessages<Page<CheckOut>> checkOutPageWiseResponseMessages = new ResponseMessages<>();
        Pageable pageable = PageRequest.of(page, size);
        checkOutPageWiseResponseMessages.setData(checkOutRepository.findAll(pageable));
        return checkOutPageWiseResponseMessages;
    }

    @Override
    public ResponseMessages<CheckOut> getCheckOutDetailsByCheckOutId(Long checkoutId)
    {
        ResponseMessages<CheckOut> checkOutResponseMessages = new ResponseMessages<>();
        checkOutResponseMessages.setData(checkOutRepository.findById(checkoutId).get());
        return checkOutResponseMessages;
    }

    @Override
    public ResponseMessages<Book> checkOutBook(String userEmail, Long bookId) throws BookNotExistOrCheckOutException
    {
        ResponseMessages<Book> optionalBookResponsesMessages = new ResponseMessages<>();
        Optional<Book> findBook = bookRepository.findById(bookId);

        CheckOut validateCheckOutBook = checkOutRepository.findByUserEmailAndBookId(userEmail,bookId);

        if(!findBook.isPresent() || validateCheckOutBook != null || findBook.get().getCopiesAvailable() <= 0)
        {
            throw new BookNotExistOrCheckOutException("Book doesn't exist or already checked out by user");
        }

        findBook.get().setCopiesAvailable(findBook.get().getCopiesAvailable() -1);
        bookRepository.save(findBook.get());

        CheckOut checkOut = new CheckOut(
                userEmail,
                LocalDateTime.now().toString(),
                LocalDateTime.now().plusDays(7).toString(),
                findBook.get().getBookId()
        );

        checkOutRepository.save(checkOut);

        optionalBookResponsesMessages.setData(findBook.get());
        return optionalBookResponsesMessages;
    }

    @Override
    public Boolean isBookCheckedOutByUser(String userEmail, Long bookId)
    {
        CheckOut validateCheckOutBook = checkOutRepository.findByUserEmailAndBookId(userEmail,bookId);

        if(validateCheckOutBook != null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public int currentLoansCount(String userEmail)
    {
        return checkOutRepository.findBooksByUserEmail(userEmail).size();
    }

    @Override
    public ResponseMessages<List<ShelfCurrentLoansResponseDto>> currentLoans(String userEmail) throws ParseException
    {
        ResponseMessages<List<ShelfCurrentLoansResponseDto>> shelfCurrentLoansListResponseMessages = new ResponseMessages<>();
        List<ShelfCurrentLoansResponseDto> shelfCurrentLoansResponseList = new ArrayList<>();
        List<CheckOut> checkOutList = checkOutRepository.findBooksByUserEmail(userEmail);

        List<Long> bookIdList = new ArrayList<>();

        for(CheckOut i:checkOutList)
        {
            bookIdList.add(i.getBookId());
        }

        List<Book> bookList = bookRepository.findBooksByBookIds(bookIdList);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for(Book book:bookList)
        {
            Optional<CheckOut> checkOutOptional = checkOutList.stream()
                    .filter(x -> x.getBookId() == book.getBookId()).findFirst();

            if(checkOutOptional.isPresent())
            {
                Date d1 = sdf.parse(checkOutOptional.get().getReturnDate());
                Date d2 = sdf.parse(LocalDate.now().toString());

                TimeUnit time = TimeUnit.DAYS;

                long difference_In_Time = time.convert(d1.getTime() - d2.getTime(),
                        TimeUnit.MILLISECONDS);

                shelfCurrentLoansResponseList.add(new ShelfCurrentLoansResponseDto(book,(int) difference_In_Time));
            }
        }
        shelfCurrentLoansListResponseMessages.setData(shelfCurrentLoansResponseList);

        return shelfCurrentLoansListResponseMessages;
    }

    @Override
    public void returnCheckedOutBook(String userEmail, Long bookId) throws BookNotExistOrNotCheckOutException
    {
        Optional<Book> book = bookRepository.findById(bookId);

        CheckOut validateCheckOut = checkOutRepository.findByUserEmailAndBookId(userEmail, bookId);

        if(!book.isPresent() || validateCheckOut == null)
        {
            throw new BookNotExistOrNotCheckOutException("Book does not exist or not checked out by user");
        }

        book.get().setCopiesAvailable((book.get().getCopiesAvailable() + 1));

        bookRepository.save(book.get());

        checkOutRepository.deleteById(validateCheckOut.getCheckoutId());

        History history = new History(validateCheckOut.getCheckoutDate(),
                LocalDate.now().toString(),
                book.get().getBookTitle(),
                book.get().getBookAuthor(),
                book.get().getBookDescription(),
                book.get().getBookImage());

        historyRepository.save(history);
    }

    @Override
    public void renewLoan(String userEmail, Long bookId) throws BookNotExistOrNotCheckOutException, ParseException {
        CheckOut validateCheckOut = checkOutRepository.findByUserEmailAndBookId(userEmail, bookId);

        if(validateCheckOut == null)
        {
            throw new BookNotExistOrNotCheckOutException("Book does not exist or not checked out by user");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date d1 = sdf.parse(validateCheckOut.getReturnDate());
        Date d2 = sdf.parse(LocalDate.now().toString());

        if(d1.compareTo(d2) > 0 || d1.compareTo(d2) == 0)
        {
            validateCheckOut.setReturnDate(LocalDate.now().plusDays(7).toString());
            checkOutRepository.save(validateCheckOut);
        }
    }
}
