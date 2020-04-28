package pl.learning.spring.service.user;

import java.util.HashSet;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.learning.spring.model.user.User;
import pl.learning.spring.model.user.UserDetails;
import pl.learning.spring.model.user.UserRole;
import pl.learning.spring.model.user.UserRoleType;
import pl.learning.spring.model.user.dto.UserFormTO;
import pl.learning.spring.repository.user.UserRepository;
import pl.learning.spring.repository.user.UserRoleRepository;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	
	private final UserRoleRepository userRoleRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	public void addUser(UserFormTO registrationUser) {
		UserRole userRole = userRoleRepository.findByRoleType(UserRoleType.USER);
		User user = new User();
		user.setRoles(new HashSet<>());
		user.getRoles().add(userRole);
		user.setEmail(registrationUser.getEmail());
		user.setPassword(passwordEncoder.encode(registrationUser.getPassword()));
		UserDetails details = new UserDetails();
		details.setFirtsName(registrationUser.getFirstName());
		details.setLastName(registrationUser.getLastName());
		user.setUserDetails(details);
		userRepository.save(user);
	}

	public void updateUser(User user) {
		// TODO:
	}

	public void deleteUser(User user) {
		// TODO:
	}



}
