package cl.inacap.SistemaMonito.config;

import cl.inacap.SistemaMonito.models.UserEntity;
import cl.inacap.SistemaMonito.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebAuthentication extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    UserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(inputName-> {
            UserEntity user = userRepository.findByEmail(inputName);
            if (user != null) {

                String role=user.getRol().name();
                User usuario = new User(user.getEmail(), user.getPassword(),
                        AuthorityUtils.createAuthorityList(role));
                return usuario;
            } else {
                throw new UsernameNotFoundException("Usuario no Encontrado: " + inputName);
            }
        });
    }
}
