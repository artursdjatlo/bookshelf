package lv.tele2ssc.bookshelf;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;


/**
 * Configuration for security: 
 *  - how to check passwords
 *  - restricted or permitted resources
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource datasource;
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .usersByUsernameQuery("select email, password, 1 from user "
                        + "where email = ?")
                .authoritiesByUsernameQuery("select u.email, r.role_name from "
                        + "user u, user_role ur, role r where "
                        + "u.id = ur.user_id and ur.role_id = r.id and u.email = ?")
                .dataSource(datasource)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/book").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/h2-console").hasAuthority("admin")
                .anyRequest().authenticated()
                .and().csrf().disable().formLogin()
                        .loginPage("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                .and().headers().frameOptions().disable();
    }
    
    
    
}
