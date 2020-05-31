package pl.learning.spring.model.user.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsTO {
	
	private Long id;

	@NotEmpty
	private String firtsName;

	@NotEmpty
	private String lastName;

}
