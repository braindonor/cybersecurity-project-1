package sec.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sec.project.entity.Password;
import sec.project.repository.PasswordRepository;

@Service
public class PasswordManagerService {
    
    @Autowired
    private PasswordRepository passwordRepository; 
      
    public PasswordManagerService() {
        super();
    }
    
    public List<Password> findAll() {
		return this.passwordRepository.findAll();
    }

    public void add(final Password seedStarter) {
        this.passwordRepository.add(seedStarter);
    }
    
}
