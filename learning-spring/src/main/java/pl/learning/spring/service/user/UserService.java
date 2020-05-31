package pl.learning.spring.service.user;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.learning.spring.model.user.User;
import pl.learning.spring.model.user.UserDetails;
import pl.learning.spring.model.user.UserRole;
import pl.learning.spring.model.user.UserRoleType;
import pl.learning.spring.model.user.dto.UserDetailsTO;
import pl.learning.spring.model.user.dto.UserFormTO;
import pl.learning.spring.model.user.dto.UserTO;
import pl.learning.spring.repository.user.UserRepository;
import pl.learning.spring.repository.user.UserRoleRepository;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
@Setter
public class UserService {

	private final UserRepository userRepository;

	private final UserRoleRepository userRoleRepository;

	private final PasswordEncoder passwordEncoder;

	public UserTO addUser(@Valid UserFormTO registrationUser) {
		if (Objects.isNull(registrationUser))
			throw new IllegalArgumentException("Given user is null or empty");
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
		User savedUser = userRepository.save(user);
		return mapUserToTransfer(savedUser);

	}

	public Optional<UserTO> findUserByEmail(String email) {
		if (email == null || email.isBlank()) {
			throw new IllegalArgumentException("Email could not be empty");
		}
		Optional<User> findByEmail = userRepository.findByEmail(email);
		return findByEmail.stream().map(this::mapUserToTransfer).findFirst();
	}

	public void updateUser(User user) {
		// TODO:
	}

	public void deleteUser(User user) {
		// TODO:
	}

	private UserTO mapUserToTransfer(@Valid User user) {
		return UserTO.builder()//
				.id(user.getId())//
				.email(user.getEmail())//
				.userDetailsTO(mapUserDetailsToTransfer(user.getUserDetails()))//
				.build();
	}

	private UserDetailsTO mapUserDetailsToTransfer(@Valid UserDetails userDetails) {
		return UserDetailsTO.builder()//
				.id(userDetails.getId())//
				.firtsName(userDetails.getFirtsName())//
				.lastName(userDetails.getLastName())//
				.build();
	}

}
