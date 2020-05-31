package pl.learning.spring.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import javax.validation.ConstraintViolationException;

import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.learning.spring.model.user.dto.UserFormTO;
import pl.learning.spring.model.user.dto.UserTO;
import pl.learning.spring.repository.user.UserRepository;
import pl.learning.spring.service.user.UserService;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

	private final static String VALID_PASS = "p@ssw0rD";
	private final static String VALID_EMAIL = "valid@mail.com";
	private final static String VALID_NAME = "tester";

	private final static String INVLID_EMAIL = "notValid";
	private final static String INVALID_SHORT_NAME = "te";
	private final static String INVALID_LONG_NAME = "test1test1test1test1test1test1";
	private final static String INVALID_NO_UPPERCASE_PASS = VALID_PASS.toLowerCase();
	private final static String INVALID_NO_LOWERCASE_PASS = VALID_PASS.toUpperCase();
	private final static String INVALID_NO_DIGIT_PASS = VALID_PASS.replace("0", "o");
	private final static String INVALID_NO_SPECIAL_PASS = VALID_PASS.replace("@", "a");
	private final static String INVALID_WHITESPACE_PASS = VALID_PASS.replace("0", " ");
	private final static String INVALID_SHORT_PASS = "p@w0rD";
	private final static String INVALID_LONG_PASS = "p@ssw0rDp@ssw0rDp@ssw0rDp@ssw0rDp@ssw0rD";

	@Autowired
	UserService userService;

	@Autowired
	UserRepository userRepository;

	@After
	public void cleanUpRepository() {
		userRepository.deleteAllInBatch();
	}

	@Test
	public void shouldPersistValidUser() {
		UserFormTO validUser = getValidUser();
		UserTO userTO = userService.addUser(validUser);
		assertNotNull(userTO, String.format("Valid user [%s] was not persist", validUser.toString()));
		assertNotNull(userTO.getId());
		assertEquals(validUser.getEmail(), userTO.getEmail());
		assertEquals(validUser.getFirstName(), userTO.getUserDetailsTO().getFirtsName());
		assertEquals(validUser.getLastName(), userTO.getUserDetailsTO().getLastName());
	}

	@ParameterizedTest
	@MethodSource("createInvalidUsers")
	public void shouldNotPersistInvalidUser(UserFormTO invalidUser) {
		assertThrows(ConstraintViolationException.class, () -> userService.addUser(invalidUser));
	}
	
	@ParameterizedTest
	@NullSource
	public void shouldNotPersistNullUser(UserFormTO invalidUser) {
		assertThrows(IllegalArgumentException.class, () -> userService.addUser(invalidUser));
	}

	@Test
	public void shouldNotPersistUserNotUniqueEmail() {
		userService.addUser(getValidUser());
		assertThrows(ConstraintViolationException.class, () -> userService.addUser(getValidUser()));
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	public void shouldValidateEmailWhileFindByEmail(String email) {
		assertThrows(IllegalArgumentException.class, () -> userService.findUserByEmail(email));
	}

	private static Stream<UserFormTO> createInvalidUsers() {

		UserFormTO nullEmailUser = getValidUser();
		nullEmailUser.setEmail(null);

		UserFormTO emptyEmailUser = getValidUser();
		emptyEmailUser.setEmail("");

		UserFormTO nullNameUser = getValidUser();
		nullNameUser.setFirstName(null);

		UserFormTO emptyNameUser = getValidUser();
		emptyNameUser.setFirstName("");

		UserFormTO nullLastNameUser = getValidUser();
		nullLastNameUser.setFirstName(null);

		UserFormTO emptyLastNameUser = getValidUser();
		emptyLastNameUser.setFirstName("");

		UserFormTO nullPassUser = getValidUser();
		nullPassUser.setPassword(null);

		UserFormTO emptyPassUser = getValidUser();
		emptyPassUser.setPassword("");

		UserFormTO nullRePassUser = getValidUser();
		nullRePassUser.setRePassword(null);

		UserFormTO emptyRePassUser = getValidUser();
		emptyRePassUser.setRePassword("");

		UserFormTO invalidEmailUser = getValidUser();
		invalidEmailUser.setEmail(INVLID_EMAIL);

		UserFormTO toShortNameUser = getValidUser();
		toShortNameUser.setFirstName(INVALID_SHORT_NAME);

		UserFormTO toShortLastNameUser = getValidUser();
		toShortLastNameUser.setLastName(INVALID_SHORT_NAME);

		UserFormTO toLongNameUser = getValidUser();
		toLongNameUser.setFirstName(INVALID_LONG_NAME);

		UserFormTO toLongLastNameUser = getValidUser();
		toLongLastNameUser.setLastName(INVALID_LONG_NAME);

		UserFormTO noUpperCasePassUser = getValidUser();
		noUpperCasePassUser.setPassword(INVALID_NO_UPPERCASE_PASS);
		noUpperCasePassUser.setRePassword(INVALID_NO_UPPERCASE_PASS);

		UserFormTO noLowerCasePassUser = getValidUser();
		noLowerCasePassUser.setPassword(INVALID_NO_LOWERCASE_PASS);
		noLowerCasePassUser.setRePassword(INVALID_NO_LOWERCASE_PASS);

		UserFormTO noDigitsPassUser = getValidUser();
		noDigitsPassUser.setPassword(INVALID_NO_DIGIT_PASS);
		noDigitsPassUser.setRePassword(INVALID_NO_DIGIT_PASS);

		UserFormTO noSpecialPassUser = getValidUser();
		noSpecialPassUser.setPassword(INVALID_NO_SPECIAL_PASS);
		noSpecialPassUser.setRePassword(INVALID_NO_SPECIAL_PASS);

		UserFormTO whitespacePassUser = getValidUser();
		whitespacePassUser.setPassword(INVALID_WHITESPACE_PASS);
		whitespacePassUser.setRePassword(INVALID_WHITESPACE_PASS);

		UserFormTO shortPassUser = getValidUser();
		shortPassUser.setPassword(INVALID_SHORT_PASS);
		shortPassUser.setRePassword(INVALID_SHORT_PASS);

		UserFormTO longPassUser = getValidUser();
		longPassUser.setPassword(INVALID_LONG_PASS);
		longPassUser.setRePassword(INVALID_LONG_PASS);

		return Stream.of( nullEmailUser, emptyEmailUser, nullNameUser, emptyNameUser, nullLastNameUser,
				emptyLastNameUser, nullPassUser, emptyPassUser, nullRePassUser, emptyRePassUser, invalidEmailUser,
				toShortNameUser, toShortLastNameUser, toLongNameUser, toLongLastNameUser, noUpperCasePassUser,
				noLowerCasePassUser, noDigitsPassUser, noSpecialPassUser, whitespacePassUser, shortPassUser,
				longPassUser);
	}

	private static UserFormTO getValidUser() {
		return UserFormTO.builder()//
				.email(VALID_EMAIL)//
				.firstName(VALID_NAME)//
				.lastName(VALID_NAME)//
				.password(VALID_PASS)//
				.rePassword(VALID_PASS)//
				.build();
	}

}
