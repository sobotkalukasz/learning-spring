package pl.learning.spring.model.user;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class User implements Serializable {

	private static final long serialVersionUID = 4601817227589422072L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user")
	@Setter(AccessLevel.PACKAGE)
	private Long id;

	@Column(nullable = false, unique = true)
	@NotEmpty
	private String login;

	@Column(nullable = false)
	@NotEmpty
	private String password;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(name = "user_user_role", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id_user") }, inverseJoinColumns = {
					@JoinColumn(name = "role_id", referencedColumnName = "id_role") })
	@NonNull
	private Set<UserRole> roles;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private UserDetails userDetails;

}
