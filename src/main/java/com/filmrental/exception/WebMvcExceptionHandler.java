package com.filmrental.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackageClasses = com.filmrental.controller.WebPageController.class)
public class WebMvcExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFound(ResourceNotFoundException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("status", 404);
        return "error";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String serverError(Exception ex, Model model) {
        Throwable root = ex;
        while (root.getCause() != null) {
            root = root.getCause();
        }

        String message = root.getMessage() != null ? root.getMessage() : ex.getMessage();
        model.addAttribute("message", message);
        model.addAttribute("status", 500);
        return "error";
    }
}
