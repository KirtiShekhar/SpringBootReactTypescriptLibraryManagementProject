package com.fullstack.library.management.backend.additional.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;
import javax.validation.ConstraintViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> hanldeException(MethodArgumentNotValidException ex)
    {
        List<FieldError> errors=ex.getFieldErrors();
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();
        validationErrorResponse.setDateTime(LocalDateTime.now());
        validationErrorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        validationErrorResponse.setMessage("Input Data has some errors... Please Input proper data");

        for(FieldError fieldError : errors)
        {
            validationErrorResponse.getErrors().put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new ResponseEntity<ValidationErrorResponse>(validationErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidationErrorResponse> hanldeException(ConstraintViolationException ex)
    {
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();
        validationErrorResponse.setDateTime(LocalDateTime.now());
        validationErrorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        validationErrorResponse.setMessage("Input Data has some errors... Please Input proper data");
        ex.getConstraintViolations() . forEach(error ->{
            validationErrorResponse.getErrors().put("field",error.getMessage());
        });
        return new ResponseEntity<ValidationErrorResponse>(validationErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailMissingException.class)
    public ResponseEntity<ErrorResponse> handleException(EmailMissingException eme)
    {
        ErrorResponse emailMissingErrorResponse = new ErrorResponse();
        emailMissingErrorResponse.setMessage(eme.getMessage());
        emailMissingErrorResponse.setDateTime(LocalDateTime.now());
        emailMissingErrorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<ErrorResponse>(emailMissingErrorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AdminRolePageException.class)
    public ResponseEntity<ErrorResponse> handleException(AdminRolePageException arpe)
    {
        ErrorResponse adminRolePageErrorResponse = new ErrorResponse();
        adminRolePageErrorResponse.setMessage(arpe.getMessage());
        adminRolePageErrorResponse.setDateTime(LocalDateTime.now());
        adminRolePageErrorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<ErrorResponse>(adminRolePageErrorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(BookNotFoundException bnfe)
    {
        ErrorResponse bookNotFoundErrorResponse = new ErrorResponse();
        bookNotFoundErrorResponse.setMessage(bnfe.getMessage());
        bookNotFoundErrorResponse.setDateTime(LocalDateTime.now());
        bookNotFoundErrorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<ErrorResponse>(bookNotFoundErrorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookNotExistOrCheckOutException.class)
    public ResponseEntity<ErrorResponse> handleException(BookNotExistOrCheckOutException bneoce)
    {
        ErrorResponse bookNotFoundOrCheckOutErrorResponse = new ErrorResponse();
        bookNotFoundOrCheckOutErrorResponse.setMessage(bneoce.getMessage());
        bookNotFoundOrCheckOutErrorResponse.setDateTime(LocalDateTime.now());
        bookNotFoundOrCheckOutErrorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<ErrorResponse>(bookNotFoundOrCheckOutErrorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookNotExistOrNotCheckOutException.class)
    public ResponseEntity<ErrorResponse> handleException(BookNotExistOrNotCheckOutException bneonce)
    {
        ErrorResponse bookNotFoundOrNotCheckOutErrorResponse = new ErrorResponse();
        bookNotFoundOrNotCheckOutErrorResponse.setMessage(bneonce.getMessage());
        bookNotFoundOrNotCheckOutErrorResponse.setDateTime(LocalDateTime.now());
        bookNotFoundOrNotCheckOutErrorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<ErrorResponse>(bookNotFoundOrNotCheckOutErrorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MessagesNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(MessagesNotFoundException mnfe)
    {
        ErrorResponse messageNotFoundErrorResponse = new ErrorResponse();
        messageNotFoundErrorResponse.setMessage(mnfe.getMessage());
        messageNotFoundErrorResponse.setDateTime(LocalDateTime.now());
        messageNotFoundErrorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<ErrorResponse>(messageNotFoundErrorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReviewExistException.class)
    public ResponseEntity<ErrorResponse> handleException(ReviewExistException reee)
    {
        ErrorResponse reviewAlreadyExistErrorResponse = new ErrorResponse();
        reviewAlreadyExistErrorResponse.setMessage(reee.getMessage());
        reviewAlreadyExistErrorResponse.setDateTime(LocalDateTime.now());
        reviewAlreadyExistErrorResponse.setStatusCode(HttpStatus.FOUND.value());
        return new ResponseEntity<ErrorResponse>(reviewAlreadyExistErrorResponse,HttpStatus.FOUND);
    }
}
