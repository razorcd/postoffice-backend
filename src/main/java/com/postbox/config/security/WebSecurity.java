package com.postbox.config.security;

import com.postbox.config.security.CustomAuthenticationFailureHandler;
import com.postbox.config.security.CustomAuthenticationSuccessHandler;
import com.postbox.config.security.CustomLogoutSuccessHandler;
import com.postbox.config.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
//@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
//@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    @Autowired
    CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    @Autowired
    CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers( "/incoming/**").permitAll()
                .antMatchers(HttpMethod.POST , "/users").permitAll()
                .anyRequest().authenticated()
            .and()
                .exceptionHandling()
                    .defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED), new AntPathRequestMatcher("/**"))
            .and()
                .logout().logoutUrl("/logout")
                         .clearAuthentication(true)
                         .logoutSuccessHandler(customLogoutSuccessHandler)
                         .permitAll()
            .and()
                .formLogin().loginProcessingUrl("/login")
                            .successHandler(customAuthenticationSuccessHandler)
                            .failureHandler(customAuthenticationFailureHandler)
                            .permitAll()
            .and()
                .rememberMe().alwaysRemember(true).useSecureCookie(false).userDetailsService(customUserDetailsService)
            .and()
                .httpBasic().disable()
                .csrf()
                    .disable()
//                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // TODO: enable csrf
//                .and()
                .cors()
//                    .disable()
            ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

}
