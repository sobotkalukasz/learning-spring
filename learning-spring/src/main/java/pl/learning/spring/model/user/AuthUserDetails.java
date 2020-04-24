package pl.learning.spring.model.user;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class AuthUserDetails implements UserDetails {

	private static final long serialVersionUID = -9176732688003705512L;

	private String userName;
	private String password;
	private List<GrantedAuthority> authotities;

	public AuthUserDetails(User user) {
		this.userName = user.getLogin();
		this.password = user.getPassword();

		// TODO: hardcoded
		this.authotities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authotities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO: hardcoded
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO: hardcoded
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO: hardcoded
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO: hardcoded
		return true;
	}

}
