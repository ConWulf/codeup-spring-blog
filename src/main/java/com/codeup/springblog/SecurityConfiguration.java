package com.codeup.springblog;

import com.codeup.springblog.model.CustomOAuth2User;
import com.codeup.springblog.model.User;
import com.codeup.springblog.model.UserWithRoles;
import com.codeup.springblog.repositories.UserRepository;
import com.codeup.springblog.services.UserDetailsLoader;
import com.codeup.springblog.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsLoader userLoader;

    private final UserRepository userDao;

    private final UserService userService;

    public SecurityConfiguration(UserDetailsLoader userLoader, UserService userService, UserRepository userDao) {
        this.userLoader = userLoader;
        this.userService = userService;
        this.userDao = userDao;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userLoader).passwordEncoder(passwordEncoder());
    }

    @Bean
    WebClient webClient(ClientRegistrationRepository clientRegistrationRepository,
                        OAuth2AuthorizedClientRepository authorizedClientRepository) {
        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 =
                new ServletOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrationRepository, authorizedClientRepository);
        oauth2.setDefaultOAuth2AuthorizedClient(true);
        return WebClient.builder().apply(oauth2.oauth2Configuration()).build();
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
                .userService(userLoader)
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
                    userService.processOAuthPostLogin(oauthUser.getName(), oauthUser.getEmail(), passwordEncoder().encode(UUID.randomUUID().toString()));
                    User user = userDao.findByEmail(((CustomOAuth2User) authentication.getPrincipal()).getEmail());
                    if (user != null) {
                        ((User) authentication.getPrincipal()).setEmail(user.getEmail());
                        ((User) authentication.getPrincipal()).setUsername(user.getUsername());
                        ((User) authentication.getPrincipal()).setId(user.getId());
                        ((User) authentication.getPrincipal()).setProvider(user.getProvider());
                        ((User) authentication.getPrincipal()).setPosts(user.getPosts());
                    }
                    response.sendRedirect("/posts");
                });
    }
}
