package com.example.myproject.web.interceptor;

import com.example.myproject.web.interceptor.UserInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionTimerInterceptor implements HandlerInterceptor {

    private static final long MAX_INACTIVE_SESSION_TIME = 10 * 10000;
    private static Logger log = LoggerFactory.getLogger(SessionTimerInterceptor.class);

    @Autowired
    private HttpSession session;

    @Override
    public boolean preHandle(

            HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Pre handle method - проверка на начално време");
        long startTime = System.currentTimeMillis();
        request.setAttribute("executionTime", startTime);


        if (UserInterceptor.isUserLogged()) {
            session = request.getSession();
            log.info("Време от последният рикуест в текущата сесия: {} ms",
                    System.currentTimeMillis() - request.getSession().getLastAccessedTime());
            if (System.currentTimeMillis() - session.getLastAccessedTime()
                    > MAX_INACTIVE_SESSION_TIME) {
                log.warn("Логаут поради неактивна сесия!");
                SecurityContextHolder.clearContext();
                request.logout();
                response.sendRedirect("/");

            }
        }
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView model) throws Exception {
        log.info("Post handle method - check execution time of handling");
        long startTime = (Long) request.getAttribute("executionTime");
        log.info("Execution time for handling the request was: {} ms",
                System.currentTimeMillis() - startTime);
    }

    }



