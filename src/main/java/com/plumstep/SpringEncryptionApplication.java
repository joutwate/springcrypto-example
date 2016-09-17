package com.plumstep;

import com.plumstep.utils.SecureRandomStringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 */
@SpringBootApplication
@EnableWebSecurity
@EnableSwagger2
public class SpringEncryptionApplication extends WebSecurityConfigurerAdapter {
    public static void main(String[] args) {
        SpringApplication.run(SpringEncryptionApplication.class, args);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public TextEncryptor textEncryptor() {
        // Generate an 8 character salt
        String salt = KeyGenerators.string().generateKey();
        // Generate a 128 character password
        String password = SecureRandomStringUtils.randomAscii(128);
        System.out.println("Generated salt = " + salt);
        System.out.println("Generated password = " + password);
        // Create an queryable text encryptor with these credentials to be used for this run
        return Encryptors.queryableText(password, salt);
    }

    protected void configure(HttpSecurity http) throws Exception {
        // We want SSL but authentication is handled by the UI.
        // Mutual TLS is used to validate that only specific apps like the UI can
        // even access this service.
        http.httpBasic().disable();

        // Turn off CSRF for this REST service.
        http.csrf().disable();

        http.authorizeRequests().antMatchers("/").permitAll().and()
                .authorizeRequests().antMatchers("/console/**").permitAll();
        http.headers().frameOptions().sameOrigin();

    }
}
