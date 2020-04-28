package pl.learning.spring.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.learning.spring.model.user.AuthUserDetails;
import pl.learning.spring.model.user.User;
import pl.learning.spring.repository.user.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthUserService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<User> findByLogin = userRepository.findByEmail(userName);
		return findByLogin.map(AuthUserDetails::new).orElseThrow(
				() -> new UsernameNotFoundException(String.format("Could not find user by login [%s]", userName)));
	}

}
