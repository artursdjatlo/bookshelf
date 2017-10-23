package lv.tele2ssc.bookshelf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Web application configuration. We need this to add interceptor which will
 * select current logged-in user and put it to the model of every page.
 */
@Configuration
public class AppConfig extends WebMvcConfigurerAdapter {

    /**
     * Injecting interceptor
     */
    @Autowired
    private CurrentUserInterceptor currentUserInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Registering interceptor
        registry.addInterceptor(currentUserInterceptor);
    }
    
    
    
}
