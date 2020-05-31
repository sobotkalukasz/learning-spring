package pl.learning.spring.model.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.learning.spring.model.user.validator.FieldMatch;
import pl.learning.spring.model.user.validator.UniqueEmail;
import pl.learning.spring.model.user.validator.ValidPassword;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldMatch(first = "rePassword", second = "password", message = "The passwords must match")
public class UserFormTO {

	@Email
	@UniqueEmail
	private String email;

	@ValidPassword
	private String password;

	private String rePassword;

	@Size(min = 3, max = 25)
	private String firstName;
	
	@Size(min = 3, max = 25)
	private String lastName;

}
