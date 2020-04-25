package pl.learning.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import pl.learning.spring.model.user.UserRoleType;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private UserDetailsService userService;

	@Autowired
	public SecurityConfiguration(UserDetailsService userService) {
		this.userService = userService;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()//
				.antMatchers("/").permitAll()//
				.antMatchers("/register").permitAll()//
				.antMatchers("/admin").hasRole(UserRoleType.ADMIN.toString())//
				.anyRequest().authenticated()
				.and().formLogin();
	}

	@Bean
	public PasswordEncoder getPaswordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

}
