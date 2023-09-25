package com.bankwithfargo.exception;

import java.util.List;

import com.bankwithfargo.entity.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleInvalidMethodArgument(MethodArgumentNotValidException ex){
        StringBuilder sb=new StringBuilder();
        BindingResult bindingResult=ex.getBindingResult();
        List<ObjectError> errors=bindingResult.getAllErrors();

        for (ObjectError objectError : errors) {
            sb.append(objectError.getDefaultMessage()+"\n");
        }
        return new ResponseEntity<String>(sb.toString(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidArgumentsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidArgumentsException(InvalidArgumentsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotFoundException(AccountNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientAccessException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientAccessException(InsufficientAccessException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
