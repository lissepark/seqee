package services;

import database.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    //protected void configure(HttpSecurity http) throws Exception {
    //    http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
    //}

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().and()
                .authorizeRequests()
                .antMatchers("/adminruslan").authenticated()
                //.antMatchers(HttpMethod.POST, "/spittles").authenticated()
                .anyRequest().permitAll();
    }
/*
    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery("select username, password, true from Spitter where username=?")
                .authoritiesByUsernameQuery("select username, 'ROLE_USER' from Spitter where username=?")
                .passwordEncoder(new StandardPasswordEncoder("53cr3t"));
    }
*/

    DataService dataService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth
                .userDetailsService(new ServiceUser(dataService));
    }

}
