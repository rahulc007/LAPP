package com.svarks.lapp.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.GenericFilterBean;

import com.svarks.lapp.order.dao.service.UserAuthDao;


@Configuration
public class ApiHeaderFilter extends GenericFilterBean {
	
	@Autowired
	UserAuthDao userAuthService;
	
	private static final Logger log = LoggerFactory.getLogger(ApiHeaderFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
     //   HttpServletResponse response = (HttpServletResponse)servletResponse;
        String token = request.getHeader("Authorization");
        /*log.info("***********************************************");
        log.info("***********HEADER VALIDATION TRIGGERED************");
        log.info("***********************************************");*/
        
     /*   log.info("*******TOKEN VALUE==>"+token);
        
       // log.info("request method==>"+request.getMethod());
        if(token !=null && !token.isEmpty()) {
           token=token.split(" ")[1];
           log.info("******* TOKEN VALUE==>"+token);
           log.info("is token valid==>"+userAuthService.findByToken(token));
        }else {
        	log.info("invalid authorization..!");
        }
        */
        filterChain.doFilter(servletRequest,servletResponse);
    }
}