package cl.inacap.SistemaMonito.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
@Configuration
public class WebAuthorization extends WebSecurityConfigurerAdapter {

    @Autowired
    UserAuthenticationSuccessHandler successHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.headers().frameOptions().sameOrigin();
        http.authorizeRequests()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/login.html", "/login").permitAll()
                .antMatchers("/assets/**").permitAll()
                .antMatchers("/jefe/**").hasAuthority("JEFE")
                .antMatchers("/vendedor/**").hasAnyAuthority("JEFE","VENDEDOR")
                .anyRequest().authenticated();



        http.formLogin()
                .successHandler(successHandler)
                .loginPage("/api/login")
                .usernameParameter("email")
                .passwordParameter("password");


        http.logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler(logoutSuccessHandler()) // Set the custom LogoutSuccessHandler
                .permitAll();

        http.csrf().disable();

        // if user is not authenticated, just send an authentication failure response
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if login is successful, just clear the flags asking for authentication
        http.formLogin().successHandler(successHandler);

        // if login fails, just send an authentication failure response
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response
        //http.logout().logoutSuccessHandler(logoutSuccessHandler);
    }


    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, authentication) -> {
            response.sendRedirect("/login"); // Redirect to the login page after successful logout
        };
    }
}
