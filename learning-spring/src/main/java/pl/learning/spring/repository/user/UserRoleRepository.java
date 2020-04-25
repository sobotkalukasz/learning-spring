package pl.learning.spring.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.learning.spring.model.user.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

}
