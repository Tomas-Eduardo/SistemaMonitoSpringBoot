package cl.inacap.SistemaMonito.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@EnableWebSecurity
@Configuration
public class WebAuthorization extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(WebAuthorization.class);



    @Autowired
    UserAuthenticationSuccessHandler successHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        logger.info("Configurando la seguridad...");

        http.headers().frameOptions().sameOrigin();
        http.authorizeRequests()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/login.html", "/login").permitAll()
                .antMatchers("/assets/**").permitAll()
                .antMatchers("/jefe/**").hasAuthority("JEFE")
                .antMatchers("/vendedor/**").hasAnyAuthority("VENDEDOR")
                .anyRequest().authenticated();


        http.exceptionHandling()
                .authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));


        http.formLogin()
                .successHandler(successHandler)
                .failureHandler((request, response, exception) -> {
                    logger.error("Error de autenticación: {}", exception.getMessage());
                    response.sendRedirect("/login?error");
                })
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password");


        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler()) // Set the custom LogoutSuccessHandler
                .permitAll();

        http.csrf().disable();

        http.formLogin().successHandler(successHandler);

        logger.info("Configuración de seguridad completa.");
    }


    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, authentication) -> {
            response.sendRedirect("/login"); // Redirect to the login page after successful logout
        };
    }
}
