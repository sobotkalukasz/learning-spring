package pl.learning.spring.service.user;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pl.learning.spring.model.user.User;
import pl.learning.spring.model.user.UserRole;
import pl.learning.spring.model.user.UserRoleType;
import pl.learning.spring.repository.user.UserRepository;
import pl.learning.spring.repository.user.UserRoleRepository;

@Service
public class UserService {

	private UserRepository userRepository;
	
	private UserRoleRepository userRoleRepository;
	
	private PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Autowired
	public void setUserRoleRepository(UserRoleRepository userRoleRepository) {
		this.userRoleRepository = userRoleRepository;
	}
	
	public void addUser(User user) {
//		TODO: add user validation + change login into email
		UserRole userRole = userRoleRepository.findByRoleType(UserRoleType.USER);
		user.setRoles(new HashSet<>());
		user.getRoles().add(userRole);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	public void updateUser(User user) {
		// TODO:
	}

	public void deleteUser(User user) {
		// TODO:
	}



}
