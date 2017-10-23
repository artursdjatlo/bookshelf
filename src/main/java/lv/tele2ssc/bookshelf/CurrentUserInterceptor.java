package lv.tele2ssc.bookshelf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * We need this interceptor to select current logged-in user 
 * and put it to the model of every page.
 * This should be configured in AppConfig class.
 */

@Component
public class CurrentUserInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(CurrentUserInterceptor.class);
    
    @Autowired
    private UserService userService;
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // Retriving email of currently logged in user.
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        // if user isn't logged in yet - email is null.
        if (email != null) {
            User currentUser = userService.findByEmail(email);
            modelAndView.addObject("currentUser", currentUser);
        }
    }
    
}
