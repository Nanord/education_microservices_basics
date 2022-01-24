package com.stm.user.manager.controller;

import com.stm.user.manager.constants.ExceptionMessageConstants;
import com.stm.user.manager.exception.ClientArgumentException;
import com.stm.user.manager.exception.CrudEOperationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@EnableWebMvc
@Slf4j
public class ControllerExceptionHandler {

    //TODO: Метод может принимать запрос и ответ, которые вызвали данную ошибку
    // ResponseEntity<Object> handleCrudEOperationException(HttpServletRequest request, HttpServletResponse response, CrudEOperationException e)
    @ExceptionHandler(CrudEOperationException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<Object> handleCrudEOperationException(CrudEOperationException e) {
        String message = String.format(ExceptionMessageConstants.CRUD_EXCEPTION, ExceptionUtils.getMessage(e));
        log.error(message, e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }

    @ExceptionHandler(ClientArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleClientArgumentException(ClientArgumentException e) {
        String message = String.format(ExceptionMessageConstants.BAD_REQUEST_EXCEPTION, ExceptionUtils.getMessage(e));
        log.error(message, e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException e) {
        List<String> collect = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getObjectName() + "." + fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());
        String message = String.format(ExceptionMessageConstants.BAD_REQUEST_EXCEPTION, String.join(",", collect));
        log.error(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleValidationException(ConstraintViolationException e) {
        List<String> collect = e.getConstraintViolations().stream()
                .map(v -> String.format("%s:%s; Invalid value: \"%s\"", v.getPropertyPath(), v.getMessage(), v.getInvalidValue()))
                .collect(Collectors.toList());
        String message = String.format(ExceptionMessageConstants.BAD_REQUEST_EXCEPTION, String.join(",", collect));
        log.error(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<Object> handleException(Exception e) {
        String message = String.format(ExceptionMessageConstants.UNEXPECTED_EXCEPTION, ExceptionUtils.getMessage(e));
        log.error(message, e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }
}
