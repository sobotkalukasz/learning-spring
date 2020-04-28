package pl.learning.spring.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
public class UserRegistrationTest {

	String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";

	@Autowired
	private MockMvc mockMvc;

	private MockHttpServletRequestBuilder prepareRegisterPostRequest(String email, String firstName, String lastName,
			String password, String rePassword) {
		HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
		CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());

		return post("/register")//
				.sessionAttr(TOKEN_ATTR_NAME, csrfToken)//
				.param(csrfToken.getParameterName(), csrfToken.getToken())//
				.param("email", email)//
				.param("firstName", firstName)//
				.param("lastName", lastName)//
				.param("password", password)//
				.param("rePassword", rePassword);
	}

	@Test
	public void submitRegistrationValid() throws Exception {
		this.mockMvc.perform(//
				prepareRegisterPostRequest("test@mail.com", "test", "test", "p@ssw0rD", "p@ssw0rD"))//
				.andExpect(model().hasNoErrors())//
				.andExpect(status().isOk());
	}

	@Test
	public void submitRegistrationEmailNotValid() throws Exception {
		this.mockMvc.perform(prepareRegisterPostRequest("notValid", "test", "test", "p@ssw0rD", "p@ssw0rD"))//
				.andExpect(model().hasErrors())//
				.andExpect(model().attributeHasFieldErrors("userFormTO", "email")).andExpect(status().isOk());
	}

	@Test
	public void submitRegistrationEmailNotUnique() throws Exception {
		this.mockMvc.perform(//
				prepareRegisterPostRequest("test2@mail.com", "test", "test", "p@ssw0rD", "p@ssw0rD"))//
				.andExpect(model().hasNoErrors()).andExpect(status().isOk());

		this.mockMvc.perform(//
				prepareRegisterPostRequest("test2@mail.com", "test", "test", "p@ssw0rD", "p@ssw0rD"))//
				.andExpect(model().hasErrors())//
				.andExpect(model().attributeHasFieldErrors("userFormTO", "email")).andExpect(status().isOk());
	}

	@Test
	public void submitRegistrationNamesToShort() throws Exception {
		this.mockMvc.perform(prepareRegisterPostRequest("test@mail.com", "te", "te", "p@ssw0rD", "p@ssw0rD"))//
				.andExpect(model().hasErrors())//
				.andExpect(model().attributeHasFieldErrors("userFormTO", "firstName", "lastName"))
				.andExpect(status().isOk());
	}

	@Test
	public void submitRegistrationNamesToLong() throws Exception {
		this.mockMvc
				.perform(prepareRegisterPostRequest("test@mail.com", "test1test1test1test1test1test1",
						"test1test1test1test1test1test1", "p@ssw0rD", "p@ssw0rD"))//
				.andExpect(model().hasErrors())//
				.andExpect(model().attributeHasFieldErrors("userFormTO", "firstName", "lastName"))
				.andExpect(status().isOk());
	}

	@Test
	public void submitRegistrationPasswordNoUpperCase() throws Exception {
		this.mockMvc.perform(prepareRegisterPostRequest("test@mail.com", "test", "test", "p@ssw0rd", "p@ssw0rd"))//
				.andExpect(model().hasErrors())//
				.andExpect(model().attributeHasFieldErrors("userFormTO", "password")).andExpect(status().isOk());
	}

	@Test
	public void submitRegistrationPasswordNoLowerCase() throws Exception {
		this.mockMvc.perform(prepareRegisterPostRequest("test@mail.com", "test", "test", "P@SSW0RD", "P@SSW0RD"))//
				.andExpect(model().hasErrors())//
				.andExpect(model().attributeHasFieldErrors("userFormTO", "password")).andExpect(status().isOk());
	}

	@Test
	public void submitRegistrationPasswordNoDigit() throws Exception {
		this.mockMvc.perform(prepareRegisterPostRequest("test@mail.com", "test", "test", "p@ssworD", "p@ssworD"))//
				.andExpect(model().hasErrors())//
				.andExpect(model().attributeHasFieldErrors("userFormTO", "password")).andExpect(status().isOk());
	}

	@Test
	public void submitRegistrationPasswordNoSpecialChar() throws Exception {
		this.mockMvc.perform(prepareRegisterPostRequest("test@mail.com", "test", "test", "possw0rD", "passw0rD"))//
				.andExpect(model().hasErrors())//
				.andExpect(model().attributeHasFieldErrors("userFormTO", "password")).andExpect(status().isOk());
	}

	@Test
	public void submitRegistrationPasswordWhiteSpace() throws Exception {
		this.mockMvc.perform(prepareRegisterPostRequest("test@mail.com", "test", "test", "possw0 rD", "passw0 rD"))//
				.andExpect(model().hasErrors())//
				.andExpect(model().attributeHasFieldErrors("userFormTO", "password")).andExpect(status().isOk());
	}

	@Test
	public void submitRegistrationPasswordToShort() throws Exception {
		this.mockMvc.perform(prepareRegisterPostRequest("test@mail.com", "test", "test", "p@w0rD", "p@w0rD"))//
				.andExpect(model().hasErrors())//
				.andExpect(model().attributeHasFieldErrors("userFormTO", "password")).andExpect(status().isOk());
	}

	@Test
	public void submitRegistrationPasswordToLong() throws Exception {
		this.mockMvc
				.perform(prepareRegisterPostRequest("test@mail.com", "test", "test",
						"p@ssw0rDp@ssw0rDp@ssw0rDp@ssw0rDp@ssw0rD", "p@ssw0rDp@ssw0rDp@ssw0rDp@ssw0rDp@ssw0rD"))//
				.andExpect(model().hasErrors())//
				.andExpect(model().attributeHasFieldErrors("userFormTO", "password")).andExpect(status().isOk());
	}

	@Test
	public void submitRegistrationPasswordNotMatching() throws Exception {
		this.mockMvc.perform(prepareRegisterPostRequest("test@mail.com", "test", "test", "p@ssw0rD", "wrong"))//
				.andExpect(model().hasErrors())//
				.andExpect(model().attributeHasFieldErrors("userFormTO", "rePassword")).andExpect(status().isOk());
	}

}
