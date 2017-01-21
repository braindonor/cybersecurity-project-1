package sec.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sec.project.entity.Password;


public interface PasswordRepository extends JpaRepository<Password, Long>{
	
	Password findByLoginname(String loginname);

}
