package jpabook.jpashop.Interceptor;

import org.apache.juli.logging.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class SessionInterceptor implements HandlerInterceptor {
    public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler) throws  Exception{
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        String reqUrl = request.getRequestURL().toString();
        System.out.println("-----------------> Url check Interceptor , reqUrl : " +reqUrl);

        if(userId != null){
            session.setMaxInactiveInterval(30*60);
            return true;
        } else {
            response.sendRedirect("/login");
            return false;
        }
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub

    }
}
