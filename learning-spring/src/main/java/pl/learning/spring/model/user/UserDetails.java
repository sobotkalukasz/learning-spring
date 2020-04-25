package pl.learning.spring.model.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_details")
@Data
@NoArgsConstructor
public class UserDetails implements Serializable {

	private static final long serialVersionUID = 5038178008646923937L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user_details")
	@Setter(AccessLevel.PACKAGE)
	private Long id;

	@NotEmpty
	private String firtsName;

	@NotEmpty
	private String lastName;

}
