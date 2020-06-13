package com.paymybuddy.sendmoney.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * This class is in charge of the application security configuration.
 *
 * @author Thierry SCHREINER
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Declare a UserDetailsService object that will be instanced by Spring.
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * This method create an instance of BCryptPasswordEncoderin order to make
     * the encryption of the password.
     *
     * @return a PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/admin").hasAuthority("ADMIN")
                .antMatchers("/resources/**", "/registration").permitAll()
                .anyRequest().authenticated().and().formLogin()
                .loginPage("/login").permitAll().and().logout().permitAll();
    }

    /**
     * Configure the AuthenticationManagerBuilder to use the password encoder.
     *
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    protected void configure(final AuthenticationManagerBuilder auth)
            throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    /**
     * An AuthenticationProvider implementation that retrieves user details from
     * a UserDetailsService.
     *
     * @return a DaoAuthenticationProvider object
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider =
                new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

}
