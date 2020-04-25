package pl.learning.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
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
				.antMatchers("/admin").hasRole(UserRoleType.ADMIN.toString())//
				.antMatchers("/user").hasAnyRole(UserRoleType.ADMIN.toString(), UserRoleType.USER.toString())//
				.antMatchers("/").permitAll()//
				.and().formLogin();
	}

	@Bean
	public PasswordEncoder getPaswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

}
