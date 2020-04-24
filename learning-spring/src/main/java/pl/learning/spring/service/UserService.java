package pl.learning.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.learning.spring.model.User;
import pl.learning.spring.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void addUser(User user) {
		// TODO: server side validation
		userRepository.save(user);
	}

	public void updateUser(User user) {
		// TODO: server side validation
	}

	public void deleteUser(User user) {
		userRepository.delete(user);
	}

}
