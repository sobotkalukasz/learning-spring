package pl.learning.spring.model.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
public class User implements Serializable {

	private static final long serialVersionUID = 4601817227589422072L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(AccessLevel.PACKAGE)
	private Long id;

	@Column(nullable = false, unique = true)
	@NonNull
	private String login;

	@Column(nullable = false)
	@NonNull
	private String password;
	

	@OneToOne
	private UserDetails userDetails;

}
