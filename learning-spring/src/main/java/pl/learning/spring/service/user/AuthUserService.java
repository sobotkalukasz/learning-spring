package pl.learning.spring.service.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pl.learning.spring.model.AuthUserDetails;
import pl.learning.spring.model.User;
import pl.learning.spring.repository.user.UserRepository;

@Service
public class AuthUserService implements UserDetailsService {

	private UserRepository userRepository;

	@Autowired
	public AuthUserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<User> findByLogin = userRepository.findByLogin(userName);
		return findByLogin.map(AuthUserDetails::new).orElseThrow(
				() -> new UsernameNotFoundException(String.format("Could not find user by login [%s]", userName)));
	}

}
