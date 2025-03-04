package com.scm2.SmartContactManager.Config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.scm2.SmartContactManager.Services.Impl.CustomUserDetailsService;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity

public class SeccurityConfig {

    /*
     * @Bean
     * public UserDetailsService userDetailsService() {
     * 
     * UserDetails userDetails = User
     * .builder()
     * .username("mohit")
     * .password(passwordEncoder().encode("mohit@123"))
     * .build();
     * 
     * 
     * return new InMemoryUserDetailsManager(userDetails);
     * }
     * 
     * @Bean
     * public PasswordEncoder passwordEncoder(){
     * return new BCryptPasswordEncoder();
     * }
     */
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private CustomAuthenticationFaliureHandler customAuthenticationFaliureHandler;

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        // user detials service ka obj
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    // security chain filter

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests(request -> {
            // authorise.requestMatchers("/home","/register","/services").permitAll();
            request.requestMatchers("/user/**").authenticated();
            request.anyRequest().permitAll();
        })
                // .formLogin(Customizer.withDefaults()); //login metnod, by default
                .formLogin(formLogin -> {

                    // change default form to new one
                    formLogin.loginPage("/login") // login page url
                            .loginProcessingUrl("/authentication") // where to summit that login in html form submit "no
                                                                   // need to be exist"
                            // .successForwardUrl("/user/dashboard") //after success login
                            // .successForwardUrl("/user/profile") // shoud be exist
                            .defaultSuccessUrl("/user/profile", true)
                            // .failureForwardUrl("/login?error=true")
                            .usernameParameter("email")
                            .passwordParameter("password")
                            .failureHandler(customAuthenticationFaliureHandler)
                            .permitAll(); // all can use this
                    /*
                     * //scussess handlar
                     * 
                     * formLogin.successHandler(new AuthenticationSuccessHandler() {
                     * 
                     * @Override
                     * public void onAuthenticationSuccess(HttpServletRequest request,
                     * HttpServletResponse response,
                     * Authentication authentication) throws IOException, ServletException {
                     * // TODO Auto-generated method stub
                     * throw new
                     * UnsupportedOperationException("Unimplemented method 'onAuthenticationSuccess'"
                     * );
                     * }
                     * 
                     * });
                     * 
                     * //failure handler
                     * 
                     * formLogin.failureHandler(new AuthenticationFailureHandler() {
                     * 
                     * @Override
                     * public void onAuthenticationFailure(HttpServletRequest request,
                     * HttpServletResponse response,
                     * AuthenticationException exception) throws IOException, ServletException {
                     * // TODO Auto-generated method stub
                     * throw new
                     * UnsupportedOperationException("Unimplemented method 'onAuthenticationFailure'"
                     * );
                     * }
                     * 
                     * });
                     * 
                     * 
                     */
                });

        // logout
        httpSecurity.logout(logoutForm -> {
            logoutForm.logoutUrl("/logout")
                    .invalidateHttpSession(true) // Invalidate session on logout
                    .clearAuthentication(true) // Clear authentication
                    .permitAll(); // Allow anyone to log out
        });

        /*
            O outh configuration
         */    
        // httpSecurity.oauth2Login(Customizer.withDefaults());

        httpSecurity.oauth2Login(oauth->{
            oauth.loginPage("/login")
                .successHandler(customAuthenticationSuccessHandler);


        }

        
        );


        


        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
