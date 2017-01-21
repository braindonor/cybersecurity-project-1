package sec.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sec.project.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
