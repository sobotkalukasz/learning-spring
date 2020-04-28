package pl.learning.spring.model.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;
import pl.learning.spring.model.user.validator.FieldMatch;
import pl.learning.spring.model.user.validator.UniqueEmail;
import pl.learning.spring.model.user.validator.ValidPassword;

@Data
@FieldMatch(first = "rePassword", second = "password", message = "The passwords must match")
public class UserFormTO {

	@Email
	@UniqueEmail
	private String email;

	@NotEmpty
	@ValidPassword
	private String password;

	@NotEmpty
	private String rePassword;

	@Size(min = 3, max = 25)
	private String firstName;
	
	@Size(min = 3, max = 25)
	private String lastName;

}
