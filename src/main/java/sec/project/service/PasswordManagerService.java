package sec.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sec.project.entity.Password;
import sec.project.entity.User;
import sec.project.repository.PasswordRepository;

@Service
public class PasswordManagerService {
    
    @Autowired
    private PasswordRepository passwordRepository;
    
    public List<Password> findAll() {
		return passwordRepository.findAll();
    }

    public void add(final Password password, final User user) {
        password.setUser(user);
        passwordRepository.save(password);
    }
    
}
