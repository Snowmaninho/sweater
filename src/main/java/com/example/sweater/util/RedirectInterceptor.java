package com.example.sweater.util;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RedirectInterceptor extends HandlerInterceptorAdapter { // этот класс для Turbolinks - ускорение загрузки страниц

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            String args = request.getQueryString() != null ? request.getQueryString() : ""; // аргументы в строке url после знака ?
            String url = request.getRequestURI().toString() + "?" + args; // соединяем url и аргументы в полноценный адрес
            response.setHeader("Turbolinks-Location", url);
        }
    }
}
