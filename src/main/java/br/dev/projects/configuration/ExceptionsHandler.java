package br.dev.projects.configuration;

import br.dev.projects.bean.ErrorMessage;
import br.dev.projects.exception.NotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ExceptionsHandler {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    ErrorMessage badRequest(Exception e) {
        return new ErrorMessage(e.getMessage());
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    ErrorMessage notFound(Exception e) {
        return new ErrorMessage(e.getMessage());
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseBody
    ErrorMessage userNameNotFound(Exception e) {
        return new ErrorMessage(e.getMessage());
    }

}
