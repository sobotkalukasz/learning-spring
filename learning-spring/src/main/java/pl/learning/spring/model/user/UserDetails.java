package pl.learning.spring.model.user;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
public class UserDetails implements Serializable {

	private static final long serialVersionUID = 5038178008646923937L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(AccessLevel.PACKAGE)
	private Long id;

	@NonNull
	private String firtsName;

	@NonNull
	private String lastName;

}
