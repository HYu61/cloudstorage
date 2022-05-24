package com.udacity.jwdnd.course1.cloudstorage.exceptions.handler;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.AppException;
import com.udacity.jwdnd.course1.cloudstorage.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * The class is used for handler all kinds of exceptions
 * author: Heng Yu
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {

    Logger log = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    // Handler the general exception.
    @ExceptionHandler(Exception.class)
    public ModelAndView handlerGeneralException(HttpServletRequest request, Exception e) {
        loging(request, e);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error");
        mav.addObject("errMsg", "Something is wrong! Sorry about that!");
        return mav;
    }

    // handler the validation exception and return the message back to the error page
    @ExceptionHandler(BindException.class)
    public ModelAndView handlerValidationException(HttpServletRequest request, BindException e) {
        loging(request, e);
        StringBuffer errStringBuffer = new StringBuffer();
        e.getAllErrors().forEach(error -> errStringBuffer.append(error.getDefaultMessage()).append(" "));
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error");
        mav.addObject("errMsg", errStringBuffer);
        return mav;
    }

    // Handler the app exception.
    @ExceptionHandler(AppException.class)
    public ModelAndView handlerAppException(HttpServletRequest request, AppException e) {
        loging(request, e);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error");
        mav.addObject("errMsg", e.getMessage());
        return mav;
    }


    private void loging(HttpServletRequest request, Exception e) {
        log.error(request.getMethod() + "--" + request.getRequestURI() + "--" + e.getMessage());

    }
}
