package com.codeup.springblog;

import com.codeup.springblog.model.CustomOAuth2User;
import com.codeup.springblog.services.UserDetailsLoader;
import com.codeup.springblog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserDetailsLoader userLoader;

    @Autowired
    private UserService userService;

    public SecurityConfiguration(UserDetailsLoader userLoader) {
        this.userLoader = userLoader;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userLoader).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .defaultSuccessUrl("/posts")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login?logout")
                .and()
                .authorizeRequests()
                .antMatchers("/", "/sign-up", "/posts", "/oauth/**")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/posts/*")
                .authenticated()
                .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(userService)
                .and()
//                .successHandler(new AuthenticationSuccessHandler() {
//                    @Override
//                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,Authentication authentication) throws IOException, ServletException {
//                        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
//                        userService.processOAuthPostLogin(oauthUser.getEmail());
//                        response.sendRedirect("/list");
//                    }
//                })
                //top and bottom code do the same thing
                .successHandler((request, response, authentication) -> {
                    CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
                    userService.processOAuthPostLogin(oauthUser.getEmail(), oauthUser.getEmail());
                    response.sendRedirect("/posts");
                });
    }
}
