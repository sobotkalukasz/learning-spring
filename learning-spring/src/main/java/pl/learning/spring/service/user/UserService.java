package pl.learning.spring.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import pl.learning.spring.repository.user.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public void addUser(User user) {
		// TODO:
	}

	public void updateUser(User user) {
		// TODO:
	}

	public void deleteUser(User user) {
		// TODO:
	}



}
