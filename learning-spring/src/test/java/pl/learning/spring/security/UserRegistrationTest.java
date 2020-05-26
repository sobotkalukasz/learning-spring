package pl.learning.spring.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import pl.learning.spring.model.user.User;
import pl.learning.spring.repository.user.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
public class UserRegistrationTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MockMvc mockMvc;

	private MockHttpServletRequestBuilder prepareRegisterPostRequest(String email, String firstName, String lastName,
			String password, String rePassword) {

		return post("/register")//
				.with(csrf())//
				.param("email", email)//
				.param("firstName", firstName)//
				.param("lastName", lastName)//
				.param("password", password)//
				.param("rePassword", rePassword);
	}

	@Test
	public void submitRegistrationValid() throws Exception {
		String email = "test@mail.com";

		this.mockMvc.perform(//
				prepareRegisterPostRequest(email, "test", "test", "p@ssw0rD", "p@ssw0rD"))//
				.andExpect(model().hasNoErrors())//
				.andExpect(status().isOk());

		Optional<User> user = userRepository.findByEmail(email);
		assertTrue(user.isPresent(), "User has been registered");
	}

	@Test
	public void submitRegistrationEmailNotValid() throws Exception {
		String email = "notValid";

		this.mockMvc.perform(prepareRegisterPostRequest(email, "test", "test", "p@ssw0rD", "p@ssw0rD"))//
				.andExpect(model().hasErrors())//
				.andExpect(model().attributeHasFieldErrors("userFormTO", "email")).andExpect(status().isOk());

		Optional<User> user = userRepository.findByEmail(email);
		assertTrue(user.isEmpty(), "User has not been registered");
	}

	@Test
	public void submitRegistrationEmailNotUnique() throws Exception {
		String email = "test2@mail.com";

		this.mockMvc.perform(//
				prepareRegisterPostRequest(email, "test", "test", "p@ssw0rD", "p@ssw0rD"))//
				.andExpect(model().hasNoErrors()).andExpect(status().isOk());

		Optional<User> user = userRepository.findByEmail(email);
		assertTrue(user.isPresent(), "User has been registered");

		this.mockMvc.perform(//
				prepareRegisterPostRequest("test2@mail.com", "test", "test", "p@ssw0rD", "p@ssw0rD"))//
				.andExpect(model().hasErrors())//
				.andExpect(model().attributeHasFieldErrors("userFormTO", "email")).andExpect(status().isOk());

		int actualSize = userRepository.findAll().stream().filter(us -> us.getEmail().equals(email))
				.collect(Collectors.toList()).size();

		assertEquals(1, actualSize, String.format("Found more than one user with email [%s]", email));
	}

	@Test
	public void submitRegistrationNamesToShort() throws Exception {
		String email = "test@mailValid.com";

		this.mockMvc.perform(prepareRegisterPostRequest(email, "te", "te", "p@ssw0rD", "p@ssw0rD"))//
				.andExpect(model().hasErrors())//
				.andExpect(model().attributeHasFieldErrors("userFormTO", "firstName", "lastName"))
				.andExpect(status().isOk());

		Optional<User> user = userRepository.findByEmail(email);
		assertTrue(user.isEmpty(), "User has not been registered");
	}

	@Test
	public void submitRegistrationNamesToLong() throws Exception {
		String email = "test@mailValid.com";

		this.mockMvc
				.perform(prepareRegisterPostRequest(email, "test1test1test1test1test1test1",
						"test1test1test1test1test1test1", "p@ssw0rD", "p@ssw0rD"))//
				.andExpect(model().hasErrors())//
				.andExpect(model().attributeHasFieldErrors("userFormTO", "firstName", "lastName"))
				.andExpect(status().isOk());

		Optional<User> user = userRepository.findByEmail(email);
		assertTrue(user.isEmpty(), "User has not been registered");
	}

	@Test
	public void submitRegistrationPasswordNoUpperCase() throws Exception {
		String email = "test@mailValid.com";

		this.mockMvc.perform(prepareRegisterPostRequest(email, "test", "test", "p@ssw0rd", "p@ssw0rd"))//
				.andExpect(model().hasErrors())//
				.andExpect(model().attributeHasFieldErrors("userFormTO", "password")).andExpect(status().isOk());

		Optional<User> user = userRepository.findByEmail(email);
		assertTrue(user.isEmpty(), "User has not been registered");
	}

	@Test
	public void submitRegistrationPasswordNoLowerCase() throws Exception {
		String email = "test@mailValid.com";

		this.mockMvc.perform(prepareRegisterPostRequest(email, "test", "test", "P@SSW0RD", "P@SSW0RD"))//
				.andExpect(model().hasErrors())//
				.andExpect(model().attributeHasFieldErrors("userFormTO", "password")).andExpect(status().isOk());

		Optional<User> user = userRepository.findByEmail(email);
		assertTrue(user.isEmpty(), "User has not been registered");
	}

	@Test
	public void submitRegistrationPasswordNoDigit() throws Exception {
		String email = "test@mailValid.com";

		this.mockMvc.perform(prepareRegisterPostRequest(email, "test", "test", "p@ssworD", "p@ssworD"))//
				.andExpect(model().hasErrors())//
				.andExpect(model().attributeHasFieldErrors("userFormTO", "password")).andExpect(status().isOk());

		Optional<User> user = userRepository.findByEmail(email);
		assertTrue(user.isEmpty(), "User has not been registered");
	}

	@Test
	public void submitRegistrationPasswordNoSpecialChar() throws Exception {
		String email = "test@mailValid.com";

		this.mockMvc.perform(prepareRegisterPostRequest(email, "test", "test", "possw0rD", "passw0rD"))//
				.andExpect(model().hasErrors())//
				.andExpect(model().attributeHasFieldErrors("userFormTO", "password")).andExpect(status().isOk());

		Optional<User> user = userRepository.findByEmail(email);
		assertTrue(user.isEmpty(), "User has not been registered");
	}

	@Test
	public void submitRegistrationPasswordWhiteSpace() throws Exception {
		String email = "test@mailValid.com";

		this.mockMvc.perform(prepareRegisterPostRequest(email, "test", "test", "possw0 rD", "passw0 rD"))//
				.andExpect(model().hasErrors())//
				.andExpect(model().attributeHasFieldErrors("userFormTO", "password")).andExpect(status().isOk());

		Optional<User> user = userRepository.findByEmail(email);
		assertTrue(user.isEmpty(), "User has not been registered");
	}

	@Test
	public void submitRegistrationPasswordToShort() throws Exception {
		String email = "test@mailValid.com";

		this.mockMvc.perform(prepareRegisterPostRequest(email, "test", "test", "p@w0rD", "p@w0rD"))//
				.andExpect(model().hasErrors())//
				.andExpect(model().attributeHasFieldErrors("userFormTO", "password")).andExpect(status().isOk());

		Optional<User> user = userRepository.findByEmail(email);
		assertTrue(user.isEmpty(), "User has not been registered");
	}

	@Test
	public void submitRegistrationPasswordToLong() throws Exception {
		String email = "test@mailValid.com";

		this.mockMvc
				.perform(prepareRegisterPostRequest(email, "test", "test", "p@ssw0rDp@ssw0rDp@ssw0rDp@ssw0rDp@ssw0rD",
						"p@ssw0rDp@ssw0rDp@ssw0rDp@ssw0rDp@ssw0rD"))//
				.andExpect(model().hasErrors())//
				.andExpect(model().attributeHasFieldErrors("userFormTO", "password")).andExpect(status().isOk());

		Optional<User> user = userRepository.findByEmail(email);
		assertTrue(user.isEmpty(), "User has not been registered");
	}

	@Test
	public void submitRegistrationPasswordNotMatching() throws Exception {
		String email = "test@mailValid.com";

		this.mockMvc.perform(prepareRegisterPostRequest(email, "test", "test", "p@ssw0rD", "wrong"))//
				.andExpect(model().hasErrors())//
				.andExpect(model().attributeHasFieldErrors("userFormTO", "rePassword")).andExpect(status().isOk());

		Optional<User> user = userRepository.findByEmail(email);
		assertTrue(user.isEmpty(), "User has not been registered");
	}

}
