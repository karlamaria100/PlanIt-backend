package com.fmi.planit.security;

import com.fmi.planit.model.User;
import com.fmi.planit.service.SecurityService;
import com.fmi.planit.service.UserService;
import com.fmi.planit.utils.AppConfig;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@CrossOrigin
public class CustomFilter implements HandlerInterceptor {

    private SecurityService service = new SecurityService();
    private static List<String> userPaths;
    private static List<String> allowedURIs;

    private UserService userService;

    private static String[] userPathsArray = {
            "/planit/projects/selectAll",

    };

    private static String[] allowedURIsArray = {
            /* public links */
            "/planit/users/login",
    };

    static {
        userPaths = Arrays.asList(userPathsArray);
        allowedURIs = Arrays.asList(allowedURIsArray);
    }

    public CustomFilter(UserService service){
        userService = service;
    }


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
//        try {
//            System.out.println();
//            if (Arrays.asList(allowedURIsArray).contains(httpServletRequest.getRequestURI())) {
//                return true;
//            } else {
//                String token = httpServletRequest.getHeader("security-token");
//                if ("OPTIONS".equals(httpServletRequest.getMethod())) {
//                    return true;
//                }
//                if(Arrays.asList(userPathsArray).contains(httpServletRequest.getRequestURI())){
//                    System.out.println("Request with token:" + token);
//                    if (token == null || !service.validateToken(token)) {
//                        System.out.println("wrong token");
//                        return false;
//                    }
//
//                    User user = service.getInfo(token);
//                    System.out.println(httpServletRequest.getRequestURI());
//                    if(userService.userExists(user)){
//                        String requestURI = httpServletRequest.getRequestURI();
//
//                        return true;
//                    }else {
//                        return false;
//                    }
//                }else {
//                    return false;
//                }
//
//
//            }

//        } catch (Exception eje) {
//            eje.printStackTrace();
//        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
