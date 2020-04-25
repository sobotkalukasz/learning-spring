package pl.learning.spring.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.learning.spring.model.user.UserRole;
import pl.learning.spring.model.user.UserRoleType;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
	
	UserRole findByRoleType(UserRoleType roleType);

}
