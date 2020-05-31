package pl.learning.spring.model.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserTO {

	private Long id;

	@NotEmpty
	@Email
	private String email;
	
	@NotNull
	private UserDetailsTO userDetailsTO;

}
