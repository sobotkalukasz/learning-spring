package pl.learning.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.learning.spring.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
