package com.example.student.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringSecurity {

	@Bean
	public UserDetailsService getUserDetailService() {
//		UserDetails admin = User.withUsername("hemant-all").password(encoder.encode("password-all")).roles("ADMIN")
//				.build();
//		UserDetails user = User.withUsername("hemant").password(encoder.encode("password")).roles("USER", "HR").build();
//		return new InMemoryUserDetailsManager(admin, user);

		return new UserInfoUserDetailsService();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		  http.csrf().disable()
          .authorizeRequests(authorize -> authorize
              .requestMatchers("/public/**").permitAll()
              .requestMatchers("/private/**").authenticated()
          )
          .formLogin(form -> form
              .loginPage("/login")
              .permitAll()
          )
          .httpBasic();
      
      return http.build();
	}

	@Bean
	public PasswordEncoder NoOpPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(getUserDetailService());
		// authenticationProvider.setPasswordEncoder(passwordEncoder());
		authenticationProvider.setPasswordEncoder(NoOpPasswordEncoder());
		return authenticationProvider;
	}

}
