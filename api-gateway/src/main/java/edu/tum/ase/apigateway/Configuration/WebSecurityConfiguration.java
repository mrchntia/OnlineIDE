package edu.tum.ase.apigateway;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;


import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableOAuth2Sso
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter  {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .logout()
            .logoutUrl("/logout")
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .deleteCookies("JSESSIONID")
            .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
                httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            })
        .and()
            .csrf()
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .and()
            .authorizeRequests()
            .antMatchers("/authenticated").permitAll()
            .antMatchers("/compiler/**").authenticated()
            //.antMatchers("/darkmode/**").authenticated()
            .antMatchers("/project/**").authenticated();
    }

}
